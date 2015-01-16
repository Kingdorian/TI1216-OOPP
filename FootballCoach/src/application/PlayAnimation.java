package application;

import application.animation.CalculateMatch.MainAIController;
import application.animation.Container.CalculatedMatch;
import application.animation.Container.CurrentPositions;
import application.animation.Container.TeamPositions;
import application.animation.Playmatch.AnimateFootballMatch;
import application.model.Competition;
import application.model.Match;
import application.model.Team;
import application.view.GameScreenChoosePositionsController;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
     * @return the match of the player
     */
    public static Match playMatches(TeamPositions teamPositions, Main main) {
        PlayAnimation.teamPositions = teamPositions;
        
        Competition competition = Main.getCompetition();
        String playerTeam = competition.getChosenTeamName();
        int round = competition.getRound();
        Match[] matches = competition.getRound(round);

        count = 0;

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

        //set the positions of the players
        if (shouldAnimate) {
            synchronized (lockAnimation) {
                if (homeTeam.getName().equals(Main.getChosenTeamName())) {
                    leftTeam = teamPositions;
                    rightTeam = choosePositions(visitorTeam, false, false);
                } else {
                    leftTeam = teamPositions;
                    rightTeam = choosePositions(homeTeam, false, false);
                }
            }
        } else {
            leftTeam = choosePositions(homeTeam, false, true);
            rightTeam = choosePositions(visitorTeam, false, false);
        }

        CalculatedMatch match;
        // Make sure at most 1 thread can generate a match at a time (because a lot
        // of variables are static
        synchronized (lockGeneration) {
            System.out.println(!shouldAnimate ? "Thread generating" : "Main generating");
            // generate the match
            match = (new MainAIController()).createMatch(leftTeam, rightTeam, shouldAnimate);

            // reset generation variables
            CurrentPositions.reset();
        }
        System.out.println(!shouldAnimate ? "Thread done generating" : "Main done generating");

        // play the generated match's animation
        if (shouldAnimate) //only the players match should be animated, but to be safe, also synchroniza this
        {
            synchronized (lockAnimation) {
                System.out.println(!shouldAnimate ? "Thread animating" : "Main animating");
                main.playMatch(match);
//                AnimateFootballMatch.playMatch(testMatch);

                
                System.out.println(!shouldAnimate ? "Thread done animating" : "Main done animating");
            }
        }

        // get the scores
        int scoreLeft = match.getPosition(match.amoutOfFrames() - 1).getScoreLeft();
        int scoreRight = match.getPosition(match.amoutOfFrames() - 1).getScoreRight();

        // Clear the memory used to store the match.
        // This does make a huge difference (see tests information in the comments above)
        match = null;
        System.gc();

        return new Match(homeTeam, visitorTeam, scoreLeft, scoreRight);
    }

    /**
     * Get the default positions of the left team based on a team parameter
     *
     * @param t1 the team playing at the left side
     * @return Object[]: [0] = keeper, [1] = ArrayList defenders, [2] =
     * ArrayList midfielders, [3] = ArrayList defenders
     */
//    private static Object[] getDefaultLeftPositions(Team t1) {
//        Object[] result = new Object[4];
//        result[0] = new PlayerInfo(70, 70, new Position(60, 381));
//
//        // add default defender positions
//        result[1] = new ArrayList<>();
//        ((ArrayList) result[1]).add(new PlayerInfo(70, 70, 70, new Position(330, 160)));
//        ((ArrayList) result[1]).add(new PlayerInfo(70, 70, 70, new Position(288, 290)));
//        ((ArrayList) result[1]).add(new PlayerInfo(70, 70, 70, new Position(288, 467)));
//        ((ArrayList) result[1]).add(new PlayerInfo(70, 70, 70, new Position(330, 605)));
//
//        // add default midfielder positions
//        result[2] = new ArrayList<>();
//        ((ArrayList) result[2]).add(new PlayerInfo(70, 70, 70, new Position(550, 250)));
//        ((ArrayList) result[2]).add(new PlayerInfo(70, 70, 70, new Position(450, 385)));
//        ((ArrayList) result[2]).add(new PlayerInfo(70, 70, 70, new Position(550, 513)));
//
//        // add default midfielder positions
//        result[3] = new ArrayList<>();
//        ((ArrayList) result[3]).add(new PlayerInfo(70, 70, 70, new Position(713, 259)));
//        ((ArrayList) result[3]).add(new PlayerInfo(70, 70, 70, new Position(785, 385)));
//        ((ArrayList) result[3]).add(new PlayerInfo(70, 70, 70, new Position(719, 494)));
//
//        return result;
//    }

    /**
     * Get the default positions of the right team based on a team parameter
     *
     * @param t1 the team playing at the right side
     * @return Object[]: [0] = keeper, [1] = ArrayList defenders, [2] =
     * ArrayList midfielders, [3] = ArrayList defenders
     */
//    private static Object[] getDefaultRightPositions(Team t1) {
//        Object[] result = new Object[4];
//        result[0] = new PlayerInfo(70, 70, new Position(963, 381));
//
//        // add default defender positions
//        result[1] = new ArrayList<>();
//        ((ArrayList) result[1]).add(new PlayerInfo(70, 70, 70, new Position(700, 160)));
//        ((ArrayList) result[1]).add(new PlayerInfo(70, 70, 70, new Position(730, 290)));
//        ((ArrayList) result[1]).add(new PlayerInfo(70, 70, 70, new Position(730, 467)));
//        ((ArrayList) result[1]).add(new PlayerInfo(70, 70, 70, new Position(700, 605)));
//
//        // add default midfielder positions
//        result[2] = new ArrayList<>();
//        ((ArrayList) result[2]).add(new PlayerInfo(70, 70, 70, new Position(461, 250)));
//        ((ArrayList) result[2]).add(new PlayerInfo(70, 70, 70, new Position(562, 385)));
//        ((ArrayList) result[2]).add(new PlayerInfo(70, 70, 70, new Position(461, 513)));
//
//        // add default midfielder positions
//        result[3] = new ArrayList<>();
//        ((ArrayList) result[3]).add(new PlayerInfo(70, 70, 70, new Position(306, 259)));
//        ((ArrayList) result[3]).add(new PlayerInfo(70, 70, 70, new Position(233, 385)));
//        ((ArrayList) result[3]).add(new PlayerInfo(70, 70, 70, new Position(306, 494)));
//
//        return result;
//    }

    private static TeamPositions choosePositions(Team team, boolean isPlayer, boolean isHomeTeam) {
        if (isPlayer) {
            System.out.println("ERROR: STILL SELECTING POSITIONS HERE");
            Stage stage = new Stage();
            AnchorPane rootLayout;
            try {
                // Load root layout from fxml file
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("animation/Playmatch/ChoosePositions.fxml"));
                rootLayout = (AnchorPane) loader.load();

                //read a competition
                // Show the scene containing the root layout
                Scene scene = new Scene(rootLayout);
                stage.setScene(scene);
                stage.setResizable(true);

                //set pane to controller
                GameScreenChoosePositionsController controller = ((GameScreenChoosePositionsController) loader.getController());
                controller.drawCircles(stage, rootLayout, team);

                stage.showAndWait();
                return GameScreenChoosePositionsController.getTeamPositions();
//            
//            scaleToScreenSize(rootLayout);
//            
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Failed to load interface: AnchorPane");
                return null;
            }
        } else{
            TeamPositions result = new TeamPositions(team);
            if(isHomeTeam)
                result.setDefaultLeftPlayers();
            else
                result.setDefaultRightPlayers();
            
            return result;
        }
    }
}
