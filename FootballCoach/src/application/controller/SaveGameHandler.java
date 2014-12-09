package application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import application.model.*;

public class SaveGameHandler {

	private final static String DEFAULTLOC = "XML/Savegames/";
	/**
	 * Reads a savegame by the inputted index
	 * @param savegameNum the id of the savegame
	 * @return a competition according to the files in the savegame with the inputted id
	 * @throws Exception
	 */
	public static Competition loadCompetition(int savegameId) throws Exception{
		return XMLHandler.readCompetition(DEFAULTLOC + savegameId + "/competition.xml" , DEFAULTLOC + savegameId + "/Matches.xml");
		
	}
	/**
	 * Gives a ArrayList with all the ids of savegames at the savegame location
	 * @return An ArrayList With integer (id's of savegames)
	 */
	public static ArrayList<Integer> getSaveGames(){
		ArrayList<Integer> saveGameIds = new ArrayList<Integer>();
		File saveFolder = new File(DEFAULTLOC);
		File[] listOfFiles = saveFolder.listFiles();
		for(int i = 0; i<listOfFiles.length;i++){
			//Check if the file is a directory
			if(listOfFiles[i].isDirectory()){
				saveGameIds.add(Integer.parseInt(listOfFiles[i].getName()));
			}
		}
		return saveGameIds;
	}
	
	/**
	 * Returns the last saved date for an savegame by its id
	 * @return An timestamp for the last saved date
	 * @throws FileNotFoundException 
	 */
	public static Date getDateById(int id) throws FileNotFoundException{
		File saveGameFolder = new File(DEFAULTLOC + "/" + id);
		if(!saveGameFolder.exists())throw new FileNotFoundException();
		return new Date(saveGameFolder.lastModified());
	}
	
	public static int createNewSave() throws FileNotFoundException, IOException{
		// Getting a list of id's that are already in use
		ArrayList<Integer> ids = getSaveGames();
		Collections.sort(ids);
		//Getting the currently largest id
		int newId = ids.get(ids.size()-1)+1;
		//Make new directory
		new File(DEFAULTLOC + "/" + newId).mkdirs();
		return newId;
	}
	
}
