package application.model;

import application.Main;
import java.util.ArrayList;

public class Team {

    private String name;
    private ArrayList<Players> players = new ArrayList<>();
    private int budget, points, goals, goalsAgainst;
    boolean artificialGrass;

    /**
     * Constructor
     *
     * @param name Name of the team.
     * @param artificialGrass true if team plays on artificialGrass.
     */
    public Team(String name, boolean artificialGrass) {
        this.name = name;
        this.budget = 5000000;//Integer.MIN_VALUE;
        this.points = 0;
        this.goals = 0;
        this.goalsAgainst = 0;
        this.artificialGrass = artificialGrass;
    }

    /**
     * Constructor
     *
     * @param name Name of the team.
     * @param budget Budget of the team.
     * @param points The current points of the team.
     * @param goals The numbers of goals the team has made in the league.
     * @param goalsAgainst The numbers of goals scored against the team in the
     * league.
     * @param artificialGrass true if team plays on artificialGrass.
     */
    public Team(String name, int budget, int points, int goals, int goalsAgainst, boolean artificialGrass) {

        this.name = name;
        this.budget = budget;
        this.points = points;
        this.goals = goals;
        this.goalsAgainst = goalsAgainst;
        this.artificialGrass = artificialGrass;
    }

    public boolean hasArtificialGrass() {
        return artificialGrass;
    }

    public void setArtificialGrass(boolean artificialGrass) {
        this.artificialGrass = artificialGrass;
    }

    public boolean equals(Object other) {

        if (other instanceof Team) {
            Team team = (Team) other;
            if (this.name.equals(team.getName())
                    && this.budget == team.getBudget()
                    && this.points == team.getPoints()
                    && this.goals == team.getGoals()
                    && this.goalsAgainst == team.getGoalsAgainst()
                    && this.artificialGrass == team.hasArtificialGrass()) {
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
     * @param player Player which is changing team
     * @param team Team which te player goes to
     * @param money The transfersum
     * @return  true if the transfer was done succesfully
     */
    public boolean transferTo(Players player, Team team, int money) {
        if (this.getPlayers().contains(player) && team.budget > money) {
            this.getPlayers().remove(player);
            this.formatPlayerID();
            team.addPlayer(player);
            player.setNumber(team.getPlayers().size());
            this.setBudget(this.getBudget() + money);
            team.setBudget(team.getBudget() - money);
            
            // remove player from market
            Main.getCompetition().getMarket().removePlayer(player);
            
            // refresh budget in title bar
            Main.getTitleController().refreshMoney();
            
            return true;
        }
        return false;
    }

    private void formatPlayerID(){
        for(int i=0; i<players.size(); i++)
            players.get(i).setId(i);
    }
}
