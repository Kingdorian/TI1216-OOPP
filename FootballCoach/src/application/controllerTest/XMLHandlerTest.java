package application.controllerTest;
import static org.junit.Assert.*;

import application.model.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;


public class XMLHandlerTest {
	ArrayList<Team> refList;
	Competition refComp;
	@Before public void initialize(){
		Team[] team = new Team[1];
		team[1] = new Team("ADO Den Haag", true);
		team[1].addPlayer(new Goalkeeper("Robert", "Zwinkels", 1, Status.DEFAULT, 0, Reason.DEFAULT, 62, 65));
		team[1].addPlayer(new Player("Dion", "Malone", 2, Status.DEFAULT, 0, Reason.DEFAULT, 26, 60, 75));
		refComp = new Competition(team);
	}
	
	
	@Test
	public void testReadCompetition() {
		try {
			Competition comp = XMLHandler.readCompetition("XML/XMLHandlerTestFile.xml", "XML/XMLHanTestComp.xml");
			assertEquals(comp, refComp);

		} catch (Exception e) {
			fail("Unexpected exception");
		}
	}
	@Test
	public void testReadCompetitionInv(){
		try {
			ArrayList<Team> genList = XMLHandler.readCompetition("XML/XMLHandlerTestFileInv.xml");
			assertEquals(genList, refList);

		} catch (Exception e) {
			fail("Unexpected exception");
		}
	}
	@Test
	public void testWriteCompetitionFileNotExist(){
		try {
			Path path = Paths.get("XML/Savegames/345/competition.xml");
			Files.delete(path);
			path = Paths.get("XML/Savegames/345");
			Files.delete(path);
		}catch (Exception e){
			
		}
		try{
			XMLHandler.writeCompetition(345, refList);
			ArrayList<Team> genList = XMLHandler.readCompetition("XML/XMLHandlerTestFile.xml");
			assertEquals(genList,refList);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected Exception");
		}
		
	}
	@Test
	public void testWriteCompetitionFileExist(){
		try {
			XMLHandler.writeCompetition(360, refList);
			ArrayList<Team> genList = XMLHandler.readCompetition("XML/XMLHandlerTestFile.xml");
			assertEquals(genList,refList);
		} catch (Exception e) {
			fail("Unexpected Exception");
		}
		
	}
	@Test
	public void testParseMatch(){
	}
}
