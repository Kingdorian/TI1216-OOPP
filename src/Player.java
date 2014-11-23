
public class Player extends Players {

	int attack;
	int defence;
	int stamina;
	
	public Player(int id, String name, String surName, int number, int status,
			int timeNotAvailable, int reason, int attack, int defence, int stamina) {
		super(id, name, surName, number, status, timeNotAvailable, reason);
		
		this.attack = attack;
		this.defence = defence;
		this.stamina = stamina;
		
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
		this.attack = attack;
	}

	public int getDefence() {
		return defence;
	}

	public void setDefence(int defence) {
		this.defence = defence;
	}

	public int getStamina() {
		return stamina;
	}

	public void setStamina(int stamina) {
		this.stamina = stamina;
	}

}
