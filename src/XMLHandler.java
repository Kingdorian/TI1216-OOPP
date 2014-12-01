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
						players.add(parsePlayer(subNodes.item(j)));
					}
					break;
				}
			}
			return new Team(null, null, 0, null, 0, 0, 0, 0);
		}
		
		public static Player parsePlayer(Node playerNode){
			String name;
			NodeList nl = playerNode.getChildNodes();
			for(int j = 0; j<nl.getLength();j++){
				switch(nl.item(j).getNodeName()){
				//Add cases here to parse certain elements
					case "name":
						name = nl.item(j).getTextContent();
						System.out.println(name);
					case "someotherattributes":
						name = nl.item(j).getTextContent();
						System.out.println(name);
						break;
				}
			}
			return null;
			//return new Player(0, name, name, 0, 0, 0, 0, 0, 0, 0);
		}

}
