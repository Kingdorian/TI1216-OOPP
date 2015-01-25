package application;

import application.animation.CalculateMatch.MainAIController;
import application.animation.Container.CalculatedMatch;
import application.animation.Container.CurrentPositions;
import application.animation.Container.PlayerInfo;
import application.animation.Container.TeamPositions;
import application.model.Competition;
import application.model.Match;
import application.model.Card;
import application.model.Market;
import application.model.Players;
import application.model.Reason;
import application.model.Team;

import java.util.ArrayList;

/**
 * This class' playMatch method can be used to handle everything which has
 * something to do with generating a football match, animating a football match
 * and choosing the players positions on the football field. The only thing it
 * needs are 2 teams with at least 11 players each.
 *
 * Some performance information: (tested on 27-12-2014) -Generating 800 matches
 * (without animating them of course), took a little less than 2 minutes (@ 2.4
 * GHz i7), so around 0.15 seconds per match. -After generating 800 matches, the
 * program was using less than 200 MB of memory, peaking at around 240 MB, so
 * there are no (major) memory leaks.
 *
 * Comparison to without cleaning the match data: (tested on 27-12-2014)
 * -Generating 800 matches (without animating them), took 1 and a half minute (@
 * 2.4 GHz i7), so around 0.11 seconds per match. -After generating 800 matches,
 * the program was using around 550 MB of memory, soon after starting with
 * generating the matches, the memory usage peaked at almost 800 MB of memory
 * usage, then afterwards stabalized at around 500 MB of memory usage, but
 * sometimes jumping up to 650 MB.
 *
 * In conclusion the java garbage collector seems to be pretty good, but for
 * optimal performance (in exceptional cases) it does need some help.
 */
public class PlayAnimation {

    private final static Object lockAll = new Object();
    private final static Object lockGeneration = new Object();
    private final static Object lockAnimation = new Object();
    private final static Object lockUpdateResults = new Object();
    private static int count;
    private static TeamPositions teamPositions;

    /**
     * Plays all matches of the current round and adjusts them in the
     * competition
     *
     * @param teamPositions positions of the team played by the player
     * @param main instance of the Main class
     * @return the match of the player
     */
    public static Match playMatches(TeamPositions teamPositions, Main main) {

        //make sure next match can't be generated when previous one isn't done yet.
        synchronized (lockAll) {
            PlayAnimation.teamPositions = teamPositions;
            Competition competition = Main.getCompetition();
            Market market = competition.getMarket();
            String playerTeam = competition.getChosenTeamName();
            int round = competition.getRound();
            Match[] matches = competition.getRound(round - 1);

            count = 0;

            //Update time not available
            for (Team t : competition.getTeams()) {
                for (Players p : t.getPlayers()) {
                    if (p.getTimeNotAvailable() > 0) {
                        p.setTimeNotAvailable(p.getTimeNotAvailable() - 1);
                        if (p.getTimeNotAvailable() == 0) {
                            p.setCard(Card.DEFAULT);
                        }
                    }
                }
            }

            //buy and sell players
            market.computerBuyPlayer(competition);
            market.computerSellPlayer(competition);
            Main.getTitleController().refreshMoney();

            // start a thread which will calculate the results of the other teams, while
            // the player is playing his own match
            Thread matchThread = new Thread() {
                @Override
                public void run() {
                    // for all matches: if it is NOT the players match, generate it without animation and store it.
                    for (int i = 0; i < matches.length; i++) {
                        if (!matches[i].getHomeTeam().getName().equals(playerTeam) && !matches[i].getVisitorTeam().getName().equals(playerTeam)) {
                            matches[i] = playMatch(matches[i].getHomeTeam(), matches[i].getVisitorTeam(), false, main);
                        }
                    }
                    synchronized (lockUpdateResults) {
                        if (count > 0) {
                            competition.updateResults();
                        } else {
                            count++;
                        }
                    }
                }
            };

            matchThread.start();

            // for all matches: if it IS the players match, generate, animation and return it.
            for (int i = 0; i < matches.length; i++) {
                if (matches[i].getHomeTeam().getName().equals(playerTeam) || matches[i].getVisitorTeam().getName().equals(playerTeam)) {
                    matches[i] = playMatch(matches[i].getHomeTeam(), matches[i].getVisitorTeam(), true, main);
                    synchronized (lockUpdateResults) {
                        if (count > 0) {
                            competition.updateResults();
                        } else {
                            count++;
                        }
                    }
                    return matches[i];
                }
            }

            return null; //return null if the play had no match (which shouldn't be possible)
        }
    }

    /**
     * This method will make sure a football match will get generated and
     * animated, based on the 2 teams which are supplied by the parameters. It
     * will also let the player choose the positions of the players of his team.
     * Afterwards it will return a Match object, containing the results of the
     * generated match.
     *
     * @param homeTeam The home team
     * @param visitorTeam The visitor team
     * @param shouldAnimate If the match should be stored and animated, or only
     * generated
     * @return A Match object containing the generated match
     */
    private static Match playMatch(Team homeTeam, Team visitorTeam, boolean shouldAnimate, Main main) {

        //ally team:
        //enemy team:
        TeamPositions leftTeam;
        TeamPositions rightTeam;
        boolean playerHome = true;

        //set the positions of the players
        if (shouldAnimate) {
            synchronized (lockAnimation) {
                if (homeTeam.getName().equals(Main.getChosenTeamName())) {
                    leftTeam = teamPositions;
                    rightTeam = setPositions(visitorTeam, false);
                } else {
                    playerHome = false;
                    leftTeam = teamPositions;
                    rightTeam = setPositions(homeTeam, false);
                }
            }
        } else {
            leftTeam = setPositions(homeTeam, true);
            rightTeam = setPositions(visitorTeam, false);
        }

        CalculatedMatch match;
        // Make sure at most 1 thread can generate a match at a time (because a lot
        // of variables are static
        synchronized (lockGeneration) {
            // generate the match
            match = (new MainAIController()).createMatch(leftTeam, rightTeam, shouldAnimate);

            // reset generation variables
            CurrentPositions.reset();
        }

        // play the generated match's animation
        if (shouldAnimate) //only the players match should be animated, but to be safe, also synchroniza this
        {
            synchronized (lockAnimation) {
                main.playMatch(match);
            }
        }

        // get the scores
        int scoreLeft = match.getPosition(match.amoutOfFrames() - 1).getScoreLeft();
        int scoreRight = match.getPosition(match.amoutOfFrames() - 1).getScoreRight();

        // Clear the memory used to store the match.
        // This does make a huge difference (see tests information in the comments above)
        match = null; // DO NOT REMOVE OR CHANGE THIS STATEMENT
        System.gc();

        //generate injurys and red/yellow cards
        Match result = playerHome ? new Match(homeTeam, visitorTeam, scoreLeft, scoreRight) : new Match(homeTeam, visitorTeam, scoreRight, scoreLeft);
        generateInjuries(result, leftTeam, rightTeam);
        generateCards(result, leftTeam, rightTeam);

        return result;
    }

    /**
     * Set the favorite positions of a team (and choose players to put in the
     * field)
     *
     * @param team the players of the team
     * @param isHomeTeam if this team should play at the left side (else right
     * side)
     * @return the favorite positions of the players and which players to put in
     * the field
     */
    private static TeamPositions setPositions(Team team, boolean isHomeTeam) {
        TeamPositions result = new TeamPositions(team);
        if (isHomeTeam) {
            result.setDefaultLeftPlayers();
        } else {
            result.setDefaultRightPlayers();
        }

        return result;
    }

    /**
     * Generate blessures
     *
     * @param match the match to generate injuries for
     * @param leftTeam the left team (so only players in the field can get
     * injuries)
     * @param rightTeam the right team (so only players in the field can get
     * injuries)
     */
    public static void generateInjuries(Match match, TeamPositions leftTeam, TeamPositions rightTeam) {

        // no injuries for keeper
        ArrayList<PlayerInfo> leftPlayers = leftTeam.getDefenders();
        leftPlayers.addAll(leftTeam.getMidfielders());
        leftPlayers.addAll(leftTeam.getAttackers());

        ArrayList<PlayerInfo> rightPlayers = rightTeam.getDefenders();
        rightPlayers.addAll(rightTeam.getMidfielders());
        rightPlayers.addAll(rightTeam.getAttackers());

        double random = Math.random();

        //max 2 injuries
        //0: 90% chance
        //1: or more: 10% chance
        //2: 3% chance
        while (random < 0.1) {
            if (Math.random() > 0.5) {
                //left team
                double randValue = Math.random();
                leftPlayers.get((int) (randValue * 10.0)).getPlayer().setReason(Reason.DEFAULT.random());
                leftPlayers.get((int) (randValue * 10.0)).getPlayer().setTimeNotAvailable((int) (Math.random() * 6.0 + 1.0));
            } else {
                //right team
                double randValue = Math.random();
                rightPlayers.get((int) (randValue * 10.0)).getPlayer().setReason(Reason.DEFAULT.random());
                rightPlayers.get((int) (randValue * 10.0)).getPlayer().setTimeNotAvailable((int) (Math.random() * 6.0 + 1.0));
            }
            random += 0.07;
        }

    }

    /**
     * Generate blessures
     *
     * @param match the match to generate cards for
     * @param leftTeam the left team (so only players in the field can get
     * cards)
     * @param rightTeam the right team (so only players in the field can get
     * cards)
     */
    public static void generateCards(Match match, TeamPositions leftTeam, TeamPositions rightTeam) {

        // no injuries for keeper
        ArrayList<PlayerInfo> leftPlayers = leftTeam.getDefenders();
        leftPlayers.addAll(leftTeam.getMidfielders());
        leftPlayers.addAll(leftTeam.getAttackers());

        ArrayList<PlayerInfo> rightPlayers = rightTeam.getDefenders();
        rightPlayers.addAll(rightTeam.getMidfielders());
        rightPlayers.addAll(rightTeam.getAttackers());

        double random = Math.random();

        //max 3 yellow cards
        //0: 10%
        //1 or more: 90%
        //2 or more: 50%
        //3: 10%
        //2x yellow card for same player = red card
        while (random < 0.9) {
            if (Math.random() > 0.5) {
                //left team
                double randValue = Math.random();
                leftPlayers.get((int) (randValue * 10.0)).getPlayer().setCard(Card.YELLOW);
                leftPlayers.get((int) (randValue * 10.0)).getPlayer().setTimeNotAvailable((int) (Math.random() * 6.0 + 2.0));
            } else {
                //right team
                double randValue = Math.random();
                rightPlayers.get((int) (randValue * 10.0)).getPlayer().setCard(Card.YELLOW);
                rightPlayers.get((int) (randValue * 10.0)).getPlayer().setTimeNotAvailable((int) (Math.random() * 6.0 + 2.0));
            }
            random += 0.4;

        }

    }

}
