package application.controllerTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;

import application.controller.*;
import application.model.*;

public class SaveGameHandlerTest {
	
	@Test
	public void testloadCompetition(){
		try {
			Competition genComp = SaveGameHandler.loadCompetition(1);
			Competition refComp = XMLHandler.readCompetition("XML/Savegames/1/competition.xml", "XML/Savegames/1/Matches.xml");
			assertEquals(genComp, refComp);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");

		}

	}
	/**
	 * Tests if the loadCompetition method throws an exception when an invalid id is entered
	 * @throws the expected Exception 
	 */
	@Test(expected = Exception.class)
	public void testloadCompetitionInv() throws Exception{
		SaveGameHandler.loadCompetition(-30);
	}
	/**
	 * Test if the ArrayList of savegame ids is correctly loaded
	 */
	@Test
	public void testGetSaveGames(){
		try{
			ArrayList<Integer> list = SaveGameHandler.getSaveGames();
			ArrayList<Integer> reflist = new ArrayList<Integer>();
			reflist.add(-1);
			reflist.add(1);
			reflist.add(2);
			assertEquals(list, reflist);
		}catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}

	}
	/**
	 * Tests if the correct last eddited day is retrieved
	 */
	@Test
	public void testGetDateById(){
		try{
			assertEquals(SaveGameHandler.getDateById(1),new Date(1418162297256L));
		}catch(FileNotFoundException e){
			e.printStackTrace();
			fail("File not Found");
		}
	}
	/**
	 * Test if an error is correctly thrown if we try to call getDateById with an invalid savegame id
	 * @throws the expected FileNotFoundException 
	 */
	@Test (expected = FileNotFoundException.class)
	public void testGetDateByIdEx() throws FileNotFoundException{
		SaveGameHandler.getDateById(-30);
	}
	
	/**
	 * Tests the createSaveGame Method
	 */
	@Test
	public void testCreateNewSave(){
		try {
			assertEquals(createNewSave, 6);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Unexpected FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Unexpected IOException");
		}
	}
	
	
	@Test
	public void coverage(){
	    SaveGameHandler a = new SaveGameHandler() {
	    };
	}
}
