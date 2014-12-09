package application.model;
import org.w3c.dom.Node;


public abstract class Players {
	
	private static int id;
	private int playerid;
	private String name;
	private String surname;
	private int number;

	private Status status;
	private int timeNotAvailable;
	private Reason reason;
	
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
	public Players(String name, String surname, int number, Status status, int timeNotAvailable, Reason reason){
		
		this.playerid = id;
		id++;
		this.name = name;
		this.surname = surname;
		this.number = number;
		this.status = status;
		this.timeNotAvailable = timeNotAvailable;
		this.reason = reason;
		
	}
	
	public boolean equals(Object other){
		
		if(other instanceof Players){
			Players p = (Players)other;
			if(	this.name.equals(p.getName())&&
				this.surname.equals(p.getSurName())&&
				this.number==p.getNumber()&&
				this.status==p.getStatus()&&
				this.timeNotAvailable==p.getTimeNotAvailable()&&
				this.reason==p.getReason()){
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
