import java.util.ArrayList;


public class Team {

	String name;
	ArrayList<Players> players = new ArrayList<Players>();
	int numberOfPlayers;
	String colour;
	int budget;
	int points;
	int goals;
	int goalsAgainst;
	boolean artificialGrass;

	/**
	 * Constructor
	 * 
	 * @param name Name of the team.
	 * @param players List of players who play for the team.
	 * @param numberOfPlayers Numbers of players.
	 * @param colour The colour of the team.
	 * @param budget Budget of the team.
	 * @param points The current points of the team.
	 * @param goals The numbers of goals the team has made in the league.
	 * @param goalsAgainst The numbers of goals scored against the team in the league.
	 * @param artificialGrass true if team plays on artificialGrass.
	 */
	public Team(String name, ArrayList<Players> players, int numberOfPlayers, String colour, int budget, int points, int goals, int goalsAgainst, boolean artificialGrass){
		
		this.name = name;
		this.players = players;
		this.numberOfPlayers = numberOfPlayers;
		this.colour = colour;
		this.budget = budget;
		this.points = points;
		this.goals = goals;
		this.goalsAgainst = goalsAgainst;
		this.artificialGrass = artificialGrass;
		
	}
	
	public boolean isArtificialGrass() {
		return artificialGrass;
	}

	public void setArtificialGrass(boolean artificialGrass) {
		this.artificialGrass = artificialGrass;
	}

	public boolean equals(Object other){
		
		if(other instanceof Team){
			
			Team that = (Team)other;
			
			if(this.name == that.name){
				
				return true;
				
			}
			
		}
		
		return false;
		
	}
	
	
	
	public ArrayList<Players> getPlayers() {
		return players;
	}


	public void setPlayers(ArrayList<Players> players) {
		this.players = players;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int getNumberOfPlayers() {
		return numberOfPlayers;
	}


	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}


	public String getColour() {
		return colour;
	}


	public void setColour(String colour) {
		this.colour = colour;
	}


	public int getBudget() {
		return budget;
	}


	public void setBudget(int budget) {
		this.budget = budget;
	}


	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}


	public int getGoals() {
		return goals;
	}


	public void setGoals(int goals) {
		this.goals = goals;
	}


	public int getGoalsAgainst() {
		return goalsAgainst;
	}


	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}



	
}
