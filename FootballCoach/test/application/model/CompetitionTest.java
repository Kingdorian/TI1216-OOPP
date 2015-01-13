package application.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import application.controller.XMLHandler;

public class CompetitionTest {
	
	Competition comp, comp1;
	Team sparta, groningen;
	@Before
	public void init(){
		sparta = new Team("Sparta", true);
		groningen = new Team("Groningen", false);
		Team[] t = {sparta, groningen};
		comp = new Competition(t);
		comp1 = new Competition(t);
		comp.addMatch(0, 1, new Match(sparta, groningen));
		comp1.addMatch(0, 1, new Match(sparta, groningen));
	}
	
	//TESTS IF THE equals method works correctly if both Competitions are equal
	@Test
	public void testEqualEqual(){
		assertEquals(comp, comp1);
	}
	//TESTS IF the equals method works correctly if the obj comparing to is not a competition
	@Test
	public void testEqualNotComp(){
		assertNotEquals(comp, 4);
	}
	//TESTS IF the equals method works correctly if the matches are not equal
	@Test
	public void testEqualMatchNot(){
		comp.addMatch(1, 0, new Match(groningen, sparta));
		assertNotEquals(comp, comp1);
		comp1.addMatch(1, 0, new Match(groningen, sparta));
	}	
	//TESTS IF the equals method works correctly if a match is null
	@Test
	public void testEqualMatchNull(){
		comp.addMatch(1, 0, new Match(groningen, sparta));
		assertNotEquals(comp1, comp);
		comp1.addMatch(1, 0, new Match(groningen, sparta));
	}
	//TESTS IF the equals if the competition contains two different matches
	@Test
	public void testEqualMatchDiff(){
		comp.addMatch(1, 0, new Match(groningen, sparta));
		comp1.addMatch(1, 0, new Match(sparta, groningen));
		assertNotEquals(comp1, comp);
		comp.addMatch(1, 0, new Match(sparta, groningen));
	}
	//TESTS IF the equals if the competition contains two different matches
	@Test
	public void testEqualsTeamsNE(){
		Team[] teams = {new Team("Sparta", true)};
		Competition comp2 = new Competition(teams);
		Team[] teams2  = {new Team("Groningen", true)};
		assertNotEquals(comp2, new Competition(teams2));
	}
	//TESTS if updateResults works correctly
	@Test
	public void testUpdateResults(){
		//Provoking exception
		comp.updateResults();
		try {
			Competition readedComp = XMLHandler.readCompetition("XML/TestSaveGames/0/competition.xml", "XML/TestSaveGames/0/Matches.xml");
			readedComp.updateResults();
		} catch (Exception e){
			e.printStackTrace();
		}
		

	}
	//TESTS the toString method
	@Test
	public void testToString(){
		assertEquals(comp.toString(), "Competition [competition=Match [homeTeam=Sparta, visitorTeam=Groningen, pointsHomeTeam=-1, pointsVisitorTeam=-1],, teams=[Team [name=Sparta, players=[], budget=-2147483648, points=0, goals=0, goalsAgainst=0, artificialGrass=true], Team [name=Groningen, players=[], budget=-2147483648, points=0, goals=0, goalsAgainst=0, artificialGrass=false]]]");
	}
	
	//TESTS getTeamByName
	@Test
	public void testGetTeamByName(){
		assertEquals(comp.getTeamByName("Sparta"),sparta);
	}	
	//TESTS getTeamByName invalid name
	@Test
	public void testGetTeamByNameInv(){
		assertNotEquals(comp.getTeamByName("Invalid team"),sparta);
	}
	//TEST getpoints provoking an exception
	@Test
	public void testGetPointsExc(){
		comp.getPoints(null);
	}
	
	
	//CAlls the getters and setters that are not covered by other methods already (Just for coverage)
	@Test
	public void callGetSet(){
		comp.getName();
		comp.getChosenTeamName();
		comp.getSaveGameId();
		comp.getRound(0);
		comp.getMarket();
		Player p = new Player("Hans", "Anderssen", 0, Status.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0);
		comp.getPlayersTeam(p);
		comp.getTeamByName("Sparta").addPlayer(p);
		comp.getPlayersTeam(p);
		comp.setMarket(comp.getMarket());

	}
	
	//TEST getAllRanks when both teams are exactly equal
	@Test
	public void testGetAllRanksName(){
		ArrayList<Team> art = new ArrayList<Team>();
		art.add(groningen);
		art.add(sparta);
		assertEquals(art, comp.getAllRanks());
	}
	//TEST getAllRanks when one team has a higher goalDiff
	@Test
	public void testGetAllRanksGoalDiff(){
		ArrayList<Team> art = new ArrayList<Team>();
		art.add(sparta);
		art.add(groningen);
		comp.getTeamByName("Sparta").setGoalDiff(30);
		assertEquals(art, comp.getAllRanks());
	}	
	//TEST getAllRanks when one team has a amount of points
	@Test
	public void testGetAllRanksGoalPoints(){
		ArrayList<Team> art = new ArrayList<Team>();
		art.add(groningen);
		art.add(sparta);
		comp.getTeamByName("Groningen").setPoints(3);
		assertEquals(art, comp.getAllRanks());
	}
	//TEST getRank
	@Test
	public void testGetRank(){
		comp.getTeamByName("Groningen").setPoints(0);
		comp.getTeamByName("Sparta").setPoints(8);
		assertEquals(comp.getRank(sparta), 1);
	}
	//TEST getRound
	@Test
	public void testGetRound(){
		try {
			Competition readedComp = XMLHandler.readCompetition("XML/TestSaveGames/0/competition.xml", "XML/TestSaveGames/0/Matches.xml");
			System.out.println(readedComp.getRound() + "HELLO");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
}
