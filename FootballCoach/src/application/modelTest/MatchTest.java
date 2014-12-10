package application.modelTest;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author faris
 */
public class MatchTest {
    Match match;
    Team homeTeam;
    Team outTeam;
    ArrayList<Players> homeList;
    ArrayList<Players> visitorList;
    
    @Before
    public void intialize(){
        homeTeam = new Team("home", 100000, 0, 0, 0, false);
        outTeam = new Team("out", 100000, 0, 0, 0, false);
        
        homeList = new ArrayList<>();
        homeList.add(new Goalkeeper("name", "sname", 0, Status.DEFAULT, 0, Reason.DEFAULT, 100, 100));
        for(int i=1; i<11; i++)
            homeList.add(new Player("name", "sname", i, Status.DEFAULT, 0, Reason.DEFAULT, 100, 100 , 100));
        homeTeam.setPlayers(homeList);
        
        visitorList = new ArrayList<>();
        visitorList.add(new Goalkeeper("name", "sname", 0, Status.DEFAULT, 0, Reason.DEFAULT, 100, 100));
        for(int i=1; i<11; i++)
            visitorList.add(new Player("name", "sname", i, Status.DEFAULT, 0, Reason.DEFAULT, 100, 100 , 100));
        outTeam.setPlayers(visitorList);
        
        match = new Match(homeTeam, outTeam, 2, 3);
    }
    
    public MatchTest() {
    }

    /**
     * Test of getHomeTeam method, of class Match.
     */
    @Test
    public void testGetHomeTeam() {
        assertTrue(homeTeam.equals(match.getHomeTeam()));
    }

    /**
     * Test of setHomeTeam method, of class Match.
     */
    @Test
    public void testSetHomeTeam() {
        Match newMatch = new Match(null, null, 0, 0);
        newMatch.setHomeTeam(homeTeam);
        assertTrue(newMatch.getHomeTeam().equals(homeTeam));
    }

    /**
     * Test of getVisitorTeam method, of class Match.
     */
    @Test
    public void testGetVisitorTeam() {
        assertTrue(outTeam.equals(match.getVisitorTeam()));
    }

    /**
     * Test of setVisitorTeam method, of class Match.
     */
    @Test
    public void testSetVisitorTeam() {
        Match newMatch = new Match(null, null, 0, 0);
        newMatch.setVisitorTeam(outTeam);
        assertTrue(newMatch.getVisitorTeam().equals(outTeam));
    }

    /**
     * Test of getPointsHomeTeam method, of class Match.
     */
    @Test
    public void testGetPointsHomeTeam() {
        assertTrue(match.getPointsHomeTeam() == 2);
    }

    /**
     * Test of setPointsHomeTeam method, of class Match.
     */
    @Test
    public void testSetPointsHomeTeam() {
        match.setPointsHomeTeam(5);
        assertTrue(match.getPointsHomeTeam() == 5);
    }

    /**
     * Test of getPointsVisitorTeam method, of class Match.
     */
    @Test
    public void testGetPointsVisitorTeam() {
        assertTrue(match.getPointsVisitorTeam() == 3);
    }

    /**
     * Test of setPointsVisitorTeam method, of class Match.
     */
    @Test
    public void testSetPointsVisitorTeam() {
        match.setPointsVisitorTeam(5);
        assertTrue(match.getPointsVisitorTeam() == 5);
    }
    
}
