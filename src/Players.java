
public abstract class Players {
	
	int id;
	String name;
	String surname;
	int number;

	Status status;
	int timeNotAvailable;
	Reason reason;
	
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
	 */
	public Players(int id, String name, String surname, int number, Status status, int timeNotAvailable, Reason reason){
		
		this.id = id;
		this.name = name;
		this.surname = surname;
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
		return surname;
	}

	public void setSurName(String surName) {
		this.surname = surName;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getTimeNotAvailable() {
		return timeNotAvailable;
	}

	public void setTimeNotAvailable(int timeNotAvailable) {
		this.timeNotAvailable = timeNotAvailable;
	}

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}
	

	public String toString() {
		return "Players [id=" + id + ", name=" + name + ", surname=" + surname
				+ ", number=" + number + ", status=" + status
				+ ", timeNotAvailable=" + timeNotAvailable + ", reason="
				+ reason + "]";
	}

}
