
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
	public Match getMatch(int l, int n){
		return competition[n][l];
	}
	/**
	 * Adds a match to the nth round of the competition
	 * @param int n the index of the round
	 * @param int l the index of the match
	 */
	public void addMatch(int n, int l,  Match m){
		competition[n][l] = m;
	}
	/**
	 * Gets a team from the competition
	 * @param The name of the team
	 * @return the team at the specified index
	 */
	public Team getTeamByName(String name){
		for(int i =0; i<teams.length;i++){
			if(teams[i].getName().equals(name)){
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
}
