package application.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Competition {

    private Match competition[][] = new Match[34][9];
    private Team teams[] = new Team[18];
    private int saveGameId;
    private int results[][] = new int[18][4];
    private Market market = new Market();
    private String chosenTeamName;
    private String name;
    private int roundCounter;
    /*
     * Results: 2d array with 18 teams (corresponding id to teams[]) and:
     * [0]: Number of wins
     * [1]: Number of draws
     * [2]: Number of losses
     * [3]: Goal difference
     */

    /**
     * Creates a new competition object
     *
     * @param t an array containing all the teams of this competition
     */
    public Competition(Team[] t) {
        this.teams = t;
    }

    /**
     * Set the name
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the chosen team name
     *
     * @param cTN the chosen team name
     */
    public void setChosenTeamName(String cTN) {
        chosenTeamName = cTN;
    }

    /**
     * Get the chosen team name
     *
     * @return the chosen team name
     */
    public String getChosenTeamName() {
        return chosenTeamName;
    }

    /**
     * Returns the id of the savegame the competition belongs to
     *
     * @return id of she corresponding savegame
     */
    public int getSaveGameId() {
        return this.saveGameId;
    }

    /**
     * Sets the saveGameId
     *
     * @param id the id of the savegame this compettition belongs to
     */
    public void setSaveGameId(int id) {
        this.saveGameId = id;
    }

    /**
     * Returns an array with the 9 matches in the nth round of the competion
     *
     * @param n nth round in competition
     * @return array with the matches in the nth round
     */
    public Match[] getRound(int n) {
        return competition[n];
    }

    /**
     * Returns the lth match from the nth round
     *
     * @param n the index int for the match
     * @param l the index int for the round
     * @return a match object
     */
    public Match getMatch(int n, int l) {
        return competition[n][l];
    }

    /**
     * Adds a match to the nth round of the competition
     *
     * @param n int n the index of the round
     * @param l int l the index of the match
     * @param m the match
     */
    public void addMatch(int n, int l, Match m) {
        competition[n][l] = m;
    }

    /**
     * Gets a team from the competition
     *
     * @param name The name of the team
     * @return the team at the specified index
     */
    public Team getTeamByName(String name) {
        for (int i = 0; i < teams.length; i++) {
            if (teams[i].getName().equals(name)) {
                return teams[i];
            }
        }
        return null;
    }

    /**
     * Returns teams in this competition
     *
     * @return An array of the teams that are currently in the competition
     */
    public Team[] getTeams() {
        return teams;
    }

    /**
     * Convert this class to a string
     *
     * @return string with information about this class
     */
    @Override
    public String toString() {
        String returnString = "";
        returnString += "Competition [competition=";
        for (int i = 0; i < 34; i++) {
            for (int j = 0; j < 8; j++) {
                if (!(competition[i][j] == null)) {
                    returnString += competition[i][j].toString() + ",";
                }
            }
        }
        returnString += ", teams=" + Arrays.toString(teams) + "]";
        return returnString;
    }

    /**
     * Equals method
     *
     * @param obj object to compare to
     * @return if this and obj are equal
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Competition)) {
            return false;
        }

        Competition comp = (Competition) obj;
        //Compare the matches
        for (int i = 0; i < 34; i++) {
            for (int j = 0; j < 8; j++) {
                if ((comp.getMatch(i, j) == null) || competition[i][j] == null) {
                    if (!((comp.getMatch(i, j) == null) && competition[i][j] == null)) {
                        return false;
                    }
                } else {
                    if (!(comp.getMatch(i, j).equals(competition[i][j]))) {
                        return false;
                    }
                }
            }
        }
        return Arrays.equals(teams, comp.getTeams());
    }

    /**
     * Update the results
     */
    public void updateResults() {

        int rounds = getRound();

        // clear all results
        for (int i = 0; i < results.length; i++) {
            for (int j = 0; j < results[i].length; j++) {
                results[i][j] = 0;

            }
        }

        // recalculate the results
        int HomeTeamId = 0, VisitorTeamId = 0;
        for (int i = 0; i < rounds; i++) {
            for (int j = 0; j < competition[i].length; j++) {
                Match match = competition[i][j];
                //Determine the id of the Teams in the teams array
                try {
                    HomeTeamId = getTeamId(match.getHomeTeam());
                    VisitorTeamId = getTeamId(match.getVisitorTeam());
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
                // If draw
                if (match.getPointsHomeTeam() == match.getPointsVisitorTeam()) {
                    //Increase draws for both teams
                    results[HomeTeamId][1]++;
                    results[VisitorTeamId][1]++;
                } else //If the home team wins	
                if (match.getPointsHomeTeam() > match.getPointsVisitorTeam()) {
                    //Increase wins for the hometeam
                    results[HomeTeamId][0]++;
                    results[VisitorTeamId][2]++;
                    // Update the goal differenceaccordingly
                    results[HomeTeamId][3] += match.getPointsHomeTeam() - match.getPointsVisitorTeam();
                    results[VisitorTeamId][3] += match.getPointsVisitorTeam() - match.getPointsHomeTeam();
                } else // If the visitor team wins
                if (match.getPointsHomeTeam() < match.getPointsVisitorTeam()) {
                    //Increase wins for the hometeam
                    results[VisitorTeamId][0]++;
                    results[HomeTeamId][2]++;
                    // Update the goal difference
                    results[HomeTeamId][3] += match.getPointsHomeTeam() - match.getPointsVisitorTeam();
                    results[VisitorTeamId][3] += match.getPointsVisitorTeam() - match.getPointsHomeTeam();
                }
            }
        }

        // update the points of all teams
        for (Team t : teams) {
            int[] result = getPoints(t);
            t.setPoints(result[3]);
            t.setGoalDiff(result[4]);
            t.setWins(result[0]);
            t.setDraws(result[1]);
            t.setLosses(result[2]);
        }

    }

    /**
     * Returns the points of a team
     *
     * @param team The team to determine points of
     * @return int[] with: [0]-wins,[1]-draws,[2]-losses,[3]-total points,
     * [4]-goal difference
     */
    public int[] getPoints(Team team) {
        int teamId;
        int[] points = new int[5];
        try {
            teamId = getTeamId(team);
            for (int k = 0; k < 3; k++) {
                points[k] = results[teamId][k];
            }
            points[3] = results[teamId][0] * 3 + results[teamId][1];
            points[4] = results[teamId][3];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return points;
    }

    /**
     * Returs the index of the team object in the teams array
     *
     * @param Team t
     * @return the index of t in the teams array
     * @throws Exception
     */
    private int getTeamId(Team t) throws Exception {
        for (int k = 0; k < teams.length; k++) {
            if (teams[k].equals(t)) {
                return k;
            }
        }
        throw new Exception();
    }

    /**
     * Get the market
     *
     * @return the market
     */
    public Market getMarket() {
        return market;
    }

    /**
     * Set the market
     *
     * @param market the market
     */
    public void setMarket(Market market) {
        this.market = market;
    }

    /**
     * Get the team of a player
     *
     * @param player the player
     * @return team of the player
     */
    public Team getPlayersTeam(Players player) {
        for (Team team : teams) {
            if (team.getPlayers().contains(player)) {
                return team;
            }
        }
        return null;
    }

    /**
     * Generate the matches
     *
     * @param seed seed for random value
     */
    public void generateMatches(int seed) {
        //first getting an array of all the matches
        ArrayList<String> matches = new ArrayList<>();
        //ROUND ROBIN STYLE FOR THE FIRST 17 Matches
        for (int i = 0; i < teams.length - 1; i++) {
            for (int j = 0; j < teams.length / 2; j++) {
                int hometeamIndex = j;
                int awayTeamIndex = teams.length - 1 - j;
                competition[i][j] = new Match(teams[hometeamIndex], teams[awayTeamIndex]);
            }
            //Rotating the array
            Team temp = teams[teams.length - 1];
            for (int j = teams.length - 2; j >= 1; j--) {
                teams[j + 1] = teams[j];
            }
            teams[1] = temp;
        }
        // Swap the teams from the first 17 matches around to create the "returns"
        for (int i = 0; i < (teams.length - 1); i++) {
            int offset = teams.length - 1;
            for (int j = 0; j < teams.length / 2; j++) {
                competition[i + offset][j] = new Match(competition[i][j].getVisitorTeam(), competition[i][j].getHomeTeam());
            }
        }
    }

    /**
     * Get the current round number
     *
     * @return the number of the current round, returns -1 if the competition
     * has ended
     */
    public int getRound() {
        for (int i = 0; i < 34; i++) {
            if (this.competition[i][1].getPointsHomeTeam() == -1) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * Get the rank of the team
     *
     * @param team team to get the rank of
     * @return the rank (int)
     */
    public int getRank(Team team) {

        ArrayList<Team> sortedTeams = getAllRanks();

        //set rank to the index of the team in the sorted list
        int rank = sortedTeams.indexOf(team);

        //give teams with exactly the same amount of points and goals the same rank
        return rank + 1;
    }

    public ArrayList<Team> getAllRanks() {
        ArrayList<Team> sortedTeams = new ArrayList<>();
        for (Team t : teams) {
            sortedTeams.add(t);
        }

        //sort the list of teams based on points (if draw, based on goals)
        Collections.sort(sortedTeams, (Team t1, Team t2) -> {
            if (t2.getPoints() == t1.getPoints()) {
                if (t2.getGoalDiff() - t1.getGoalDiff() == 0) {
                    return t1.getName().compareTo(t2.getName());
                } else {
                    return t2.getGoalDiff() - t1.getGoalDiff();
                }
            } else {
                return t2.getPoints() - t1.getPoints();
            }
        });
        return sortedTeams;
    }

    /**
     * Reset the cards and injury for each player of each team of the
     * competition
     */
    public void resetCardReason() {
        for (Team team : teams) {
            team.resetCardReason();
        }
    }
}
