package net.ped.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Party implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	
	@Embedded
	private Adress adress;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="PARTY_ARTIST", joinColumns = {@JoinColumn(name = "PARTY_FK")}, inverseJoinColumns = {@JoinColumn(name = "ARTIST_FK")})
	private List<Artist> artists = new ArrayList<Artist>();

	public Party() {

	}

	public Party(String title, Adress adress, List<Artist> artists) {
		this.title = title;
		this.adress = adress;
		this.artists = artists;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Adress getAdress() {
		return adress;
	}

	public void setAdress(Adress adress) {
		this.adress = adress;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}	
}
