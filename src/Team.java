
public class Team {

	String name;
	int numberOfPlayers;
	String colour;
	int budget;
	int points;
	int goals;
	int goalsAgainst;
	
	
	public Team(String name, int numberOfPlayers, String colour, int budget, int points, int goals, int goalsAgainst){
		
		this.name = name;
		this.numberOfPlayers = numberOfPlayers;
		this.colour = colour;
		this.budget = budget;
		this.points = points;
		this.goals = goals;
		this.goalsAgainst = goalsAgainst;
		
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
