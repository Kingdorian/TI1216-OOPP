import java.io.File;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLHandler {
		static final String Encoding = "UTF-8";
		
		public static ArrayList<Team> readCompetition(String filename) throws Exception{
			ArrayList teams = new ArrayList<String>();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(filename));
			NodeList teamnodes = doc.getElementsByTagName("team");
			for(int i = 0; i<teamnodes.getLength();i++){
				teams.add(parseTeam(teamnodes.item(i)));
			}
			return teams;
			
		}
		public static Team parseTeam(Node teamNode){
			ArrayList<Players> players = new ArrayList<Players>();
			NodeList nodes = teamNode.getChildNodes();
			for(int i = 0; i<nodes.getLength();i++){
				NodeList subNodes = nodes.item(i).getChildNodes();
				switch(nodes.item(i).getNodeName()){
				case "players":
					for(int j =0; j<subNodes.getLength();j++){
						if(subNodes.item(j).getNodeName().equals("player")){
							players.add(parsePlayer(subNodes.item(j)));
						}
					}
					break;
				}
			}
			return null;
			//return new Team(null, null, 0, null, 0, 0, 0, 0);
		}
		
		public static Player parsePlayer(Node playerNode){
			String name = null, surname = null;
			int number = Integer.MIN_VALUE, 
				tna	= Integer.MIN_VALUE,
				attack = Integer.MIN_VALUE, 
				defence = Integer.MIN_VALUE, 
				stamina = Integer.MIN_VALUE;
			Status status = Status.DEFAULT;
			Reason reason = Reason.DEFAULT;
			NodeList nl = playerNode.getChildNodes();
			for(int j = 1; j<nl.getLength();j=j+2){
//				System.out.println(nl.item(j).getNodeName());
				switch(nl.item(j).getNodeName()){
				//Add cases here to parse certain elements

					case "name":
						name = nl.item(j).getTextContent();
						break;
					case "surname":
						surname = nl.item(j).getTextContent();
						break;
					case "number":
						number = Integer.parseInt(nl.item(j).getTextContent());
						break;
					case "timenotavailable":
						tna = Integer.parseInt(nl.item(j).getTextContent());
						break;
					case "reason":
						switch(nl.item(j).getTextContent()){
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
						attack = Integer.parseInt(nl.item(j).getTextContent());
						break; 
					case "defence":
						defence = Integer.parseInt(nl.item(j).getTextContent());
						break; 
					case "stamina":
						stamina = Integer.parseInt(nl.item(j).getTextContent());
						break; 
					case "status":
						switch(nl.item(j).getTextContent()){
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

}
