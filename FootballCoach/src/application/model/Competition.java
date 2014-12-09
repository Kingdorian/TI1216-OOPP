package application.model;

import java.util.Arrays;

public class Competition {
	private Match competition[][] = new Match[34][9]; 
	private Team teams[] = new Team[18];
	
	/**
	 * Creates a new competition object
	 */
	public Competition(Team[] t){
		this.teams = t;
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
}
