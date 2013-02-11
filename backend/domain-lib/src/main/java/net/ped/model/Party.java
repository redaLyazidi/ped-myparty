package net.ped.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Party implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String title;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateParty;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateBegin;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar dateEnd;
	private String description;
	private int nbPlace;
	private String theme;
	private double price;
	private int nbPlaceBought = 0;
	private int nbPlaceScanned = 0;
	
	@Embedded
	private Adress adress;
	
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name="PARTY_ARTIST", joinColumns = {@JoinColumn(name = "PARTY_FK")}, inverseJoinColumns = {@JoinColumn(name = "ARTIST_FK")})
	private List<Artist> artists = new ArrayList<Artist>();

	public Party() {

	}

	public Party(String title, Calendar dateParty, Calendar dateBegin, Calendar dateEnd,
			String description, int nbPlace, String theme, double price,
			Adress adress, List<Artist> artists) {
		this.title = title;
		this.dateParty = dateParty;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.description = description;
		this.nbPlace = nbPlace;
		this.theme = theme;
		this.price = price;
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
	
	public Calendar getDateParty() {
		return dateParty;
	}

	public void setDateParty(Calendar dateParty) {
		this.dateParty = dateParty;
	}

	public Calendar getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Calendar dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Calendar getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Calendar dateEnd) {
		this.dateEnd = dateEnd;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNbPlace() {
		return nbPlace;
	}

	public void setNbPlace(int nbPlace) {
		this.nbPlace = nbPlace;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNbPlaceBought() {
		return nbPlaceBought;
	}

	public void setNbPlaceBought(int nbPlaceBought) {
		this.nbPlaceBought = nbPlaceBought;
	}

	public int getNbPlaceScanned() {
		return nbPlaceScanned;
	}

	public void setNbPlaceScanned(int nbPlaceScanned) {
		this.nbPlaceScanned = nbPlaceScanned;
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
