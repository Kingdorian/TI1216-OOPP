package application.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class MarketTest {

	Market market;
	Player player;
	ArrayList<Players> playerList = new ArrayList<Players>();
	
	@Before
	public void init(){
		market = new Market();
		player = new Player("henk", "hendriks", 0, Status.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0);
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
		market.addPlayer(new Player("Freek", "Frans", 0, Status.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0), 3000);
		market.removePlayer(new Player("Freek", "Frans", 0, Status.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0));
		assertEquals(playerList, market.getPlayersForSale());
	}
	
	//TESTS if te getPlayersPrice works correctly
	@Test
	public void testGetPlayersPrice(){
		market.addPlayer(new Player("Freek", "Frans", 0, Status.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0), 3000);
		assertEquals(3000, market.getPlayersPrice(new Player("Freek", "Frans", 0, Status.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0)));
		assertEquals(0, new Market().getPlayersPrice(player));
		market.removePlayer(new Player("Freek", "Frans", 0, Status.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0));
	}
}
