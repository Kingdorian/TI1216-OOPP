package application.model;
public enum Status {
	DEFAULT(0),
	INJURED(1),
	SUSPENDED(2),
	INJUREDSUSPENDED(3);
	
	private Status(int status) {
		this.status = status;
	}
	
	int status;
}
