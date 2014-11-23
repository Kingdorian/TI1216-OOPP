
public class Goalkeeper extends Players {
	
	int stopPower;
	int penaltyStopPower;
	
	
	public Goalkeeper(int id, String name, String surName, int number, int status, int timeNotAvailable, int reason, int stopPower, int penalyStopPower){
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
