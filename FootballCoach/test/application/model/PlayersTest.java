package application.model;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class PlayersTest {
	Players player1, player2;
	@Before
	public void init(){
		player1 = new Player("Jan", "de Lange", 2, Card.DEFAULT, 10, Reason.DEFAULT, 80, 90, 70);
		player2 = new Player("Jan", "de Lange", 2, Card.DEFAULT, 10, Reason.DEFAULT, 80, 90, 70);
	}
	
	//TESTS IF tostring returns a correct value
	@Test
	public void testToString(){
		assertTrue(player1.toString().startsWith("Player [attack=80, defence=90, stamina=70Players [id="));
		assertTrue(player1.toString().endsWith(", name=Jan, surname=de Lange, number=2, status=DEFAULT, timeNotAvailable=10, reason=DEFAULT]]"));
	}
	//TESTS if equals returns the correct value if both objects are equal
	@Test
	public void testEqualsEqual(){
		assertEquals(player1, player2);
	}
	//TESTS if equals returns the correct value if the other object is not an instance of Players
	@Test
	public void testEqualsOtherObj(){
		assertNotEquals(player1, new ArrayList<String>());
	}
	//TESTS if equals returns the correct value if the names are different
	@Test
	public void testEqualsName(){
		String buffer = player1.getName();
		player1.setName("othername");
		assertNotEquals(player1, player2);
		player1.setName(buffer);
	}
	//TESTS if equals returns the correct value if the surnames are different
	@Test
	public void testEqualsSurName(){
		String buffer = player1.getSurName();
		player1.setSurName("surname");
		assertNotEquals(player1, player2);
		player1.setSurName(buffer);
	}
	//TESTS if equals returns the correct value if the numbers are different
	@Test
	public void testEqualsNumber(){
		int buffer = player1.getNumber();
		player1.setNumber(-1);
		assertNotEquals(player1, player2);
		player1.setNumber(buffer);
	}
	
	//TESTS if equals returns the correct value if the cards are different
	@Test
	public void testEqualsCards(){
		Card buffer = player1.getCard();
		player1.setCard(Card.RED);
		assertNotEquals(player1, player2);
		player1.setCard(buffer);
	}
	//TESTS if equals returns the correct value if the reasons are different
	@Test
	public void testEqualsReason(){
		Reason buffer = player1.getReason();
		player1.setReason(Reason.ANKEL);
		assertNotEquals(player1, player2);
		player1.setReason(buffer);
	}
	
	//TESTS if equals returns the correct value if the timeNotAvailable is different
	@Test
	public void testEqualsTimeNotAvailable(){
		int buffer = player1.getTimeNotAvailable();
		player1.setTimeNotAvailable(3);
		assertNotEquals(player1, player2);
		player1.setTimeNotAvailable(buffer);
	}
	
	//TESTS if a players Id is valid
	@Test
	public void testId(){
		assertTrue(player1.getId()>=0);
		assertTrue(player1.getId()<=Integer.MAX_VALUE);
	}
	
	//TESTS if Getprice returns a correct price
	@Test
	public void testGetPrice(){
		assertEquals(player1.getPrice(), 7630000.00,  1e-7);
	}
	//TESTS if getAbilityStr returns a string when the getAbility on the object is less then 4 chars long
	@Test
	public void testGetAbilityStr(){
		Player player3 = new Player("Jan", "de Lange", 2, Card.DEFAULT, 10, Reason.DEFAULT, 0, 0, 0);
		assertEquals(player1.getAbilityStr(),"4.00");
	}
	
	//TESTS if getAbilityStr returns a string when the getAbility on the object is more then 4 chars long
	@Test
	public void testGetAbilityStrLong(){
		Player player3 = new Player("Jan", "de Lange", 2, Card.DEFAULT, 10, Reason.DEFAULT, 3, 0, 0);
		assertEquals(player3.getAbilityStr(), "0.07");
	}
	
	
	//TESTS if Players.isAvailable() returns the correct value
	@Test
	public void testIsAvailableFalse(){
		Card buffer = player1.getCard();
		player1.setCard(Card.RED);
		assertFalse(player1.isAvailable());
		player1.setCard(buffer);
	}
	
	//TESTS if Players.isAvailable() returns the correct value
	@Test
	public void testIsAvailableTrue(){
		int buffer = player1.getTimeNotAvailable();
		player1.setTimeNotAvailable(0);
		assertTrue(player1.isAvailable());
		player1.setTimeNotAvailable(buffer);
	}
}
