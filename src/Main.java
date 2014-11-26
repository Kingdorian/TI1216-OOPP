
public class Main {
	public static void main(String[] args){
		try{
			XMLHandler.readNEWGAME("XML/competition.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
