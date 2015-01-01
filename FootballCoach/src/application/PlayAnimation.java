package application;

import application.animation.CalculateMatch.MainAIController;
import application.animation.Container.CalculatedMatch;
import application.animation.Container.CurrentPositions;
import application.animation.Container.Position;
import application.animation.Container.PlayerInfo;
import application.animation.PlayMatch.AnimateFootballMatch;
import application.model.Match;
import application.model.Team;
import java.util.ArrayList;

/**
 * This class' playAnimation method can be used to handle everything which has
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

    /**
     * This method will make sure a football match will get generated and
     * animated, based on the 2 teams which are supplied by the parameters. It
     * will also let the player choose the positions of the players of his team.
     * Afterwards it will return a Match object, containing the results of the
     * generated match.
     *
     * @param homeTeam The home team
     * @param visitorTeam The visitor team
     * @return A Match object containing the generated match
     */
    public static Match playAnimation(Team homeTeam, Team visitorTeam) {

        // TODO: select team composition instead of the part for testing below this
        //ONLY FOR TESTING: *******************************
        //ally team:
        PlayerInfo p1 = new PlayerInfo(70, 70, new Position(60, 381));

        ArrayList<PlayerInfo> defense1 = new ArrayList<>();
        defense1.add(new PlayerInfo(70, 70, 70, new Position(330, 160)));
        defense1.add(new PlayerInfo(70, 70, 70, new Position(288, 290)));
        defense1.add(new PlayerInfo(70, 70, 70, new Position(288, 467)));
        defense1.add(new PlayerInfo(70, 70, 70, new Position(330, 605)));

        ArrayList<PlayerInfo> midfield1 = new ArrayList<>();
        midfield1.add(new PlayerInfo(70, 70, 70, new Position(550, 250)));
        midfield1.add(new PlayerInfo(70, 70, 70, new Position(450, 385)));
        midfield1.add(new PlayerInfo(70, 70, 70, new Position(550, 513)));

        ArrayList<PlayerInfo> attack1 = new ArrayList<>();
        attack1.add(new PlayerInfo(70, 70, 70, new Position(713, 259)));
        attack1.add(new PlayerInfo(70, 70, 70, new Position(785, 385)));
        attack1.add(new PlayerInfo(70, 70, 70, new Position(719, 494)));

        //enemy team:
        PlayerInfo p2 = new PlayerInfo(70, 70, new Position(963, 381));

        ArrayList<PlayerInfo> defense2 = new ArrayList<>();
        defense2.add(new PlayerInfo(70, 70, 70, new Position(700, 160)));
        defense2.add(new PlayerInfo(70, 70, 70, new Position(730, 290)));
        defense2.add(new PlayerInfo(70, 70, 70, new Position(730, 467)));
        defense2.add(new PlayerInfo(70, 70, 70, new Position(700, 605)));

        ArrayList<PlayerInfo> midfield2 = new ArrayList<>();
        midfield2.add(new PlayerInfo(70, 70, 70, new Position(461, 250)));
        midfield2.add(new PlayerInfo(70, 70, 70, new Position(562, 385)));
        midfield2.add(new PlayerInfo(70, 70, 70, new Position(461, 513)));

        ArrayList<PlayerInfo> attack2 = new ArrayList<>();
        attack2.add(new PlayerInfo(70, 70, 70, new Position(306, 259)));
        attack2.add(new PlayerInfo(70, 70, 70, new Position(233, 385)));
        attack2.add(new PlayerInfo(70, 70, 70, new Position(306, 494)));
        //************************************************* ^ ONLY FOR TESTING

        // generate the match
        CalculatedMatch testMatch = (new MainAIController()).createMatch(p1, defense1, midfield1,
                attack1, p2, defense2, midfield2, attack2);

        // play the generated match's animation
        AnimateFootballMatch.playMatch(testMatch);
        // get the scores
        int scoreLeft = testMatch.getPosition(testMatch.amoutOfFrames() - 1).getScoreLeft();
        int scoreRight = testMatch.getPosition(testMatch.amoutOfFrames() - 1).getScoreRight();

        // reset score
        CurrentPositions.reset();

        // reset animation variables
        AnimateFootballMatch.reset();

        // Clear the memory used to store the match.
        // This does make a huge difference (see tests information in the comments above)
        testMatch = null;
        System.gc();

        return new Match(homeTeam, visitorTeam, scoreLeft, scoreRight);
    }

}
