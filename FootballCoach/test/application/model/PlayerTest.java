package application.model;

import application.model.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class PlayerTest {
	Player player1, player2, player3;
	
	@Before
	public void init() {
		player1 = new Player("Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		player2 = new Player("Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		player3 = new Player("Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 30, 30, 40);
	}
	//TESTS: playerPlayer(String name, String surName, int number, Status status, int timeNotAvailable, Reason reason, int attack, int defence, int stamina)
	//USES: equals
	@Test
	public void testConstructor(){
		Player player = new Player("Bob", "De Bouwer", 9, Status.DEFAULT, 5, Reason.DEFAULT, 90, 60, 80);
		assertTrue(player instanceof Player);
	}
	//TESTS: getAttack()
	@Test
	public void testGetAttack(){
		assertEquals(90, player1.getAttack());
		assertEquals(30, player3.getAttack());
	}
	//TESTS: setAttack()
	//USES: getAttack()
	@Test
	public void testSetAttack(){
		player1.setAttack(30);
		assertEquals(30, player1.getAttack());
	}
	//TESTS: getDefence()
	@Test
	public void testGetDefence(){
		assertEquals(60, player1.getDefence());
		assertEquals(30, player3.getDefence());
	}
	//TESTS: setDefence()
	//USES: getDefence()
	@Test
	public void testSetDefence(){
		player1.setDefence(30);
		assertEquals(30, player1.getDefence());
	}
	//TESTS: getStamina()
	@Test
	public void testGetStamina(){
		assertEquals(80, player1.getStamina());
		assertEquals(40, player3.getStamina());
	}
	//TESTS: setStamina()
	//USES: getStamina()
	@Test
	public void testSetStamina(){
		player1.setStamina(30);
		assertEquals(30, player1.getStamina());
	}
	//TESTS: toString()
	@Test
	public void testToString(){
		assertEquals("Player [attack=90, defence=60, stamina=80Players [id="+player1.getId() +", name=Bob, surname=De Bouwer, number=9, status=DEFAULT, timeNotAvailable=5, reason=DEFAULT]]", player1.toString());
	}
	//TESTS: equals() other object
	@Test 
	public void testEqualsOtherObject(){
		assertFalse(player1.equals(""));
	}
	//TESTS: equals() attack not equal
	//USES: player.setAttack()
	@Test
	public void testEqualsNotAttack(){
		player1.setAttack(30);
		assertNotEquals(player1, player2);
		player1.setAttack(90);
	}
	//TESTS: equals() attack not equal
	//USES: player.setDefence()
	@Test
	public void testEqualsNotDefence(){
		player1.setDefence(30);
		assertNotEquals(player1, player2);
		player1.setDefence(9);
	}
	//TESTS: equals() stamina not equal
	//USES: player.setStamina()
	@Test
	public void testEqualsNotStamina(){
		player1.setStamina(30);
		assertNotEquals(player1, player2);
		player1.setStamina(80);
	}
	//TESTS: equals() with super class not equal
	//USES: players.setName()
	@Test
	public void testEqualsNotName(){
		player1.setName("Mario");
		assertNotEquals(player1, player2);
		player1.setName("Bob");
	}
	//TESTS: equals()
	@Test
	public void testEquals(){
		assertEquals(player1, player2);
	}
	//TESTS: getAbility() if attack is higher then defence
	@Test
	public void testAbilityAt(){
		assertEquals(player1.getAbility(), 4.1904761904, 1e-7 );
	}
	//TESTS: getAbility() if defence is higer then attack
	@Test
	public void testAbilityDef(){
		player1.setAttack(20);
		player1.setDefence(40);
		assertEquals(player1.getAbility(), 2.904761904, 1e-7);
	}
	//TESTS: getKind() if player is allround
	@Test
	public void testGetKindAllr(){
		player1.setAttack(80);
		player1.setDefence(80);
		assertEquals(player1.getKind(), "Allrounder");
	}
	//TESTS: getKind() if player is a forward playing player
	@Test
	public void testGetKindFor(){
		player1.setAttack(80);
		player1.setDefence(50);
		assertEquals(player1.getKind(), "Forward");
	}
	//TESTS: getKind() if player is a backwards playing player
	@Test
	public void testGetKindDef(){
		player1.setAttack(50);
		player1.setDefence(80);
		assertEquals(player1.getKind(), "Defender");
	}
	//TESTS: getKind() if player is a midfield playing player
	@Test
	public void testGetKindMid(){
		player1.setAttack(50);
		player1.setDefence(50);
		assertEquals(player1.getKind(), "Midfielder");
	}
	
	
}
