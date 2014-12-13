package application.model;

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
        assertFalse(team1.equals(team3));

    }

    @Test
    public void testTransferTo() {
        Team team1 = new Team("Ajax", false);
        Team team2 = new Team("Ado", false);
        Team team3 = new Team("AZ", false);
        Team team4 = new Team("Excelsior", false);

        Player player1 = new Player("Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
        Player player2 = new Player("Ken", "De Bouweer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
        Player player3 = new Player("Paul", "De Bouker", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);

        team1.setBudget(50000);
        team2.setBudget(100000);
        team3.setBudget(100000);
        team4.setBudget(50000);

        team1.addPlayer(player1);
        team1.addPlayer(player2);
        team1.addPlayer(player3);
        team2.addPlayer(player1);
        team2.addPlayer(player2);
        team3.addPlayer(player1);
        team3.addPlayer(player2);
        team4.addPlayer(player1);
        team4.addPlayer(player2);
        team4.addPlayer(player3);

        team1.transferTo(player3, team2, 50000);

        assertEquals(team1.getBudget(), team3.getBudget());
        assertEquals(team1.getPlayers(), team3.getPlayers());
        assertEquals(team2.getBudget(), team4.getBudget());
        assertEquals(team2.getPlayers(), team4.getPlayers());
    }

}
