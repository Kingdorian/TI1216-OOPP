import java.util.ArrayList;


public class Main {
	public static void main(String[] args){
		try{
			ArrayList<Team> teams = XMLHandler.readCompetition("XML/Savegames/1/competition.xml");
			XMLHandler.writeCompetition(2, teams);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
