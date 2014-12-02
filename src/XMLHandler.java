import java.io.File;
import java.util.ArrayList;

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
		static final String Encoding = "UTF-8";
		
		public static ArrayList<Team> readCompetition(String filename) throws Exception{
			ArrayList<Team> teams = new ArrayList<Team>();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(filename));
			NodeList teamnodes = doc.getElementsByTagName("team");
			for(int i = 0; i<teamnodes.getLength();i++){
				teams.add(parseTeam((Element)teamnodes.item(i)));
			}
			return teams;
			
		}
		
		private static Team parseTeam(Element teamElement){
			Team team = new Team(teamElement.getAttribute("name"), Boolean.parseBoolean(teamElement.getAttribute("art_grass")));
			NodeList nodes = teamElement.getChildNodes();
			for(int i = 0; i<nodes.getLength();i++){
				Players p; 
				if(nodes.item(i).getNodeName().equals("player")){
					p = parsePlayer(nodes.item(i));
					if(p!=null)team.addPlayer(p);
					//System.out.println(p);
				}else if(nodes.item(i).getNodeName().equals("goalkeeper")){
					p = parseGoalkeeper(nodes.item(i));
					if(p!=null)team.addPlayer(p);
					//System.out.println(p);
				}

				//System.out.println(team.toString());
			}
			return team;
		}
		
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
						switch(nl.item(i).getTextContent()){
							case "HARMSTRING":
								reason = Reason.HARMSTRING;
								break;
							case "KNEE":
								reason = Reason.KNEE;
								break;
							case "HEAD":
								reason = Reason.HEAD;
								break;
							case "ANKEL":
								reason = Reason.ANKEL;
								break;
						}
						break;
					case "stoppower":
						stoppower = Integer.parseInt(nl.item(i).getTextContent());
						break; 
					case "penaltystoppower":
						penaltystoppower = Integer.parseInt(nl.item(i).getTextContent());
						break;  
					case "status":
						switch(nl.item(i).getTextContent()){
						case "INJUREDSUSPENDED":
							status = Status.INJUREDSUSPENDED;
							break;
						case "SUSPENDED":
							status = Status.SUSPENDED;
							break;
						case "INJURED":
							status = Status.INJURED;
							break;
					}
				}
			}
			if(name!=null&&surname!=null&&number!=Integer.MIN_VALUE&&tna!=Integer.MIN_VALUE
				&stoppower!=Integer.MIN_VALUE&&penaltystoppower!=Integer.MIN_VALUE){
				return new Goalkeeper(name, surname, number, status, tna, reason, stoppower, penaltystoppower);
			}else{
				
				return null;
			}
		}
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
						switch(nl.item(i).getTextContent()){
							case "HARMSTRING":
								reason = Reason.HARMSTRING;
								break;
							case "KNEE":
								reason = Reason.KNEE;
								break;
							case "HEAD":
								reason = Reason.HEAD;
								break;
							case "ANKEL":
								reason = Reason.ANKEL;
								break;
						}
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
						switch(nl.item(i).getTextContent()){
						case "INJUREDSUSPENDED":
							status = Status.INJUREDSUSPENDED;
							break;
						case "SUSPENDED":
							status = Status.SUSPENDED;
							break;
						case "INJURED":
							status = Status.INJURED;
							break;
					}
				}
			}
			if(name!=null&&surname!=null&&number!=Integer.MIN_VALUE&&tna!=Integer.MIN_VALUE
				&attack!=Integer.MIN_VALUE&&defence!=Integer.MIN_VALUE&&stamina!=Integer.MIN_VALUE){
				return new Player(name, surname, number, status, tna, reason, attack, defence, stamina);
			}else{
				return null;
			}
		}
		
		public static void writeCompetition(int saveGameId, ArrayList<Team> teams) throws  Exception {
			File saveDir = new File("XML/Savegames/" + saveGameId + "/");
			//If the savedir does not yet exist create it and copy the default competition xml into it
			if(!saveDir.exists()){
				System.out.println("Making dir");
				saveDir.mkdir();
			}
			// Setting up doc builder
			DocumentBuilderFactory dF = DocumentBuilderFactory.newInstance();
			DocumentBuilder dB = dF.newDocumentBuilder();
			Document doc = dB.newDocument();
			//Appending the teams element
			Element teamsElement = doc.createElement("teams");
			doc.appendChild(teamsElement);
			// Looping over all the teams in the ArrayList
			for(int i = 0; i<teams.size();i++){
				Element teamElement = doc.createElement("team");
				teamsElement.appendChild(teamElement);	
				teamElement.setAttribute("name", teams.get(i).getName());
	
				// TODO WRITE LOGO TO XML FILE
				teamElement.setAttribute("logo", "");
				teamElement.setAttribute("art_grass", teams.get(i).hasArtificialGrass()+"");
		
				for(int j = 0; j<teams.get(i).getPlayers().size();j++){
					Players pl = teams.get(i).getPlayers().get(j);
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
						buffer.get(3).setTextContent(p.getStatus().status+"");
						buffer.add(doc.createElement("timenotavailable"));
						buffer.get(4).setTextContent(p.getTimeNotAvailable()+"");
						buffer.add(doc.createElement("reason"));
						buffer.get(5).setTextContent(p.getReason().reason+"");
						buffer.add(doc.createElement("attack"));
						buffer.get(6).setTextContent(p.getAttack()+"");
						buffer.add(doc.createElement("defence"));
						buffer.get(7).setTextContent(p.getDefence()+"");
						buffer.add(doc.createElement("stamina"));
						
						buffer.get(8).setTextContent(p.getStamina()+"");
						for(int k = 0; k<buffer.size();k++){
							pE.appendChild(buffer.get(k));
						}
					}else if(pl instanceof Goalkeeper){
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
						buffer.get(3).setTextContent(k.getStatus().status+"");
						buffer.add(doc.createElement("timenotavailable"));
						buffer.get(4).setTextContent(k.getTimeNotAvailable()+"");
						buffer.add(doc.createElement("reason"));

						buffer.get(5).setTextContent(k.getReason().reason+"");
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
//			StreamResult result = new StreamResult(System.out);
			StreamResult result = new StreamResult(new File("XML/Savegames/" + saveGameId + "/competition.xml"));
			t.transform(source, result);
			

			
		}

}
