package application.modelTest;

import application.model.*;
import static org.junit.Assert.*;

import org.junit.Test;


public class PlayerTest {

	@Test
	public void testPlayer() {
		
		Player player1 = new Player("Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		Player player2 = new Player("Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		Player player3 = new Player("Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		
		assertEquals(player1, player2);
		assertNotEquals(player1, player3);
	}

}
