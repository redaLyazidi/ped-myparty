package net.ped.model;

public class ScannedTicket {

	private int idCustomer;
	private int idParty;
	private String secretCode;
	private boolean validated;
	
	public int getIdCustomer() {
		return idCustomer;
	}
	
	public void setIdCustomer(int idCustomer) {
		this.idCustomer = idCustomer;
	}
	
	public int getIdParty() {
		return idParty;
	}
	
	public void setIdParty(int idParty) {
		this.idParty = idParty;
	}
	
	public String getSecretCode() {
		return secretCode;
	}
	
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}
}
