package application.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import application.model.Goalkeeper;
import application.model.Match;
import application.model.Player;
import application.model.Players;
import application.model.Reason;
import application.model.Status;
import application.model.Team;
import java.util.ArrayList;
import java.util.Random;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author faris
 */
public class GenerateMatchTest {

    Team homeTeam;
    Team outTeam;
    ArrayList<Players> homeList;
    ArrayList<Players> visitorList;

    @Before
    public void intialize() {
        homeTeam = new Team("home", true);
        outTeam = new Team("out", false);

        homeList = new ArrayList<>();
        homeList.add(new Goalkeeper("name", "sname", 0, Status.DEFAULT, 0, Reason.DEFAULT, 100, 100));
        for (int i = 1; i < 11; i++) {
            homeList.add(new Player("name", "sname", i, Status.DEFAULT, 0, Reason.DEFAULT, 100, 100, 100));
        }
        homeTeam.setPlayers(homeList);

        visitorList = new ArrayList<>();
        visitorList.add(new Goalkeeper("name", "sname", 0, Status.DEFAULT, 0, Reason.DEFAULT, 80, 80));
        for (int i = 1; i < 11; i++) {
            visitorList.add(new Player("name", "sname", i, Status.DEFAULT, 0, Reason.DEFAULT, 80, 80, 80));
        }
        outTeam.setPlayers(visitorList);
    }

    public GenerateMatchTest() {
    }

    /**
     * Test of generateMatch method, of class GenerateMatch.
     */
    @Test
    public void testGenerateMatch_Team_Team() {
        Match match = GenerateMatch.generateMatch(homeTeam, outTeam);
        assertTrue(match != null && match.getHomeTeam() != null && match.getVisitorTeam() != null);
        // other part of this is covered in the test below called: testGenerateMatch_4args
    }

    /**
     * Test of generateMatch method, of class GenerateMatch.
     */
    @Test
    public void testGenerateMatchArtificialGrassHome_4args() {
        // Test if we get the right results with a thousand random numbers. Only home team artificial grass.
        for (int i = 0; i < 1000; i++) {
            long seed1 = new Random().nextLong();
            long seed2 = new Random().nextLong();
            Match match = GenerateMatch.generateMatch(homeTeam, outTeam, seed1, seed2);

            int teamHomePower = (int) (11 * 3 * 100 * 1.05); // *1.05 becaus of artificial grass
            int teamOutPower = 11 * 3 * 80;
            int homeGoals = (int) ((new Random(seed1).nextDouble() * teamHomePower / teamOutPower * 3));
            int visitorGoals = (int) ((new Random(seed2).nextDouble() * teamOutPower / teamHomePower * 3));

            assertTrue(homeGoals == match.getPointsHomeTeam() && visitorGoals == match.getPointsVisitorTeam());
        }
    }

    /**
     * Test if we get the right results with a hundred random numbers. Both
     * normal grass.
     */
    @Test
    public void testGenerateMatchNormalGrass_4args() {
        homeTeam.setArtificialGrass(false);
        for (int i = 0; i < 100; i++) {
            long seed1 = new Random().nextLong();
            long seed2 = new Random().nextLong();
            Match match = GenerateMatch.generateMatch(homeTeam, outTeam, seed1, seed2);

            int teamHomePower = (int) (11 * 3 * 100);
            int teamOutPower = 11 * 3 * 80;
            int homeGoals = (int) ((new Random(seed1).nextDouble() * teamHomePower / teamOutPower * 3));
            int visitorGoals = (int) ((new Random(seed2).nextDouble() * teamOutPower / teamHomePower * 3));

            assertTrue(homeGoals == match.getPointsHomeTeam() && visitorGoals == match.getPointsVisitorTeam());
        }
    }

    /**
     * Test if we get the right results with a hundred random numbers. Both
     * artificial grass.
     */
    @Test
    public void testGenerateMatchArtificialGrass_4args() {
        outTeam.setArtificialGrass(true);
        for (int i = 0; i < 100; i++) {
            long seed1 = new Random().nextLong();
            long seed2 = new Random().nextLong();
            Match match = GenerateMatch.generateMatch(homeTeam, outTeam, seed1, seed2);

            int teamHomePower = (int) (11 * 3 * 100);
            int teamOutPower = 11 * 3 * 80;
            int homeGoals = (int) ((new Random(seed1).nextDouble() * teamHomePower / teamOutPower * 3));
            int visitorGoals = (int) ((new Random(seed2).nextDouble() * teamOutPower / teamHomePower * 3));

            assertTrue(homeGoals == match.getPointsHomeTeam() && visitorGoals == match.getPointsVisitorTeam());
        }
    }

    /**
     * Test if we get the right results with a hundred random numbers. Only
     * visitor team artificial grass.
     */
    @Test
    public void testGenerateMatchArtificialVisitor_4args() {
        outTeam.setArtificialGrass(true);
        homeTeam.setArtificialGrass(false);
        for (int i = 0; i < 100; i++) {
            long seed1 = new Random().nextLong();
            long seed2 = new Random().nextLong();
            Match match = GenerateMatch.generateMatch(homeTeam, outTeam, seed1, seed2);

            int teamHomePower = (int) (11 * 3 * 100 * 1.05);
            int teamOutPower = 11 * 3 * 80;
            int homeGoals = (int) ((new Random(seed1).nextDouble() * teamHomePower / teamOutPower * 3));
            int visitorGoals = (int) ((new Random(seed2).nextDouble() * teamOutPower / teamHomePower * 3));

            assertTrue(homeGoals == match.getPointsHomeTeam() && visitorGoals == match.getPointsVisitorTeam());
        }
    }
}
