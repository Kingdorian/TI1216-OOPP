
public class Goalkeeper extends Players {
	
	int stopPower;
	int penaltyStopPower;
	
	/**
	 * Constructor
	 * 
	 * @param id Players unique ID.
	 * @param name First name of the player.
	 * @param surname Surname of the player.
	 * @param number Backnumber of the player.
	 * @param status Status if player is injured or suspended or both.
	 * @param timeNotAvailable Time that the player isn't available.
	 * @param reason Reason why the player is injured.
	 * @param stopPower Skill of the goalkeeper to stop goals.
	 * @param penalyStopPower Skill of the goalkeeper to stop penalty's. 
	 */
	public Goalkeeper(int id, String name, String surName, int number, Status status, int timeNotAvailable, Reason reason, int stopPower, int penalyStopPower){
		super(id, name, surName, number, status, timeNotAvailable, reason);
		
		this.stopPower = stopPower;
		this.penaltyStopPower = stopPower;
		
	}


	public int getStopPower() {
		return stopPower;
	}


	public void setStopPower(int stopPower) {
		this.stopPower = stopPower;
	}


	public int getPenaltyStopPower() {
		return penaltyStopPower;
	}


	public void setPenaltyStopPower(int penaltyStopPower) {
		this.penaltyStopPower = penaltyStopPower;
	}

}
