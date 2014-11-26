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
			Element rootEl = doc.getDocumentElement();
			NodeList teamnodes = rootEl.getChildNodes();
			for(int i = 0; i<teamnodes.getLength();i++){
				teams.add(parseTeam((Element)teamnodes.item(i)));
			}
			return teams;
			
		}
		public static Team parseTeam(Element teamElement){
			ArrayList<Players> players = new ArrayList<Players>();
			NodeList nodes = teamElement.getChildNodes();
			for(int i =0; i<nodes.getLength();i++){
				Node node = nodes.item(i);
				Element el = (Element)node;
				players.add(parsePlayer(el));
			}
			return new Team(null, null, 0, null, 0, 0, 0, 0);
		}
		
		public static Player parsePlayer(Element playerElement){
			String name;
			NodeList nl = playerElement.getChildNodes();
			for(int j = 0; j<nl.getLength();j++){
				switch(nl.item(j).getNodeName()){
				//Add cases here to parse certain elements
					case "Name":
						name = nl.item(j).getTextContent();
						break;
				}
			}
			return null;
			//return new Player(0, name, name, 0, 0, 0, 0, 0, 0, 0);
		}

}
