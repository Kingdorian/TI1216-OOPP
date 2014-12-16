package application.controller;

import java.io.File;
import java.util.ArrayList;

import application.model.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLHandler {

		private static final String ENCODING = "UTF-8";
		/**
		 * Reads a competition xml file and returns an array of all the teams in the file
		 * @param String the location for the file
		 * @return ArrayList with all the teams in the xml file
		 * @throws Exception
		 */
		public static Competition readCompetition(String teamsLoc, String compLoc) throws Exception{
			Team teams[] = new Team[18];
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(teamsLoc));
			NodeList teamnodes = doc.getElementsByTagName("team");
			for(int i = 0; i<teamnodes.getLength();i++){
				teams[i] = parseTeam((Element)teamnodes.item(i));
			}
			Competition comp = new Competition(teams);
			doc = db.parse(new File(compLoc));
			NodeList rounds = doc.getElementsByTagName("round");
			for(int i = 0; i<rounds.getLength();i++){
				Element round = (Element)rounds.item(i);
				NodeList matches = round.getElementsByTagName("match");
				for(int j = 0; j<matches.getLength();j++){
					comp.addMatch(i, j, parseMatch((Element)matches.item(j), comp));
				}
			}
			int saveGameId = 0;
			try{
				saveGameId = Integer.parseInt(((Element)doc.getElementsByTagName("rounds").item(0)).getAttribute("saveGameId"));
			}catch(NumberFormatException e){
				e.printStackTrace();
				
			}
			comp.setSaveGameId(saveGameId);
			System.out.println(comp.getSaveGameId());
			return comp;
			
		}
		private static Match parseMatch(Element match, Competition comp) {
			Element homeTeam = (Element)match.getElementsByTagName("hometeam").item(0);
			Team 	hT = comp.getTeamByName(homeTeam.getAttribute("name"));
			int	hTp = Integer.parseInt(homeTeam.getAttribute("points"));
			Element visitorTeam = (Element)match.getElementsByTagName("visitorteam").item(0);
			Team vT = comp.getTeamByName(visitorTeam.getAttribute("name"));
			int vTp = Integer.parseInt(visitorTeam.getAttribute("points"));
			return new Match(hT, vT, hTp, vTp);
		}
		/**
		 * @Param teamElement, the XML Element of that correesponds with a certain team
		 * @return the Team composed out of the values in the Element supplied
		 */
		public static Team parseTeam(Element teamElement){
			Team team = new Team(teamElement.getAttribute("name"), Boolean.parseBoolean(teamElement.getAttribute("art_grass")), teamElement.getAttribute("logo"));
			NodeList nodes = teamElement.getChildNodes();
			for(int i = 0; i<nodes.getLength();i++){
				Players p; 
				if(nodes.item(i).getNodeName().equals("player")){
					p = parsePlayer(nodes.item(i));
					if(p!=null)team.addPlayer(p);
				}else if(nodes.item(i).getNodeName().equals("goalkeeper")){
					p = parseGoalkeeper(nodes.item(i));
					if(p!=null)team.addPlayer(p);
				}
			}
			return team;
		}
		/**
		 * 
		 * @param playerNode to be parsed
		 * @return Goalkeeper Object according to inputted node
		 */
		private static Goalkeeper parseGoalkeeper(Node playerNode){

			String name = null, surname = null;
			int number = Integer.MIN_VALUE, 
				tna	= Integer.MIN_VALUE,
				stoppower = Integer.MIN_VALUE, 
				penaltystoppower = Integer.MIN_VALUE;
			Status status = Status.DEFAULT;
			Reason reason = Reason.DEFAULT;
			NodeList nl = playerNode.getChildNodes();
			for(int i = 0; i<nl.getLength();i++){

				switch(nl.item(i).getNodeName()){
				//Add cases here to parse certain elements
					case "name":
						name = nl.item(i).getTextContent();
						break;
					case "surname":			
						surname = nl.item(i).getTextContent();
						break;
					case "number":
						
						number = Integer.parseInt(nl.item(i).getTextContent());
						break;
					case "timenotavailable":
						tna = Integer.parseInt(nl.item(i).getTextContent());
						break;
					case "reason":
						reason = Reason.valueOf(nl.item(i).getTextContent());
						break;
					case "stoppower":
						stoppower = Integer.parseInt(nl.item(i).getTextContent());
						break; 
					case "penaltystoppower":
						penaltystoppower = Integer.parseInt(nl.item(i).getTextContent());
						break;  
					case "status":
						status = Status.valueOf(nl.item(i).getTextContent());
						break;
				}
			}
			if(name!=null&&surname!=null&&number!=Integer.MIN_VALUE&&tna!=Integer.MIN_VALUE
				&&stoppower!=Integer.MIN_VALUE&&penaltystoppower!=Integer.MIN_VALUE){
				return new Goalkeeper(name, surname, number, status, tna, reason, stoppower, penaltystoppower);
			}else{
				
				return null;
			}
		}
		/**
		 * Parses a playernode
		 * @param playerNode to be parsed
		 * @return Player object according to the values in the playerNode
		 */
		private static Player parsePlayer(Node playerNode){

			String name = null, surname = null;
			int number = Integer.MIN_VALUE, 
				tna	= Integer.MIN_VALUE,
				attack = Integer.MIN_VALUE, 
				defence = Integer.MIN_VALUE, 
				stamina = Integer.MIN_VALUE;
			Status status = Status.DEFAULT;
			Reason reason = Reason.DEFAULT;
			NodeList nl = playerNode.getChildNodes();
			for(int i = 0; i<nl.getLength();i++){
				switch(nl.item(i).getNodeName()){
				//Add cases here to parse certain elements
					case "name":
						name = nl.item(i).getTextContent();
						break;
					case "surname":
						
						surname = nl.item(i).getTextContent();
						break;
					case "number":
						
						number = Integer.parseInt(nl.item(i).getTextContent());
						break;
					case "timenotavailable":
						tna = Integer.parseInt(nl.item(i).getTextContent());
						break;
					case "reason":
						reason = Reason.valueOf(nl.item(i).getTextContent());
						break;
					case "attack":
						attack = Integer.parseInt(nl.item(i).getTextContent());
						break; 
					case "defence":
						defence = Integer.parseInt(nl.item(i).getTextContent());
						break; 
					case "stamina":
						stamina = Integer.parseInt(nl.item(i).getTextContent());
						break; 
					case "status":
						status = Status.valueOf(nl.item(i).getTextContent());
						break;
					
				}
			}
			if(name!=null&&surname!=null&&number!=Integer.MIN_VALUE&&tna!=Integer.MIN_VALUE
				&&attack!=Integer.MIN_VALUE&&defence!=Integer.MIN_VALUE&&stamina!=Integer.MIN_VALUE){
				return new Player(name, surname, number, status, tna, reason, attack, defence, stamina);
			}else{
				return null;
			}
		}
		/**
		 * Stores a competition in the competition.xml file in the specified savegame
		 * @param teams The arraylist with teams to store
		 * @throws Exception
		 */
		public static void writeCompetition(Competition comp, String location){
			File saveDir = new File(location);
			//If the savedir does not yet exist create it and copy the default competition xml into it
			if(!saveDir.exists()){
				System.out.println("Making dir");
				saveDir.mkdir();
			}
			try{
				writeTeams(location + comp.getSaveGameId() + "/Competition.xml", comp.getTeams());
				writeMatches(location + comp.getSaveGameId() + "/Matches.xml", comp);
			}catch(Exception e){
				e.printStackTrace();
			}
			// Writing the matches
			
		}
		
		private static void writeMatches(String location, Competition comp) throws Exception {
			System.out.println(location + "   loc");
			// Setting up doc builder
			DocumentBuilderFactory dF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dF.newDocumentBuilder();
			Document doc = dB.newDocument();
			//Appending the teams element
			Element roundsElement = doc.createElement("rounds");
			doc.appendChild(roundsElement);
			// Looping over all the rounds
			for(int i = 0; i<34;i++){
				Element roundElement = doc.createElement("round");
				roundsElement.appendChild(roundElement);
				//Looping over the matches
				for(int j = 0; j<9; j++){
					Match match = comp.getMatch(i, j);
					if(!(match==null)){
						Element matchElement = doc.createElement("match");
						roundElement.appendChild(matchElement);
						Element homeTeam = doc.createElement("hometeam");
						matchElement.appendChild(homeTeam);
						homeTeam.setAttribute("name", match.getHomeTeam().getName());
						homeTeam.setAttribute("points", match.getHomeTeam().getPoints()+"");
						Element visTeam = doc.createElement("visitorteam");
						matchElement.appendChild(visTeam);
						visTeam.setAttribute("name", match.getVisitorTeam().getName());
						visTeam.setAttribute("points", match.getVisitorTeam().getPoints()+"");
					}
				}
			}
			roundsElement.setAttribute("saveGameId", comp.getSaveGameId()+"");
			//Storing xml into a file
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer t = tF.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(location));
			t.transform(source, result);
		}
		/**
		 * Writes the Teams to the file specified in the location string.
		 * @param A string to the path where to store the files
		 * @param teams An array with the teams to write to the file
		 * @throws Exception
		 */
		private static void writeTeams(String location, Team[] teams) throws Exception{
			// Setting up doc builder
			DocumentBuilderFactory dF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dF.newDocumentBuilder();
			Document doc = dB.newDocument();
			//Appending the teams element
			Element teamsElement = doc.createElement("teams");
			doc.appendChild(teamsElement);
			// Looping over all the teams in the ArrayList
			for(int i = 0; i<teams.length;i++){
				Element teamElement = doc.createElement("team");
				teamsElement.appendChild(teamElement);	
				teamElement.setAttribute("logo", "");
				teamElement.setAttribute("art_grass", teams[i].hasArtificialGrass()+"");
		
				for(int j = 0; j<teams[i].getPlayers().size();j++){
					Players pl = teams[i].getPlayers().get(j);
					Element pE = null;
					if(pl instanceof Player){
						Player p = (Player)pl;
						pE = doc.createElement("player");
						ArrayList<Element> buffer = new ArrayList<Element>();
						buffer.add(doc.createElement("name"));
						buffer.get(0).setTextContent(p.getName());
						buffer.add(doc.createElement("surname"));
						buffer.get(1).setTextContent(p.getSurName());
						buffer.add(doc.createElement("number"));
						buffer.get(2).setTextContent(p.getNumber()+"");
						buffer.add(doc.createElement("status"));
						buffer.get(3).setTextContent(p.getStatus()+"");
						buffer.add(doc.createElement("timenotavailable"));
						buffer.get(4).setTextContent(p.getTimeNotAvailable()+"");
						buffer.add(doc.createElement("reason"));
						buffer.get(5).setTextContent(p.getReason()+"");
						buffer.add(doc.createElement("attack"));
						buffer.get(6).setTextContent(p.getAttack()+"");
						buffer.add(doc.createElement("defence"));
						buffer.get(7).setTextContent(p.getDefence()+"");
						buffer.add(doc.createElement("stamina"));
						
						buffer.get(8).setTextContent(p.getStamina()+"");
						for(int k = 0; k<buffer.size();k++){
							pE.appendChild(buffer.get(k));
						}
					}else{
						Goalkeeper k = (Goalkeeper)pl;
						pE = doc.createElement("goalkeeper");
						ArrayList<Element> buffer = new ArrayList<Element>();
						buffer.add(doc.createElement("name"));
						buffer.get(0).setTextContent(k.getName());
						buffer.add(doc.createElement("surname"));
						buffer.get(1).setTextContent(k.getSurName());
						buffer.add(doc.createElement("number"));
						buffer.get(2).setTextContent(k.getNumber()+"");
						buffer.add(doc.createElement("status"));
						buffer.get(3).setTextContent(k.getStatus()+"");
						buffer.add(doc.createElement("timenotavailable"));
						buffer.get(4).setTextContent(k.getTimeNotAvailable()+"");
						buffer.add(doc.createElement("reason"));

						buffer.get(5).setTextContent(k.getReason()+"");
						buffer.add(doc.createElement("stoppower"));
						buffer.get(6).setTextContent(k.getStopPower()+"");
						buffer.add(doc.createElement("penaltystoppower"));
						buffer.get(7).setTextContent(k.getPenaltyStopPower()+"");
						for(int l = 0; l<buffer.size();l++){
							pE.appendChild(buffer.get(l));
						}
					}
					teamElement.appendChild(pE);
				}

			}
			
			//Storing xml into a file
			TransformerFactory tF = TransformerFactory.newInstance();
			Transformer t = tF.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(location));
			t.transform(source,	result);
		}
}
