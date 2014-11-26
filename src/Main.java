
public class Main {
	public static void main(String[] args){
		try{
			XMLHandler.readXML("XML/competition.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
