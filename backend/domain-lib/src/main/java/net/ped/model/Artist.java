package net.ped.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
//@XmlRootElement(name = "artist")
public class Artist implements java.io.Serializable{
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String type;
	
	public Artist() {

	}
	
	public Artist(String name, String type) {
		this.name = name;
		this.type = type;
	}

	//@XmlElement
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//@XmlElement
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//@XmlElement
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
