package application.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TeamTest {

	Team eqTeam1, eqTeam2, eqTeamNull;
	@Before
	public void init(){
		eqTeam1 = new Team("Knudde", false);
		eqTeam2 = new Team("Knudde", false);
		eqTeamNull = null;
	}

    @Test
    public void testTeam() {
        ArrayList<Players> ado = new ArrayList<Players>();
        ArrayList<Players> ajax = new ArrayList<Players>();
        Player player1 = new Player("Bob", "De Bouwer", 9, Card.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
        Player player2 = new Player("Bob", "De Bouwer", 9, Card.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
        ado.add(player1);
        ado.add(player2);
        ajax.add(player1);

        Team team1 = new Team("Ado Den haag", false);
        Team team2 = new Team("Ado Den haag", false);
        Team team3 = new Team("Ajax", true);
        assertEquals(team1, team2);
        assertFalse(team1.equals(team3));
        team3 = new Team("Ado Den Haag", false, "hoi");
        team1.setImgUrl("hoi");
        //assertEquals(team1, team3);
        

    }
    
	//TESTS: getAvailablePlayers()
	@Test
	public void testGetAvailablePlayers(){
        Team team1 = new Team("Ajax", false);

        Player player1 = new Player("Bob", "De Bouwer", 9, Card.DEFAULT, 0, Reason.DEFAULT, 90, 60, 80);
        Player player2 = new Player("Ken", "De Bouweer", 9, Card.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
        Player player3 = new Player("Paul", "De Bouker", 9, Card.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
        team1.addPlayer(player1);
        team1.addPlayer(player2);
        team1.addPlayer(player3);
        ArrayList<Players> playerList = new ArrayList<Players>();
        playerList.add(player1);
    	playerList.add(player2);
    	playerList.add(player3);
    	assertEquals(playerList, team1.getAvailablePlayers());
    	
	}
	@Test
	public void testGetAvailablePlayersGoalKeeper(){
        Team team1 = new Team("Ajax", false);
        Goalkeeper keeper = new Goalkeeper("Bob", "De Bouwer", 9, Card.DEFAULT, 0, Reason.DEFAULT, 90, 60);
        team1.addPlayer(keeper);
        ArrayList<Players> playerList = new ArrayList<Players>();
        playerList.add(keeper);
    	assertEquals(playerList, team1.getAvailablePlayers());
    	
	}
	@Test
	public void testGetPlayersEnoughPlayers(){
        Team team1 = new Team("Ajax", false);
        Player player1 = new Player("Bob", "De Bouwer", 9, Card.DEFAULT, 0, Reason.DEFAULT, 90, 60, 80);
        ArrayList<Players> playerList = new ArrayList<Players>();
        for(int i = 0; i<15; i++){
        	team1.addPlayer(player1);
        	playerList.add(player1);
        }
        System.out.println(playerList);
        System.out.println(team1.getAvailablePlayers());
        assertEquals(playerList, team1.getAvailablePlayers());
	}
	
	@Test
	public void testResetCardReason(){
        Team team1 = new Team("Ajax", false);
        Player player1 = new Player("Bob", "De Bouwer", 9, Card.DEFAULT, 0, Reason.DEFAULT, 90, 60, 80);
        team1.addPlayer(player1);
        team1.resetCardReason();
        assertEquals(player1.getReason(), Reason.DEFAULT);
        assertEquals(player1.getCard(), Card.DEFAULT);
        assertEquals(player1.getTimeNotAvailable(), 0);
	}
	
	@Test
	public void testGetPlayersEnoughAndKeeper(){
        Team team1 = new Team("Ajax", false);
        Player player1 = new Player("Bob", "De Bouwer", 9, Card.DEFAULT, 0, Reason.DEFAULT, 90, 60, 80);
        ArrayList<Players> playerList = new ArrayList<Players>();
        for(int i = 0; i<15; i++){
        	team1.addPlayer(player1);
        	playerList.add(player1);
        }
        Goalkeeper keeper = new Goalkeeper("Bob", "De Bouwer", 9, Card.DEFAULT, 0, Reason.DEFAULT, 90, 60);
        team1.addPlayer(keeper);
        playerList.add(keeper);
        System.out.println(playerList);
        System.out.println(team1.getAvailablePlayers());
        assertEquals(playerList, team1.getAvailablePlayers());
	}
	


    @Test
    public void testTransferTo() {
        Team team1 = new Team("Ajax", false);
        Team team2 = new Team("Ado", false);
        Team team3 = new Team("AZ", false);
        Team team4 = new Team("Excelsior", false);

        Player player1 = new Player("Bob", "De Bouwer", 9, Card.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
        Player player2 = new Player("Ken", "De Bouweer", 9, Card.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
        Player player3 = new Player("Paul", "De Bouker", 9, Card.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);

        team1.setBudget(50000);
        team3.setBudget(100000);
        team4.setBudget(50000);
        
        
        team1.transferTo(player3, team2, 50000); 

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
       
        assertNotEquals(team2.getPlayers(), team4.getPlayers());
        team2.setBudget(100000);
        team1.transferTo(player3, team2, 50000); 
        assertEquals(team1.getBudget(), team3.getBudget());
        assertEquals(team1.getPlayers(), team3.getPlayers());
        assertEquals(team2.getBudget(), team4.getBudget());
        assertEquals(team2.getPlayers(), team4.getPlayers());
        team3.setBudget(0);

    }
    /** The reason for this method is mainly coverage....
     * TEST: All getters and setters
     */
    @Test
    public void testGettersSetters(){
    	Team team1 = new Team("Knudde", false);
    	team1.setArtificialGrass(true);
    	team1.setBudget(500);
    	team1.setDraws(3);
    	team1.setGoalDiff(400);
    	team1.setGoals(10);
    	team1.setGoalsAgainst(300);
    	team1.setImgUrl("babla");
    	team1.setLosses(30);
    	team1.setName("Veenwijk");
    	team1.setPoints(40);
    	ArrayList<Players> plrs = new ArrayList<Players>();
    	plrs.add(new Player("Brian", "Hallo", 3, Card.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0));
    	team1.setPlayers(plrs);
    	team1.setWins(34);
    	assertEquals(team1.hasArtificialGrass(), true);
    	assertEquals(team1.getBudget(), 500);
    	assertEquals(team1.getDraws(), 3);
    	assertEquals(team1.getGoalDiff(), 400);
    	assertEquals(team1.getGoals(), 10);
    	assertEquals(team1.getGoalsAgainst(), 300);
    	assertEquals(team1.getImgUrl(), "babla");
    	assertEquals(team1.getLosses(), 30);
    	assertEquals(team1.getName(), "Veenwijk");
    	assertEquals(team1.getPoints(), 40);
    	assertEquals(team1.getPlayers(), plrs);
    	assertEquals(team1.getWins(), 34);
    }
	 //Test if equals works correctly if teams are equal
	@Test
	public void testEqualsNull(){
		assertNotEquals(eqTeam1, eqTeamNull);
	}
	 //Test if equals works correctly if teams are equal
	@Test
	public void testEqualsOtherObj(){
		assertNotEquals(eqTeam1, new ArrayList<Players>());
	}
		 //Test if equals works correctly if teams are equal
		@Test
		public void testEqualsEqual(){
			assertEquals(eqTeam1, eqTeam2);
		}
		//Test if ArtGrass is not equal
	   @Test
	   public void testEqualsArtGrass(){
		   	eqTeam1.setArtificialGrass(true);
	   		assertNotEquals(eqTeam1, eqTeam2);
	   		eqTeam2.setArtificialGrass(true);
	   }
	   //Test if equals works correctly if ArtGrass is not equal
	  @Test
	  public void testEqualsBudget(){
		   	eqTeam1.setBudget(200);
	  		assertNotEquals(eqTeam1, eqTeam2);
	  		eqTeam2.setBudget(200);
	  }
	  //Test if equals works correctly if Draws are not equal
	 @Test
	 public void testEqualsDraws(){
		   	eqTeam1.setDraws(4);
	 		assertNotEquals(eqTeam1, eqTeam2);
	 		eqTeam2.setDraws(4);
	 }
	 //Test if equals works correctly if GoalDiff is not equal
	@Test
	public void testEqualsGoalDiff(){
		   	eqTeam1.setGoalDiff(4);
			assertNotEquals(eqTeam1, eqTeam2);
			eqTeam2.setGoalDiff(4);
	}
	 //Test if equals works correctly if GoalDiff is not equal
	@Test
	public void testEqualsName(){
		   	eqTeam1.setName("Groningen");
			assertNotEquals(eqTeam1, eqTeam2);
			eqTeam2.setName("Groningen");
	}
	//Test if equals works if the imgURL of the first object is null
	@Test
	public void testEqualsImgUrlN(){
		assertEquals(eqTeam1, eqTeam2);
	}
	 //Test if equals works correctly if imgURL is null (both cases)s
	@Test
	public void testEqualsImgUrlNull(){
			System.out.println(eqTeam1.toString());
		   	eqTeam1.setImgUrl("URL");
			assertNotEquals(eqTeam1, eqTeam2);
			eqTeam2.setImgUrl("URL");
	}
	 //Test if equals works correctly if imgURL is not equal
	@Test
	public void testEqualsImgUrl(){
		   	eqTeam1.setImgUrl("OtherURL");
			assertNotEquals(eqTeam1, eqTeam2);
			eqTeam2.setImgUrl("OtherURL");
	}
	 //Test if equals works correctly if Goals are not equal
	@Test
	public void testEqualsGoals(){
		   	eqTeam1.setGoals(30);
			assertNotEquals(eqTeam1, eqTeam2);
			eqTeam2.setGoals(30);
	}
	 //Test if equals works correctly if GoalsAgainst are not equal
	@Test
	public void testEqualsGolsAgainst(){
		   	eqTeam1.setGoalsAgainst(30);
			assertNotEquals(eqTeam1, eqTeam2);
			eqTeam2.setGoalsAgainst(30);
	}
	 //Test if equals works correctly if Points are not equal
	@Test
	public void testEqualsPoints(){
		   	eqTeam1.setPoints(30);
			assertNotEquals(eqTeam1, eqTeam2);
			eqTeam2.setPoints(30);
	}
	 //Test if equals works correctly if Points are not equal
	@Test
	public void testEqualsLosses(){
		   	eqTeam1.setLosses(30);
			assertNotEquals(eqTeam1, eqTeam2);
			eqTeam2.setLosses(30);
	}
	 //Test if equals works correctly if Wins are not equal
	@Test
	public void testEqualsWins(){
		   	eqTeam1.setWins(30);
			assertNotEquals(eqTeam1, eqTeam2);
			eqTeam2.setWins(30);
	}
	 //Test if equals works correctly if players is not equal
	@Test
	public void testEqualsPlayers(){
    		ArrayList<Players> plrs = new ArrayList<Players>();
    		plrs.add(new Player("Brian", "Hallo", 3, Card.DEFAULT, 0, Reason.DEFAULT, 0, 0, 0));
    		eqTeam1.addPlayer(plrs.get(0));
			assertNotEquals(eqTeam1, eqTeam2);
    		eqTeam2.addPlayer(plrs.get(0));
	}
	 //Test if equals works correctly if players is not equal
	@Test
	public void testEqualsImgUrlOtherNull(){
   		eqTeam1.setImgUrl("wrong");
   		eqTeam2.setImgUrl("notWrong");
		assertNotEquals(eqTeam1,eqTeam2);
   		eqTeam2.setImgUrl("wrong");
	}
	 //Test if equals works correctly if players is not equal
	@Test
	public void testEqualName(){
  		eqTeam1.setImgUrl("wrong");
  		eqTeam2.setImgUrl("wrong");
		assertEquals(eqTeam1,eqTeam2);
	}

	 //Test if equals works correctly if players is not equal
	@Test
	public void testEqualsNameNullOtherNameNotNull(){
  		eqTeam1.setName(null);
  		eqTeam2.setName("wrong");
		assertNotEquals(eqTeam1, eqTeam2);
  		eqTeam1.setName("wrong");
	}
	 //Test if equals works correctly if players is not equal
	@Test
	public void testEqualsBothNamesNull(){
 		eqTeam1.setName(null);
 		eqTeam2.setName(null);
		assertEquals(eqTeam1, eqTeam2);
 		eqTeam1.setName("wrong");
 		eqTeam2.setName("wrong");
	}
	
	@Test
	public void testAmountGoalkeepers(){
		Team team = new Team("testteam", true);
		team.addPlayer(new Goalkeeper(null, null, 0, null, 0, null, 0, 0));
		team.addPlayer(new Player(null, null, 0, null, 0, null, 0, 0, 0));
		assertEquals(team.getAmountGoalkeepers(), 1);
	}
	
	@Test
	public void testAmountDefenders(){
		Team team = new Team("testteam", true);
		team.addPlayer(new Goalkeeper(null, null, 0, null, 0, null, 0, 0));
		team.addPlayer(new Player(null, null, 0, null, 0, null, 1, 75, 2));
		assertEquals(team.getAmountDefenders(), 1);
	}
	@Test
	public void testAmountMidfielders(){
		Team team = new Team("testteam", true);
		team.addPlayer(new Goalkeeper(null, null, 0, null, 0, null, 0, 0));
		team.addPlayer(new Player(null, null, 0, null, 0, null, 60, 60, 60));
		assertEquals(team.getAmountMidfielders(), 1);
	}
	@Test
	public void testAmountForwards(){
		Team team = new Team("testteam", true);
		team.addPlayer(new Goalkeeper(null, null, 0, null, 0, null, 0, 0));
		team.addPlayer(new Player(null, null, 0, null, 0, null, 80, 6, 60));
		assertEquals(team.getAmountForwards(), 1);
	}
	@Test
	public void testAmountAllrounders(){
		Team team = new Team("testteam", true);
		team.addPlayer(new Goalkeeper(null, null, 0, null, 0, null, 0, 0));
		team.addPlayer(new Player(null, null, 0, null, 0, null, 80, 80, 80));
		assertEquals(team.getAmountAllrounders(), 1);
	}
}
