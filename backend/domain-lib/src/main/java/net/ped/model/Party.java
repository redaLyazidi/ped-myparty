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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
//@XmlRootElement(name = "party")
public class Party implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	@Temporal(TemporalType.DATE)
	private Calendar dateParty;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar timeParty;
	@Temporal(TemporalType.DATE)
	private Calendar dateBegin;
	@Temporal(TemporalType.DATE)
	private Calendar dateEnd;
	private String description;
	private int nbPlace;
	private String theme;
	private double price;
	private int nbPlaceBought = 0;
	private int nbPlaceScanned = 0;
	private String image;
	private boolean validated = false;
	
	private String street;
	private String town;
	private String CP;
	private String place;
	
	@ManyToMany
	@JoinTable(name="PARTY_ARTIST", joinColumns = {@JoinColumn(name = "PARTY_FK")}, inverseJoinColumns = {@JoinColumn(name = "ARTIST_FK")})
	private List<Artist> artists = new ArrayList<Artist>();

	public Party() {

	}

	public Party(String title, Calendar dateParty, Calendar timeParty,
			Calendar dateBegin, Calendar dateEnd, String description,
			int nbPlace, String theme, double price, String image, String street, String town, String CP, String place, List<Artist> artists) {
		this.title = title;
		this.dateParty = dateParty;
		this.timeParty = timeParty;
		this.dateBegin = dateBegin;
		this.dateEnd = dateEnd;
		this.description = description;
		this.nbPlace = nbPlace;
		this.theme = theme;
		this.price = price;
		this.image = image;
		this.street = street;
		this.town = town;
		this.CP = CP;
		this.place = place;
		this.artists = artists;
	}

	//@XmlElement
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	//@XmlElement
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	//@XmlElement(type=Date.class)
	public Calendar getDateParty() {
		return dateParty;
	}

	public void setDateParty(Calendar dateParty) {
		this.dateParty = dateParty;
	}
	
	public Calendar getTimeParty() {
		return timeParty;
	}

	public void setTimeParty(Calendar timeParty) {
		this.timeParty = timeParty;
	}

	//@XmlElement(type=Date.class)
	public Calendar getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Calendar dateBegin) {
		this.dateBegin = dateBegin;
	}

	//@XmlElement(type=Date.class)
	public Calendar getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Calendar dateEnd) {
		this.dateEnd = dateEnd;
	}

	//@XmlElement
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	//@XmlElement
	public int getNbPlace() {
		return nbPlace;
	}

	public void setNbPlace(int nbPlace) {
		this.nbPlace = nbPlace;
	}

	//@XmlElement
	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	//@XmlElement
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	//@XmlElement
	public int getNbPlaceBought() {
		return nbPlaceBought;
	}

	public void setNbPlaceBought(int nbPlaceBought) {
		this.nbPlaceBought = nbPlaceBought;
	}

	//@XmlTransient
	public int getNbPlaceScanned() {
		return nbPlaceScanned;
	}

	public void setNbPlaceScanned(int nbPlaceScanned) {
		this.nbPlaceScanned = nbPlaceScanned;
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

	public String getCP() {
		return CP;
	}

	public void setCP(String CP) {
		this.CP = CP;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}
	
	//@XmlElement
	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public boolean isValidated() {
		return validated;
	}

	public void setValidated(boolean validated) {
		this.validated = validated;
	}
}
