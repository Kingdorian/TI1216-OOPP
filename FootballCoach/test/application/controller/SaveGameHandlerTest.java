package application.controller;

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
		SaveGameHandler.changeDefaultCompLoc("XML/TestCompetitions/");
	}
	
	
	@Test
	public void testloadCompetition(){
		try {
			Competition genComp = SaveGameHandler.loadCompetition(1);
			Competition refComp = XMLHandler.readCompetition("XML/TestSavegames/1/Teams.xml", "XML/TestSavegames/1/Matches.xml");
			assertEquals(genComp, refComp);
		} catch (Exception e) {
			System.out.println("Error with testLoadCompetition");
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
	
	@Test
	public void testGetCompetitions(){
		try{
			ArrayList<String> list = SaveGameHandler.getCompetitions();
			ArrayList<String> reflist = new ArrayList<String>();
			reflist.add("Testdivisie");
			assertEquals(reflist, list);
		}catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception");
		}

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
			String time = SaveGameHandler.getDateById(-2).getTime()+"";
			assertEquals(SaveGameHandler.getDateById(-2),new Date( 1418762230437L));
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
			Competition genComp =  SaveGameHandler.createNewSave("XML/Competitions/Eredivisie/Teams.xml", "XML/Competitions/Eredivisie/Matches.xml");
			Competition refComp = XMLHandler.readCompetition("XML/Competitions/Eredivisie/Teams.xml", "XML/Competitions/Eredivisie/Matches.xml");
			System.out.println("Refcomp" + refComp);
			System.out.println("Gencomp" + genComp);
			assertEquals(refComp.getTeams(),  genComp.getTeams());
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
	
	

	@Test
	public void testDeleteSaveGame(){
		String locStorage = SaveGameHandler.getDefaultLoc();
		SaveGameHandler.changeDefaultLoc("XML/TestSaveGames/");
		try {
			Competition comp = SaveGameHandler.createNewSave("XML/Competitions/Eredivisie/Teams.xml", "XML/Competitions/Eredivisie/Matches.xml");
			SaveGameHandler.deleteSaveGame(comp.getSaveGameId());
			File Teams = new File("XML/TestSaveGames/5/Teams.xml");
			File Matches = new File("XML/TestSaveGames/5/Matches.xml");
			assertFalse(Teams.exists()||Matches.exists());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SaveGameHandler.changeDefaultLoc(locStorage);
	}
	
	@Test
	public void testGetDefaultLoc(){
		 assertEquals(SaveGameHandler.getDefaultLoc(), "XML/TestSaveGames/");
	}
	
	@Test
	public void testGetDefaultCompLoc(){
		 assertEquals(SaveGameHandler.getDefaultCompLoc(), "XML/TestCompetitions/");
	}
	@Test
	public void testGetNameById(){
		assertEquals(SaveGameHandler.getNameById(1), "Mario");
	}
	@Test
	public void testGetTeamNameById(){
		assertEquals(SaveGameHandler.getTeamNameById(1), "Ajax");
	}
	
	@Test
	public void testSomething(){
		
	}
	@After
	public void cleanUp(){
		ArrayList<Integer> saveGameIds = new ArrayList<Integer>();
		File saveFolder = new File("XML/TestSavegames/");
		File[] listOfFiles = saveFolder.listFiles();
		for(int i = 0; i<listOfFiles.length;i++){
			//Delete all the directories that are not -2 or 1
			String name = listOfFiles[i].getName();
			System.out.println(name);
			if(!(name.equals("1")||name.equals("-2")||name.equals("-229"))){
				System.out.println("Deleting" + name);
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
