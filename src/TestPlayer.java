import static org.junit.Assert.*;

import org.junit.Test;


public class TestPlayer {

	@Test
	public void testPlayer() {
		
		Player player1 = new Player(1, "Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		Player player2 = new Player(1, "Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		Player player3 = new Player(2, "Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		
		assertEquals(player1, player2);
		assertNotEquals(player1, player3);
	}

}
