package net.ped.model;

public class ScannedTicket {

	private int idCustomer;
	private int idParty;
	private String secretCode;
	
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
}
