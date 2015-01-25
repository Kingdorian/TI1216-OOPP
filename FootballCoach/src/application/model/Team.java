package application.model;

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
     * @param artificalGrass true if team plays on artificialGrass.
     */
    public Team(String name, boolean artificalGrass) {
        this.name = name;
        this.budget = Integer.MIN_VALUE;
        this.points = 0;
        this.goals = 0;
        this.goalsAgainst = 0;
        this.artificialGrass = artificalGrass;
    }

    /**
     * Constructor
     *
     * @param name the name of the team
     * @param artificalGrass if the team has artificial grass
     * @param imgUrl the image url
     */
    public Team(String name, boolean artificalGrass, String imgUrl) {
        this(name, artificalGrass);
        this.setImgUrl(imgUrl);
    }

    /**
     * Set the image url
     *
     * @param imgUrl the image url
     */
    public final void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    /**
     * Get the image url
     *
     * @return the image url
     */
    public String getImgUrl() {
        return this.imgUrl;
    }

    /**
     * Get if this team has artificial grass
     *
     * @return if this team has artificial grass
     */
    public boolean hasArtificialGrass() {
        return artificialGrass;
    }

    /**
     * Set if this team has artificial grass
     *
     * @param artificialGrass if this team has artificial grass
     */
    public void setArtificialGrass(boolean artificialGrass) {
        this.artificialGrass = artificialGrass;
    }

    /**
     * Get the player of this team
     *
     * @return the player of this team
     */
    public ArrayList<Players> getPlayers() {
        return players;
    }

    /**
     * Getthe available player of this team
     *
     * @return the available players of this team
     */
    public ArrayList<Players> getAvailablePlayers() {
        ArrayList<Players> res = new ArrayList<>();
        int keepers = 0;
        for (Players pl : players) {
            if (pl.getCard() != Card.RED && pl.getReason() == Reason.DEFAULT) {
                if (pl instanceof Goalkeeper) {
                    keepers++;
                }
                res.add(pl);
            }
        }
        //return all players if there aren't enough players to put in the field
        if (res.size() - keepers < 11) {
            return getPlayers();
        } else {
            return res;
        }
    }

    /**
     * Set the players of this team
     *
     * @param players the players of this team
     */
    public void setPlayers(ArrayList<Players> players) {
        this.players = players;
    }

    /**
     * Equals method
     *
     * @param obj object to compare to
     * @return if this and obj are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof Team)) {
            return false;
        }
        Team other = (Team) obj;
        if (artificialGrass != other.hasArtificialGrass()) {
            return false;
        }
        if (budget != other.getBudget()) {
            return false;
        }
        if (draws != other.getDraws()) {
            return false;
        }
        if (goalDiff != other.getGoalDiff()) {
            return false;
        }
        if (goals != other.getGoals()) {
            return false;
        }
        if (goalsAgainst != other.getGoalsAgainst()) {
            return false;
        }
        if (other.getImgUrl() == null) {
            if (imgUrl != null) {
                return false;
            }
        } else if (!imgUrl.equals(other.getImgUrl())) {
            return false;
        }
        if (losses != other.getLosses()) {
            return false;
        }
        if (name == null) {
            if (other.getName() != null) {
                return false;
            }
        } else if (!name.equals(other.getName())) {
            return false;
        }
        if (!players.equals(other.getPlayers())) {
            return false;
        }
        if (points != other.getPoints()) {
            return false;
        }
        return wins == other.getWins();
    }

    /**
     * Get the name of the team
     *
     * @return the name of the team
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the name of this team
     *
     * @param name the name of the team
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the budget of this team
     *
     * @return the budget of this team
     */
    public int getBudget() {
        return budget;
    }

    /**
     * Set the budget of this team
     *
     * @param budget the budget of the team
     */
    public void setBudget(int budget) {
        this.budget = budget;
    }

    /**
     * Get the amount of points of this team
     *
     * @return the amount of points of this team
     */
    public int getPoints() {
        return points;
    }

    /**
     * Set the amount of points of this team
     *
     * @param points the amount of points of the team
     */
    public void setPoints(int points) {
        this.points = points;
    }

    /**
     * Get the amount of goals of this team
     *
     * @return the amount of goals of this team
     */
    public int getGoals() {
        return goals;
    }

    /**
     * Set the amount of goals of this team
     *
     * @param goals the amount of goals of the team
     */
    public void setGoals(int goals) {
        this.goals = goals;
    }

    /**
     * Get the amount of goals scored against this team
     *
     * @return the amount of goals scored against this team
     */
    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    /**
     * Set the amount of goals scored against this team
     *
     * @param goalsAgainst the amount of goals scored against this team
     */
    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    /**
     * Returns a string representation of the Team object
     *
     * @return a string containing information of about this class
     */
    @Override
    public String toString() {
        return "Team [name=" + name + ", players=" + players.toString() + ", budget="
                + budget + ", points=" + points + ", goals=" + goals
                + ", goalsAgainst=" + goalsAgainst + ", artificialGrass="
                + artificialGrass + "]";
    }

    /**
     * Adds a player to the team
     *
     * @param p player to add to the team
     */
    public void addPlayer(Players p) {
        players.add(p);
    }

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
            this.formatNumbers();
            team.formatNumbers();
            this.setBudget(this.getBudget() + money);
            team.setBudget(team.getBudget() - money);

            return true;
        }
        return false;
    }

    /**
     * Reformat the numbers of the players
     */
    private void formatNumbers(){
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setNumber(i);
        }
    }
    
    /**
     * Reformat the ID's of the players
     */
    private void formatPlayerID() {
        for (int i = 0; i < players.size(); i++) {
            players.get(i).setId(i);
        }
    }

    /**
     * Get the goal difference
     *
     * @return the goal difference
     */
    public int getGoalDiff() {
        return goalDiff;
    }

    /**
     * Set the goal difference
     *
     * @param goalDiff the goal difference
     */
    public void setGoalDiff(int goalDiff) {
        this.goalDiff = goalDiff;
    }

    /**
     * Get the amount of matches won
     *
     * @return the amount of matches won
     */
    public int getWins() {
        return wins;
    }

    /**
     * Set the amount of wins
     *
     * @param wins the amount of wins
     */
    public void setWins(int wins) {
        this.wins = wins;
    }

    /**
     * Get the amount of draws
     *
     * @return the amount of draws
     */
    public int getDraws() {
        return draws;
    }

    /**
     * Set the amount of draws
     *
     * @param draws the amount of draws
     */
    public void setDraws(int draws) {
        this.draws = draws;
    }

    /**
     * Get the amount of losses
     *
     * @return the amount of losses
     */
    public int getLosses() {
        return losses;
    }

    /**
     * Set the amount of losses
     *
     * @param losses the amount of losses
     */
    public void setLosses(int losses) {
        this.losses = losses;
    }

    /**
     * Get the amount of forwards
     *
     * @param comp the current competition
     * @return the amount of forwards
     */
    public int getAmountForwards(Competition comp) {
        int amount = 0;
        ArrayList<Players> notForSale = getPlayersNotForSale(comp);
        for (int i = 0; i < notForSale.size(); i++) {
            if (notForSale.get(i).getKind().equals("Forward")) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * Get the amount of defenders
     *
     * @param comp the current competition
     * @return the amount of defenders
     */
    public int getAmountDefenders(Competition comp) {
        int amount = 0;
        ArrayList<Players> notForSale = getPlayersNotForSale(comp);
        for (int i = 0; i < notForSale.size(); i++) {
            if (notForSale.get(i).getKind().equals("Defender")) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * Get the amount of midfielders
     *
     * @param comp the current competition
     * @return the amount of midfielders
     */
    public int getAmountMidfielders(Competition comp) {
        int amount = 0;
        ArrayList<Players> notForSale = getPlayersNotForSale(comp);
        for (int i = 0; i < notForSale.size(); i++) {
            if (notForSale.get(i).getKind().equals("Midfielder")) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * Get the amount of goalkeepers
     *
     * @param comp the current competition
     * @return the amount of goalkeepers
     */
    public int getAmountGoalkeepers(Competition comp) {
        int amount = 0;
        ArrayList<Players> notForSale = getPlayersNotForSale(comp);
        for (int i = 0; i < notForSale.size(); i++) {
            if (notForSale.get(i).getKind().equals("Goalkeeper")) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * Get the amount of allrounders
     *
     * @param comp the current competition
     * @return the amount of allrounders
     */
    public int getAmountAllrounders(Competition comp) {
        int amount = 0;
        ArrayList<Players> notForSale = getPlayersNotForSale(comp);
        for (int i = 0; i < notForSale.size(); i++) {
            if (notForSale.get(i).getKind().equals("Allrounder")) {
                amount++;
            }
        }
        return amount;
    }

    /**
     * reset the cards and injury for each player of the team
     */
    public void resetCardReason() {
        for (Players player : players) {
            player.resetCardReason();
        }
    }

    /**
     * Get the players that are not for sale
     *
     * @param competition the current competition
     * @return the amount of players not for sale
     */
    public ArrayList<Players> getPlayersNotForSale(Competition competition) {
        ArrayList<Players> result = new ArrayList<>();
        for (int i = 0; i < this.getPlayers().size(); i++) {
            if (competition.getMarket().getPlayersForSale().contains(this.getPlayers().get(i)) == false) {
                result.add(this.getPlayers().get(i));
            }
        }
        return result;
    }
}
