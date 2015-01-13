package application.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CompetitionTest {
	
	Competition comp, comp1;
	Team sparta, groningen;
	@Before
	public void init(){
		sparta = new Team("Sparta", true);
		groningen = new Team("Groningen", false);
		Team[] t = {sparta, groningen};
		comp = new Competition(t);
		comp.addMatch(0, 1, new Match(0, 1));
		comp1 = comp;
	}
	
	//TESTS IF THE equals method works correctly if both Competitions are equal
	@Test
	public void testEqualEqual(){
		assertEquals(comp, comp1);
	}
	//TESTS IF the equals method works correctly if the matches are not equal
	@Test
	public void testEqualMatchNot(){
		comp.addMatch(1, 0, new Match(1, 0));
		assertNotEquals(comp, comp1);
		comp1.addMatch(1, 0, new Match(1, 0));
	}
}
