package application.model;


import application.Main;
import java.util.ArrayList;

public class Team {

    private String name;
    private String imgUrl;
    private ArrayList<Players> players = new ArrayList<>();
    private int budget, points, goals, goalsAgainst, goalDiff, wins, draws, losses;
    private boolean artificialGrass;

    /**
     * Constructor
     *
     * @param name Name of the team.
     * @param artificialGrass true if team plays on artificialGrass.
     */
    public Team(String name, boolean artificalGrass) {
        this.name = name;
        this.budget = Integer.MIN_VALUE;
        this.points = 0;
        this.goals = 0;
        this.goalsAgainst = 0;
        this.artificialGrass = artificalGrass;
    }

    public Team(String name, boolean artificalGrass, String imgUrl) {
        this(name, artificalGrass);
        this.setImgUrl(imgUrl);
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return this.imgUrl;
    }

    public boolean hasArtificialGrass() {
        return artificialGrass;
    }

    public void setArtificialGrass(boolean artificialGrass) {
        this.artificialGrass = artificialGrass;
    }


    

    public ArrayList<Players> getPlayers() {
        return players;
    }
    
    public ArrayList<Players> getAvailablePlayers() {
        ArrayList<Players> res = new ArrayList<>();
        int keepers = 0;
        for (Players pl : players) {
            if(pl.getTimeNotAvailable()==0){
                if(pl instanceof Goalkeeper)
                    keepers++;
                res.add(pl);
            }
        }
        //return all players if there aren't enough players to put in the field
        if(res.size() - keepers < 11 || keepers==0){
            return getPlayers();
        }else{
            return res;
        }
    }

    public void setPlayers(ArrayList<Players> players) {
        this.players = players;
    }

	public boolean equals(Object obj) {
		if(obj==null)
			return false;
		if(!(obj instanceof Team))
			return false;
		Team other = (Team) obj;
		if (artificialGrass != other.hasArtificialGrass())
			return false;
		if (budget != other.getBudget())
			return false;
		if (draws != other.getDraws())
			return false;
		if (goalDiff != other.getGoalDiff())
			return false;
		if (goals != other.getGoals())
			return false;
		if (goalsAgainst != other.getGoalsAgainst())
			return false;
		if (other.getImgUrl() == null) {
			if (imgUrl != null)
				return false;
		} else if (!imgUrl.equals(other.getImgUrl()))
			return false;

		if (losses != other.getLosses())
			return false;
		if (name == null) {
			if (other.getName() != null)
				return false;
		} else if (!name.equals(other.getName()))
			return false;
		if (!players.equals(other.getPlayers()))
			return false;
		if (points != other.getPoints())
			return false;
		if (wins != other.getWins())
			return false;
		return true;
	}

	public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    /**
     * Returns a string representation of the Team object
     */
    public String toString() {
        return "Team [name=" + name + ", players=" + players.toString() + ", budget="
                + budget + ", points=" + points + ", goals=" + goals
                + ", goalsAgainst=" + goalsAgainst + ", artificialGrass="
                + artificialGrass + "]";
    }

    /**
     * Adds a player to the team
     *
     * @param Player p to add to the team
     */
    public void addPlayer(Players p) {
        players.add(p);
    }

    /**
     * Makes a transfer from the team to the other team t
     *
     * @param p Player which is changing team
     * @param t Team which te player goes to
     * @param money The transfersum
     */
    /**
     * Makes a transfer from the team to the other team t
     *
     * @param player Player which is changing team
     * @param team Team which te player goes to
     * @param money The transfersum
     * @return true if the transfer was done succesfully
     */
    public boolean transferTo(Players player, Team team, int money) {
        if (this.getPlayers().contains(player) && team.budget > money) {
            this.getPlayers().remove(player);
            this.formatPlayerID();
            team.addPlayer(player);
            player.setNumber(team.getPlayers().size());
            this.setBudget(this.getBudget() + money);
            team.setBudget(team.getBudget() - money);


            return true;
        }
        return false;
    }

    private void formatPlayerID() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setId(i);
        }
    }

    public int getGoalDiff() {
        return goalDiff;
    }

    public void setGoalDiff(int goalDiff) {
        this.goalDiff = goalDiff;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
    
    public int getAmountForwards() {
    	int amount = 0;
    	for(int i = 0; i < this.getPlayers().size(); i++){
    		if (this.getPlayers().get(i).getKind().equals("Forward")){
    			amount = amount + 1;
    		}
    	}
    	return amount;
    }
    public int getAmountDefenders() {
    	int amount = 0;
    	for(int i = 0; i < this.getPlayers().size(); i++){
    		if (this.getPlayers().get(i).getKind().equals("Defender")){
    			amount = amount + 1;
    		}
    	}
    	return amount;
    }
    public int getAmountMidfielders() {
    	int amount = 0;
    	for(int i = 0; i < this.getPlayers().size(); i++){
    		if (this.getPlayers().get(i).getKind().equals("Midfielder")){
    			amount = amount + 1;
    		}
    	}
    	return amount;
    }
    public int getAmountGoalkeepers() {
    	int amount = 0;
    	for(int i = 0; i < this.getPlayers().size(); i++){
    		if (this.getPlayers().get(i).getKind().equals("Goalkeeper")){
    			amount = amount + 1;
    		}
    	}
    	return amount;
    }
    public int getAmountAllrounders() {
    	int amount = 0;
    	for(int i = 0; i < this.getPlayers().size(); i++){
    		if (this.getPlayers().get(i).getKind().equals("Allrounder")){
    			amount = amount + 1;
    		}
    	}
    	return amount;
    }
    
    /**
     * reset the cards and injury for each player of the team
     */
    public void resetCardReason(){
        for (Players player : players) {
            player.resetCardReason();
        }
    }
    
    public ArrayList<Players> getPlayersNotForSale(Competition competition){
    	ArrayList<Players> result= new ArrayList<Players>();
    	for(int i = 0; i < this.getPlayers().size(); i++){
    		if(competition.getMarket().getPlayersForSale().contains(this.getPlayers().get(i)) == false){
    			result.add(this.getPlayers().get(i));
    		}
    	}
    	return result;
    }
}
