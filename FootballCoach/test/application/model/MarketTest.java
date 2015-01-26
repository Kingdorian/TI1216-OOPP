package application.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

public class MarketTest {

	Market market;
	Player player;
	ArrayList<Players> playerList = new ArrayList<Players>();
	
	@Before
	public void init(){
		market = new Market();
		player = new Player("henk", "hendriks", 0, Card.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0);
		market.addPlayer(player, 300);
		playerList.add(player);
	}
	//TESTS if getPlayersForSale works correctly 
	@Test
	public void testGetPlayersForSale() {
		ArrayList<Players> playerList = new ArrayList<Players>();
		playerList.add(player);
		assertEquals(market.getPlayersForSale(), playerList);
	}
	
	//TESTS if the addPlayer works correctly when the player is already on the market
	@Test
	public void testAddPlayerAlreadyOn(){
		market.addPlayer(player, 4000);
		assertEquals(market.getPlayersForSale(), playerList);
	}
	
	
	//TESTS if the removeplayer function works correctly
	@Test
	public void testRemovePlayer(){
		market.addPlayer(new Player("Freek", "Frans", 0, Card.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0), 3000);
		market.removePlayer(new Player("Freek", "Frans", 0, Card.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0));
		assertEquals(playerList, market.getPlayersForSale());
	}
	
	//TESTS if te getPlayersPrice works correctly
	@Test
	public void testGetPlayersPrice(){
		market.addPlayer(new Player("Freek", "Frans", 0, Card.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0), 3000);
		assertEquals(3000, market.getPlayersPrice(new Player("Freek", "Frans", 0, Card.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0)));
		assertEquals(0, new Market().getPlayersPrice(player));
		market.removePlayer(new Player("Freek", "Frans", 0, Card.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0));
	}
	
	@Test
	public void testBuyPlayers() {
		Player player1 = new Player("Freek", "Frans", 0, Card.DEFAULT, 0, Reason.DEFAULT, 200, 20, 50);
		Player player2 = new Player("Freek", "Frant", 0, Card.DEFAULT, 0, Reason.DEFAULT, 20, 200, 50);
		Goalkeeper player5 = new Goalkeeper("Freek", "Frank", 0, Card.DEFAULT, 0, Reason.DEFAULT, 200, 200);
		
		Team team1 = new Team("Team1", true);
		Team team2 = new Team("Team2", true);
		Team team3 = new Team("Team3", true);

		team2.addPlayer(player5);
		team2.addPlayer(player2);
		team2.addPlayer(player1);
		team3.addPlayer(player1);
		team3.addPlayer(player2);
		team3.addPlayer(player5);
		
		team1.setBudget(50000000);
		team2.setBudget(50000000);
		Team[] teams = {team1, team3};
		Competition competition = new Competition(teams);
		competition.getMarket().addPlayer(player1, 5000);
		competition.getMarket().addPlayer(player2, 5000);
		competition.getMarket().addPlayer(player5, 5000);
		competition.getMarket().computerBuyPlayer(competition);
		assertEquals(team1.getPlayers(), team2.getPlayers());
	}
	
	@Test
	public void buyPlayerTest2(){
		Player player1 = new Player("Freek", "Frans", 0, Card.DEFAULT, 0, Reason.DEFAULT, 200, 20, 50);
		Player player2 = new Player("Freek", "Frant", 0, Card.DEFAULT, 0, Reason.DEFAULT, 20, 200, 50);
		Goalkeeper player3 = new Goalkeeper("Freek", "Frank", 0, Card.DEFAULT, 0, Reason.DEFAULT, 200, 200);
		
		Team team1 = new Team("Team1", true);
		Team team2 = new Team("Team2", true);
		Team team3 = new Team("Team3", true);

		team3.addPlayer(player1);
		team3.addPlayer(player2);
		team3.addPlayer(player3);
		
		team1.setBudget(5);
		Team[] teams = {team1, team3};
		Competition competition = new Competition(teams);
		competition.getMarket().addPlayer(player1, 5000);
		competition.getMarket().addPlayer(player2, 5000);
		competition.getMarket().addPlayer(player3, 5000);
		competition.getMarket().computerBuyPlayer(competition);
		assertEquals(team1.getPlayers(), team2.getPlayers());
	}
	
	@Test
	public void buyPlayerTest3(){
		Team team1 = new Team("Team 1", true);
		Team team2 = new Team("Team 2", true);
		Team team3 = new Team("Team 3", true);
		Team team4 = new Team("Team 4", true);
		Goalkeeper GK = new Goalkeeper("Freek", "Frank", 0, Card.DEFAULT, 0, Reason.DEFAULT, 2, 2);
		Goalkeeper GK2 = new Goalkeeper("Freek", "Frank", 0, Card.DEFAULT, 0, Reason.DEFAULT, 2000, 2000);
		Player Def2 = new Player("Freek", "Frant", 0, Card.DEFAULT, 0, Reason.DEFAULT, 0, 3000, 100);
		Player Mid2 = new Player("Frank", "Best", 0, Card.DEFAULT, 0, Reason.DEFAULT, 3000, 3000, 100);
		Player For2 = new Player("Frank", "Best", 0, Card.DEFAULT, 0, Reason.DEFAULT, 3000, 0, 100);
		for(int i = 0; i < 4; i++){
			team1.addPlayer(GK2);
			team3.addPlayer(GK2);
		}
		for(int i = 0; i < 200; i++){
			team1.addPlayer(Def2);
			team3.addPlayer(Def2);
		}
		for(int i = 0; i < 20; i++){
			team1.addPlayer(Mid2);
			team3.addPlayer(Mid2);
		}
		for(int i = 0; i < 20; i++){
			team1.addPlayer(For2);
			team3.addPlayer(For2);
		}
		
		for(int i = 0; i < 6; i++){
			team2.addPlayer(GK2);
			team4.addPlayer(GK2);
		}
		for(int i = 0; i < 1; i++){
			team2.addPlayer(Def2);
			team4.addPlayer(Def2);
		}
		for(int i = 0; i < 1; i++){
			team2.addPlayer(Mid2);
			team4.addPlayer(Mid2);
		}
		for(int i = 0; i < 1; i++){
			team2.addPlayer(For2);
			team4.addPlayer(For2);
		}
		
		Team[] teams = {team1, team2};
		Competition competition = new Competition(teams);
		competition.getMarket().computerBuyPlayer(competition);
		competition.getMarket().addPlayer(GK, 2000);
		assertEquals(team1.getPlayers(), team3.getPlayers());
		assertEquals(team2.getPlayers(), team4.getPlayers());
	}
	
	@Test
	public void sellPlayerTest(){
		Team team1 = new Team("Team 1", true);
		Goalkeeper GK = new Goalkeeper("Freek", "Frank", 0, Card.DEFAULT, 0, Reason.DEFAULT, 20, 20);
		Player Def = new Player("Freek", "Frant", 0, Card.DEFAULT, 0, Reason.DEFAULT, 0, 30, 0);
		Player For = new Player("Frank", "Best", 0, Card.DEFAULT, 0, Reason.DEFAULT, 30, 0, 0);
		Goalkeeper GK2 = new Goalkeeper("Freek", "Frank", 0, Card.DEFAULT, 0, Reason.DEFAULT, 2000, 2000);
		Player Def2 = new Player("Freek", "Frant", 0, Card.DEFAULT, 0, Reason.DEFAULT, 0, 3000, 100);
		Player Mid2 = new Player("Frank", "Best", 0, Card.DEFAULT, 0, Reason.DEFAULT, 3000, 3000, 100);
		Player For2 = new Player("Frank", "Best", 0, Card.DEFAULT, 0, Reason.DEFAULT, 3000, 0, 100);
		for(int i = 0; i < 4; i++){
			team1.addPlayer(GK2);
		}
		for(int i = 0; i < 20; i++){
			team1.addPlayer(Def2);
		}
		for(int i = 0; i < 20; i++){
			team1.addPlayer(Mid2);
		}
		for(int i = 0; i < 20; i++){
			team1.addPlayer(For2);
		}
		team1.addPlayer(GK);
		team1.addPlayer(Def);
		team1.addPlayer(For);
		
		Team[] teams = {team1};
		Competition competition = new Competition(teams);
		
		competition.getMarket().computerSellPlayer(competition);
		ArrayList<Players> market = competition.getMarket().getPlayersForSale();
		ArrayList<Players> test = new ArrayList<Players>();
		test.add(Def);
		test.add(GK);
		test.add(For);
		
		assertEquals(market, test);
	}
	
	@Test
	public void sellPlayersTest2(){
		Team team1 = new Team("Team 1", true);
		Team team2 = new Team("Team 2", true);
		Team team3 = new Team("Team 3", true);
		Goalkeeper GK = new Goalkeeper("Freek", "Frank", 0, Card.DEFAULT, 0, Reason.DEFAULT, 2000, 2000);
		Player Def = new Player("Freek", "Frant", 0, Card.DEFAULT, 0, Reason.DEFAULT, 0, 3000, 100);
		Player Mid2 = new Player("Freek", "Frant", 0, Card.DEFAULT, 0, Reason.DEFAULT, 40, 40, 0);
		Player Mid = new Player("Frank", "Best", 0, Card.DEFAULT, 0, Reason.DEFAULT, 74, 74, 1000);
		Player For = new Player("Frank", "Best", 0, Card.DEFAULT, 0, Reason.DEFAULT, 3000, 0, 100);
		Player All = new Player("Frank", "Best", 0, Card.DEFAULT, 0, Reason.DEFAULT, 3000, 3000, 100);
		for(int i = 0; i < 1; i++){
			team1.addPlayer(GK);
		}
		for(int i = 0; i < 2; i++){
			team1.addPlayer(Def);
		}
		for(int i = 0; i < 30; i++){
			team1.addPlayer(Mid);
		}
		for(int i = 0; i < 2; i++){
			team1.addPlayer(For);
		}
		team1.addPlayer(All);
		team1.addPlayer(Mid2);
		
		Team[] teams = {team1, team2};
		Competition competition = new Competition(teams);
		
		competition.getMarket().computerSellPlayer(competition);
		ArrayList<Players> market = competition.getMarket().getPlayersForSale();
		ArrayList<Players> test = new ArrayList<Players>();

		
		assertEquals(market, test);
		test.add(Mid2);
		assertEquals(team2.getPlayers(), team3.getPlayers());
	}
}
