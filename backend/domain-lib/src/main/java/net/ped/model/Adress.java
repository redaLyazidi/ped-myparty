package net.ped.model;

import javax.persistence.Embeddable;
import javax.xml.bind.annotation.XmlElement;

@Embeddable
public class Adress {
	
	private String street;
	private String town;
	private String CP;
	
	public Adress() {
		
	}

	public Adress(String street, String town, String CP) {
		this.street = street;
		this.town = town;
		this.CP = CP;
	}

	//@XmlElement
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	//@XmlElement
	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	//@XmlElement
	public String getCP() {
		return CP;
	}

	public void setCountry(String CP) {
		this.CP = CP;
	}
}
