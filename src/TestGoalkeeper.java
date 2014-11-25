import static org.junit.Assert.*;

import org.junit.Test;


public class TestGoalkeeper {

	@Test
	public void testGoalkeeper() {
		
		Goalkeeper player1 = new Goalkeeper(1, "Bob", "De Bouwer", 3, 1, 0, 0, 80, 60);
		Goalkeeper player2 = new Goalkeeper(1, "Bob", "De Bouwer", 3, 1, 0, 0, 80, 60);
		Goalkeeper player3 = new Goalkeeper(2, "Bob", "De Bouwer", 3, 1, 0, 0, 80, 60);
		
		assertEquals(player1, player2);
		assertNotEquals(player1, player3);
	}

}
