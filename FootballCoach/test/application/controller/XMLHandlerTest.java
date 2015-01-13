package application.controller;

import static org.junit.Assert.*;
import application.model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class XMLHandlerTest {

    Competition refComp;

    @Before
    public void initialize() {
        Team[] team = new Team[18];
        team[0] = new Team("ADO Den Haag", true);
        team[0].setBudget(50000);
        team[0].setImgUrl("http://eredivisie-images.s3.amazonaws.com/Eredivisie%20images/Eredivisie%20Badges/701/150x150.png");
        team[0].setArtificialGrass(true);
        team[0].addPlayer(new Goalkeeper("Robert", "Zwinkels", 1, Status.DEFAULT, 0, Reason.DEFAULT, 62, 65));
        team[0].addPlayer(new Player("Dion", "Malone", 2, Status.DEFAULT, 0, Reason.DEFAULT, 26, 60, 75));
        team[0].addPlayer(new Player("Dion", "Malone", 2, Status.INJUREDSUSPENDED, 0, Reason.DEFAULT, 26, 60, 75));
        refComp = new Competition(team);
        refComp.addMatch(0, 0, new Match(team[0], team[0], 5, 3));
    }

    @Test
    public void testReadCompetition() {
        try {
            Competition comp = XMLHandler.readCompetition("XML/XMLHandlerTestFile.xml", "XML/XMLHanTestComp.xml");
            System.out.println(comp.getTeams()[0].hasArtificialGrass());
            System.out.println(refComp.getTeams()[0].hasArtificialGrass());
            assertEquals(comp, refComp);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception");
        }
    }


    @Test
    public void testReadCompetitionInv() {
        try {
            Competition comp = XMLHandler.readCompetition("XML/XMLHandlerTestFileInv.xml", "XML/XMLHanTestComp.xml");
            fail("No exception");

        } catch (Exception e) {
            
        }
    }

    @Test
    public void testWriteCompetitionFileNotExist() {
        try {
            Path path = Paths.get("XML/Savegames/345/competition.xml");
            Files.delete(path);
            path = Paths.get("XML/Savegames/345");
            Files.delete(path);
        } catch (Exception e) {

        }
        try {
            XMLHandler.writeCompetition(refComp, "XML/TestSavegames/");
            Competition genComp = XMLHandler.readCompetition("XML/XMLHandlerTestFile.xml", "XML/XMLHanTestComp.xml");
            assertEquals(genComp, refComp);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected Exception");
        }

    }

	@Test
	public void testWriteCompetitionFileExist(){
		try {
			Competition genComp = XMLHandler.readCompetition("XML/XMLHandlerTestFile.xml","XML/XMLHanTestComp.xml");
			assertEquals(genComp.getMatch(0, 0),refComp.getMatch(0, 0));
		} catch (Exception e) {
			fail("Unexpected Exception");
		}
		
	}

    @Test
    public void testWriteCompetition() {

    }
}
