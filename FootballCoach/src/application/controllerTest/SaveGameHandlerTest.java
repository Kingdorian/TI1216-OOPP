package application.controllerTest;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import application.controller.*;
import application.model.*;

public class SaveGameHandlerTest {
	@Before public void initialize(){
		SaveGameHandler.changeDefaultLoc("XML/TestSaveGames/");
	}
	
	@Test
	public void testloadCompetition(){
		try {
			Competition genComp = SaveGameHandler.loadCompetition(1);
			Competition refComp = XMLHandler.readCompetition("XML/TestSavegames/1/competition.xml", "XML/TestSavegames/1/Matches.xml");
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
			reflist.add(-2);
			reflist.add(1);
			assertEquals(reflist, list);
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
			assertEquals(SaveGameHandler.getDateById(-2),new Date(1418203924370L));
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
			Competition refComp = XMLHandler.readCompetition("XML/Teams.xml", "XML/Matches.xml");
			assertEquals(refComp,  SaveGameHandler.createNewSave("XML/Teams.xml", "XML/Matches.xml"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			fail("Unexpected FileNotFoundException");
		} catch (IOException e) {
			e.printStackTrace();
			fail("Unexpected IOException");
		} catch (Exception e) {
			fail("Unexpected Exception");
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void coverage(){
	    SaveGameHandler a = new SaveGameHandler() {
	    };
	}
	@After
	public void cleanUp(){
		ArrayList<Integer> saveGameIds = new ArrayList<Integer>();
		File saveFolder = new File("XML/TestSavegames/");
		File[] listOfFiles = saveFolder.listFiles();
		for(int i = 0; i<listOfFiles.length;i++){
			//Delete all the directories that are not -2 or 1
			String name = listOfFiles[i].getName();
			if(!(name.equals("1")||name.equals("-2"))){
				System.out.println("Deleting...");
				removeDirectory(listOfFiles[i]);
			}
		}
	}
	
	public static void removeDirectory(File dir) {
	    if (dir.isDirectory()) {
	        File[] files = dir.listFiles();
	        if (files != null && files.length > 0) {
	            for (int i = 0; i < files.length; i++) {
	                removeDirectory(files[i]);
	            }
	        }
	        dir.delete();
	    } else {
	        dir.delete();
	    }
	}
}
