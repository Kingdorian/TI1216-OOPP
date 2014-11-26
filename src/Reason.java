
public enum Reason {
	
	DEFAULT(0),
	HARMSTRING(1),
	KNEE(2),
	HEAD(3),
	ANKEL(4);
	
	private Reason(int reason) {
		this.reason = reason;
	}
	
	int reason;
}
