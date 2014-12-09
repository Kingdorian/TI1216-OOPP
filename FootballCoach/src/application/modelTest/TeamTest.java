package application.modelTest;

import application.model.*;
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;


public class TeamTest {

	@Test
	public void testTeam() {
		ArrayList<Players> ado = new ArrayList<Players>();
		ArrayList<Players> ajax = new ArrayList<Players>();
		Player player1 = new Player("Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		Player player2 = new Player("Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		ado.add(player1);
		ado.add(player2);
		ajax.add(player1);
		
		Team team1 = new Team("Ado Den haag", false);
		Team team2 = new Team("Ado Den haag", false);
		Team team3 = new Team("Ajax", true);
		
		assertEquals(team1, team2);
		assertNotEquals(team1, team3);
		
	}

}
