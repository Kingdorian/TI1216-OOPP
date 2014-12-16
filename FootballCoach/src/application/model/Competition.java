package application.model;

import java.util.Arrays;

public class Competition {
	private Match competition[][] = new Match[34][9]; 
	private Team teams[] = new Team[18];
	private int saveGameId;
	private int results[][] = new int[18][4];
	/*
	 * Results: 2d array with 18 teams (corresponding id to teams[]) and:
	 * [0]: Number of wins
	 * [1]: Number of draws
	 * [2]: Number of losses
	 * [3]: Goal difference
	 */
	
	/**
	 * Creates a new competition object
	 */
	public Competition(Team[] t){
		this.teams = t;
	}
	/**
	 * Returns the id of the savegame the competition belongs to
	 * @return id of she corresponding savegame
	 */
	public int getSaveGameId(){
		return this.saveGameId;
	}
	/**
	 * Sets the saveGameId
	 * @param the id of the savegame this compettition belongs to
	 */
	
	public void setSaveGameId(int id){
		this.saveGameId = id;
	}

	/**
	 * Returns an array with the 9 matches in the nth round of the competion
	 * @param nth round in competition
	 * @return array with the matches in the nth round
	 */
	public Match[] getRound(int n){
		return competition[n];
	}
	/**
	 * Returns the lth match from the nth round
	 * @param the index int for the match
	 * @param the index int for the round
	 * @return a match object
	 */
	public Match getMatch(int n, int l){
		return competition[n][l];
	}
	/**
	 * Adds a match to the nth round of the competition
	 * @param int n the index of the round
	 * @param int l the index of the match
	 */
	public void addMatch(int n, int l,  Match m){
		System.out.println(n);
		competition[n][l] = m;
	}
	/**
	 * Gets a team from the competition
	 * @param The name of the team
	 * @return the team at the specified index
	 */
	public Team getTeamByName(String name){
		for(int i =0; i<teams.length;i++){
			if(teams[i]!=null&&teams[i].getName().equals(name)){
				return teams[i];
			}
		}
		return null;
	}
	/**
	 * Returns teams in this competition
	 * @return An array of the teams that are currently in the competition
	 */
	public Team[] getTeams(){
		return teams;
	}
	public String toString() {
		String returnString = "";
		returnString+="Competition [competition=";
		for(int i = 0; i<34;i++){
			for(int j = 0; j<8;j++){
				if(!(competition[i][j]==null))
				returnString+=competition[i][j].toString()+",";
			}
		}
		returnString+= ", teams=" + Arrays.toString(teams) + "]";
		return returnString;
	}

	public boolean equals(Object obj) {
		if(!(obj instanceof Competition)){
			return false;
		}
		
		Competition comp = (Competition)obj;
		//Compare the matches
		for(int i = 0; i<34;i++){
			for(int j = 0; j<8;j++){
				if((comp.getMatch(i,j)==null)||competition[i][j]==null){
					if(!((comp.getMatch(i,j)==null)&&competition[i][j]==null)){
						return false;
					}
				}else{
					if(!(comp.getMatch(i,j).equals(competition[i][j]))){
						return false;
					}
				}
			}
		}
		if(!Arrays.equals(teams, comp.getTeams())){
			return false;
		}
		//If it passes trough all of the above return true
		return true;
	}
	
	public void updateResults(){
		int HomeTeamId = 0, VisitorTeamId = 0;
		for(int i = 0; i<competition.length;i++){
			for(int j = 0; j<competition[i].length;j++){
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
				if(match.getPointsHomeTeam()==match.getPointsVisitorTeam()){
					//Increase draws for both teams
					results[HomeTeamId][1]++;
					results[VisitorTeamId][1]++;
				}else 
				//If the home team wins	
				if(match.getPointsHomeTeam()>match.getPointsVisitorTeam()){
					//Increase wins for the hometeam
					results[HomeTeamId][0]++;
					results[VisitorTeamId][2]++;
					// Update the goal differenceaccordingly
					results[HomeTeamId][3]+=match.getPointsHomeTeam()-match.getPointsVisitorTeam();
					results[VisitorTeamId][3]+=match.getPointsVisitorTeam()-match.getPointsHomeTeam();
				}else
				// If the visitor team wins
				if(match.getPointsHomeTeam()<match.getPointsVisitorTeam()){
					//Increase wins for the hometeam
					results[VisitorTeamId][0]++;
					results[HomeTeamId][2]++;
					// Update the goal difference
					results[HomeTeamId][3]+=match.getPointsHomeTeam()-match.getPointsVisitorTeam();
					results[VisitorTeamId][3]+=match.getPointsVisitorTeam()-match.getPointsHomeTeam();
				}
			}
		}
	}
	/**
	 * Returns the points of a team
	 * @param The team to determine poitns f
	 * @return int[] with: [0]-wins,[1]-draws,[2]-losses,[3]-goal difference, [4]-total points
	 */
	public int[] getPoints(Team team){
		int teamId;
		int[] points = new int[5];
		try {
			teamId = getTeamId(team);
			for(int k = 0; k<3; k++)
				points[k] = results[teamId][k];
			points[3]= results[teamId][0]*3+results[teamId][1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return points;
		}

	/**
	 * Returs the index of the team object in the teams array
	 * @param Team t
	 * @return the index of t in the teams array
	 * @throws Exception
	 */
	private int getTeamId(Team t) throws Exception{
		for(int k = 0; k < teams.length; k++){
			if(teams[k].equals(t)){
				return k;
			}
		}
		throw new Exception();
	}
}
