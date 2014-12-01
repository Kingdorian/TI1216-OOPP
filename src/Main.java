
public class Main {
	public static void main(String[] args){
		try{
			XMLHandler.readCompetition("XML/competition.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
