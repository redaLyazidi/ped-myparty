package net.ped.model;

import javax.persistence.Embeddable;

@Embeddable
public class Adress {
	
	private String street;
	private String town;
	private String country;
	
	public Adress() {
		
	}

	public Adress(String street, String town, String country) {
		this.street = street;
		this.town = town;
		this.country = country;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
