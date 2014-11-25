import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class TestTeam {

	@Test
	public void testTeam() {
		ArrayList<Players> ado = new ArrayList<Players>();
		ArrayList<Players> ajax = new ArrayList<Players>();
		Player player1 = new Player(1, "Bob", "De Bouwer", 9, 0, 5, 1, 90, 60, 80);
		Player player2 = new Player(2, "Bob", "De Bouwer", 9, 0, 5, 1, 90, 60, 80);
		ado.add(player1);
		ado.add(player2);
		ajax.add(player1);
		
		Team team1 = new Team("Ado Den haag", ado, 2, "Groen Geel", 100000, 62, 92, 2);
		Team team2 = new Team("Ado Den haag", ado, 2, "Groen Geel", 100000, 62, 92, 2);
		Team team3 = new Team("Ajax", ajax, 1, "Rood Wit", 100000, 62, 92, 2);
		
		assertEquals(team1, team2);
		assertNotEquals(team1, team3);
		
	}

}
