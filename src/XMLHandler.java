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
		
		public static void readNEWGAME(String filename) throws Exception{
			ArrayList teamNames = new ArrayList<String>();
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse(new File(filename));
			Element rootEl = doc.getDocumentElement();
			NodeList nodes = rootEl.getChildNodes();
			for(int i =0; i<nodes.getLength();i++){
				Node node = nodes.item(i);
				if(node instanceof Element){
					Element el = (Element)node;
					String name = el.getChildNodes().item(1).getTextContent();
					String grassType = el.getChildNodes().item(3).getTextContent();
					String urlToImg = el.getChildNodes().item(5).getTextContent();
				}
			}
		}
		public static void storeGAME()
}
