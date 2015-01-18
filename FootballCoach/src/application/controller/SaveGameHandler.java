package application.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import javax.imageio.ImageIO;

import application.model.*;

public class SaveGameHandler {

    //The standardlocation of the savegames
    private static String defaultloc = "XML/Savegames/";
    //The standardlocation of the competitions 
    private static String defaultcomploc = "XML/Competitions/";

    /**
     * Changes the default location for savegames, useful for for example
     * testing purposes
     *
     * @param String newLoc with the new path to the savegame
     */
    public static void changeDefaultLoc(String newLoc) {
        defaultloc = newLoc;
    }
    
    /**
     * Changes the default location for competitions
     *
     * @param String newLoc with the new path to the competitions
     */
    public static void changeDefaultCompLoc(String newLoc) {
    	defaultcomploc = newLoc;
    }
    
    /**
     * Returns the default competition location
     * 
     * @return The location of the competitions as a String.
     */
    public static String getDefaultCompLoc() {
    	return defaultcomploc;
    }
    
    /**
     * Returns the default savegame location
     * 
     * @return The location of the savegames as a String.
     */
    public static String getDefaultLoc() {
    	return defaultloc;
    }


    /**
     * Reads a savegame by the inputted index
     *
     * @param savegameNum the id of the savegame
     * @return a competition according to the files in the savegame with the
     * inputted id
     * @throws Exception
     */
    public static Competition loadCompetition(int savegameId) throws Exception {
        Competition returnComp = ldByCompByPath(defaultloc + savegameId + "/Teams.xml", defaultloc + savegameId + "/Matches.xml");
        returnComp.setSaveGameId(savegameId);
        return returnComp;
    }

    /**
     * Loads a competition by the specified path
     *
     * @throws Exception if one of both files does not exist
     *
     */
    public static Competition ldByCompByPath(String compPath, String matchPath) throws Exception {
        return XMLHandler.readCompetition(compPath, matchPath);
    }

    /**
     * Returns an arraylist with all competitions that can be played in game.
     * 
     * @return An ArrayList with String (names of competitions)
     */
    public static ArrayList<String> getCompetitions() {
    	ArrayList<String> competitions = new ArrayList<String>();
    	File compFolder = new File(defaultcomploc);
    	File[] listOfFiles = compFolder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            //Check if the file is a directory
            if (listOfFiles[i].isDirectory()) {
                competitions.add(listOfFiles[i].getName());
            }
        }
        return competitions;
    }
    
    /**
     * Gives a ArrayList with all the ids of savegames at the savegame location
     *
     * @return An ArrayList With integer (id's of savegames)
     */
    public static ArrayList<Integer> getSaveGames() {
    	System.out.println("Current defaultloc" + defaultloc);
        ArrayList<Integer> saveGameIds = new ArrayList<Integer>();
        File saveFolder = new File(defaultloc);
        File[] listOfFiles = saveFolder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            //Check if the file is a directory
            if (listOfFiles[i].isDirectory()) {
                saveGameIds.add(Integer.parseInt(listOfFiles[i].getName()));
            }
        }
        return saveGameIds;
    }

    /**
     * Returns the last saved date for an savegame by its id
     *
     * @return An timestamp for the last saved date
     * @throws FileNotFoundException
     */
    public static Date getDateById(int id) throws FileNotFoundException {
        File saveGameFolder = new File(defaultloc + "/" + id);
        if (!saveGameFolder.exists()) {
            throw new FileNotFoundException();
        }
        System.out.println(saveGameFolder.lastModified());
        return new Date(saveGameFolder.lastModified());
    }

    /**
     * Creates a new savegame
     *
     * @return The new competition
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static Competition createNewSave(String teamsLoc, String matchesLoc) throws FileNotFoundException, IOException {
        // Getting a list of id's that are already in use
        ArrayList<Integer> ids = getSaveGames();
        Collections.sort(ids);
        //Getting the currently largest id
        int newId;
        newId = ids.get(ids.size() - 1) + 1;

        //Make new directory
        new File(defaultloc + "/" + newId).mkdirs();
        try {
            new File(defaultloc + "/" + newId + "/images/").mkdirs();
            Competition returnComp = ldByCompByPath(teamsLoc, matchesLoc);
            fetchImages(defaultloc + "/" + newId + "/images/", returnComp);
            returnComp.setSaveGameId(newId);
            returnComp.generateMatches(10100);
            return returnComp;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Save a competition to the hard drive
     *
     * @param Competition comp the competition to store
     */
    public static void saveGame(Competition comp) throws IOException {
        System.out.println(defaultloc);
        XMLHandler.writeCompetition(comp, defaultloc);
    }

    /**
     *
     * @param location
     * @param comp
     */

    private static void fetchImages(String location, Competition comp) {
        System.out.println("Fetching images...");
        File file = new File(location);
        file.mkdirs();
        for (int i = 0; i < comp.getTeams().length; i++) {
            try {
                BufferedImage img = ImageIO.read(new URL(comp.getTeams()[i].getImgUrl()));
                File outputfile = new File(location + comp.getTeams()[i].getName() + ".png");
                ImageIO.write(img, "png", outputfile);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Deletes the savegame with the specfied id from the filesystem
     *
     * @param id of the savegame to delete
     */
    public static void deleteSaveGame(int id) {
        File dir = new File(defaultloc);
        File[] savegames = dir.listFiles();
        for (int i = 0; i < savegames.length; i++) {
        	if(Integer.parseInt(savegames[i].getName())==id){
        		removeDirectory(savegames[i]);
        	}
        }
    }

    private static void removeDirectory(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (int i = 0; i < files.length; i++) {
                removeDirectory(files[i]);
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }
    /**
     * Returns the name the user assigned to the savegame
     * @param integer id of the savegame
     * @return The name
     */
	public static String getNameById(int id) {
		return XMLHandler.getSaveGameName(defaultloc + id + "/Matches.xml");
	}

	public static String getTeamNameById(int id) {
		return XMLHandler.getTeamName(defaultloc + id + "/Matches.xml");
	}
}
