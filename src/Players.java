
public abstract class Players {
	
	int id;
	String name;
	String surName;
	int number;
	int status;
	int timeNotAvailable;
	int reason;
	
	public Players(int id, String name, String surName, int number, int status, int timeNotAvailable, int reason){
		
		this.id = id;
		this.name = name;
		this.surName = surName;
		this.number = number;
		this.status = status;
		this.timeNotAvailable = timeNotAvailable;
		this.reason = reason;
		
	}
	
	public boolean equals(Object other){
		
		if(other instanceof Players){
			
			Players that = (Players)other;
			
			if(this.id == that.id){
				
				return true;
				
			}
			
		}
		
		return false;
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getTimeNotAvailable() {
		return timeNotAvailable;
	}

	public void setTimeNotAvailable(int timeNotAvailable) {
		this.timeNotAvailable = timeNotAvailable;
	}

	public int getReason() {
		return reason;
	}

	public void setReason(int reason) {
		this.reason = reason;
	}
	
		

}
