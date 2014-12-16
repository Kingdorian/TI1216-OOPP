package application.modelTest;

import application.model.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class GoalKeeperTest {
	Goalkeeper player1, player2, player3;
	@Before
	public void init() {
		player1 = new Goalkeeper("Bob", "De Bouwer", 3, Status.DEFAULT, 0, Reason.DEFAULT, 80, 60);
		player2 = new Goalkeeper("Bob", "De Bouwer", 3, Status.DEFAULT, 0, Reason.DEFAULT, 80, 60);
		player3 = new Goalkeeper("Bob", "De Bouwer", 3, Status.DEFAULT, 0, Reason.DEFAULT, 20, 30);
	}
	//TESTS: constructor
	@Test
	public void testConstructor(){
		assertTrue(player1 instanceof Goalkeeper);
	}
	//TESTS: getStopPower()
	@Test
	public void testGetStopPower(){
		assertEquals(80, player1.getStopPower());
		assertEquals(20, player3.getStopPower());
	}
	//TESTS: setStopPower()
	//USES: setStopPower()
	@Test
	public void testSetStopPower(){
		player1.setStopPower(30);
		assertEquals(30, player1.getStopPower());
	}
	//TESTS: getPenaltyStopPower()
	@Test
	public void testGetPenaltyStopPower(){
		assertEquals(60, player1.getPenaltyStopPower());
	}
	//TESTS: setPenaltyStopPower()
	//USES: getPenaltyStopPower()
	@Test
	public void testSetPenaltyStopPower(){
		player1.setPenaltyStopPower(100);
		assertEquals(100, player1.getPenaltyStopPower());
	}
	//TESTS: toString()
	@Test
	public void testToString(){
		assertEquals("Goalkeeper [stopPower=20, penaltyStopPower=30, Players [name=Bob, surname=De Bouwer, number=3, status=DEFAULT, timeNotAvailable=0, reason=DEFAULT]]", player3.toString());
	}
	//TESTS: equals() if the object is not an instance of goalkeeper
	@Test
	public void testEqualsOtherObj(){
		assertFalse(player3.equals(""));
	}
	//TEST: equals() i stoppower is not equal
	@Test
	public void testEqualsStopPower(){
		player2.setStopPower(100);
		player3.setStopPower(20);
		player2.setPenaltyStopPower(10);
		player3.setPenaltyStopPower(10);
		assertFalse(player2.equals(player3));
		player2.setStopPower(80);
	}
	//TEST: equals() i penaltyStopPower is not equal
	@Test
	public void testEqualsPenaltyStopPower(){
		player2.setPenaltyStopPower(100);
		assertFalse(player3.equals(player2));
		player2.setStopPower(60);
	}
	//TEST: equals() if super objects are not equal
	@Test
	public void testEqualsSuper(){
		player2.setName("Mario");
		assertFalse(player3.equals(player2));
		player2.setName("Bob");
	}
	//TEST: equals() if both objects are equal
	@Test
	public void testEquals(){
		assertEquals(player1, player2);
		System.out.println("howdy");
	}
}