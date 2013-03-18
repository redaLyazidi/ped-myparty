package net.ped.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ped.constante.ConstantesWeb;
import net.ped.constante.ConstantesWeb.ThemeParty;
import net.ped.model.Artist;
import net.ped.model.Party;
import net.ped.service.front.FrontPartyService;

@ManagedBean
@SessionScoped
public class CreateParty implements Serializable{

	private static final Logger LOG = LoggerFactory.getLogger(CreateParty.class);
	private static final String imageUploadPath = "/resources/upload";

	private UploadedFile file;
	private String title;
	private String description;
	private int nbPlace;
	private double price;
	private String street;
	private String town;
	private String place;
	private String codepostal;
	private Date dateParty;
	private int selectedHour;
	private List<Integer> hour;
	private int selectedMinute;
	private List<Integer> minute;
	private Date dateBegin;
	private Date dateEnd;
	private String selectedTheme;
	private List<String> themes;

	private List<Artist> selectedArtists;
	private List<Artist> artists;
	private List<String> selectedTexts;

	private Party partyEdited;

	public CreateParty(){
		hour = new ArrayList<Integer>();
		minute = new ArrayList<Integer>();
		themes = new ArrayList<String>();

		for(int i=0; i<=23; i++){
			hour.add(i);
		}
		for(int j=0; j<60; j=j+5){
			minute.add(j);
		}
		for(ThemeParty t : ThemeParty.values()){
			themes.add(t.toString());
		}

		artists = FrontPartyService.getInstance().getAllArtists();
	}

	public UploadedFile getFile() {  
		return file;  
	}  

	public void setFile(UploadedFile file) {  
		this.file = file;  
	}  

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getSelectedTheme() {
		return selectedTheme;
	}

	public void setSelectedTheme(String selectedTheme) {
		this.selectedTheme = selectedTheme;
	}

	public List<String> getThemes() {
		return themes;
	}

	public void setThemes(List<String> themes) {
		this.themes = themes;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
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

	public String getCodepostal() {
		return codepostal;
	}

	public void setCodepostal(String codepostal) {
		this.codepostal = codepostal;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public Date getDateBegin() {
		return dateBegin;
	}

	public void setDateBegin(Date dateBegin) {
		this.dateBegin = dateBegin;
	}

	public Date getDateEnd() {
		return dateEnd;
	}

	public void setDateEnd(Date dateEnd) {
		this.dateEnd = dateEnd;
	}

	public Date getDateParty() {
		return dateParty;
	}

	public void setDateParty(Date dateParty) {
		this.dateParty = dateParty;
	}

	public int getSelectedHour() {
		return selectedHour;
	}

	public void setSelectedHour(int selectedHour) {
		this.selectedHour = selectedHour;
	}

	public int getSelectedMinute() {
		return selectedMinute;
	}

	public void setSelectedMinute(int selectedMinute) {
		this.selectedMinute = selectedMinute;
	}

	public List<Integer> getHour() {
		return hour;
	}

	public void setHour(List<Integer> hour) {
		this.hour = hour;
	}

	public List<Integer> getMinute() {
		return minute;
	}

	public void setMinute(List<Integer> minute) {
		this.minute = minute;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public List<String> getSelectedTexts() {
		return selectedTexts;
	}

	public void setSelectedTexts(List<String> selectedTexts) {
		this.selectedTexts = selectedTexts;
	}

	public List<String> complete(String query) {  
		List<String> suggestions = new ArrayList<String>();  

		for(Artist a : artists) {  
			if(a.getName().startsWith(query))  
				suggestions.add(a.getName());  
		}  
		return suggestions;  
	}

	private boolean validationDate(){
		if(dateBegin.after(dateEnd)){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "La date de début des préventes doit se situer avant la date de fin des préventes", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(dateBegin.after(dateParty) || dateEnd.after(dateParty)){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Les dates de début/fin de préventes doivent se situer avant la date de la party", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		if(Calendar.getInstance().getTime().after(dateParty) || Calendar.getInstance().getTime().after(dateBegin) || Calendar.getInstance().getTime().after(dateEnd)){
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible de choisir une date déjà passée", "");
			FacesContext.getCurrentInstance().addMessage(null, msg);
			return false;
		}
		return true;
	}

	public void save(){

		String filename="";
		if(file != null) {
			filename = FilenameUtils.getName(file.getFileName());
			InputStream input=null;
			OutputStream output=null;
			ServletContext sc = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String webappPath = sc.getRealPath("/");

			try {
				input = file.getInputstream();
				// à changer en mettant la destination de son workspace
				output = new FileOutputStream(new File(webappPath + imageUploadPath, filename));
				if( output == null)
					LOG.debug("REP FAUX\n");
				//				output = new FileOutputStream(new File("//D://Fac//AL//workspace//trunk//frontend//src//main//webapp//resources//upload" + "//", filename));
				IOUtils.copy(input, output);
			}catch(Exception e){
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(output);
			} 
		}

		if(!validationDate())
			return;

		Party party = new Party();
		party.setImage(filename);
		party.setTitle(title);
		party.setDescription(description);
		party.setNbPlace(nbPlace);
		party.setTheme(selectedTheme);
		party.setPrice(price);
		party.setStreet(street);
		party.setTown(town);
		party.setCP(codepostal);
		party.setPlace(place);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(dateBegin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(dateEnd);
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(dateParty);
		party.setDateBegin(cal1);
		party.setDateEnd(cal2);
		party.setDateParty(cal3);
		Calendar hourParty = new GregorianCalendar(0,0,0,selectedHour,selectedMinute);
		party.setTimeParty(hourParty);

		selectedArtists = new ArrayList<Artist>();
		for(String s : selectedTexts){
			LOG.debug("DEBUG: " + s);
			selectedArtists.add(FrontPartyService.getInstance().getArtistByName(s));
		}

		party.setArtists(selectedArtists);
		FrontPartyService.getInstance().addParty(party);

		// On réinitialise le bean createParty (pour ne pas garder les valeurs losqu'on va dans la vue newParty)
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.removeAttribute("createParty");

		FacesMessage msg = new FacesMessage("La party a été créée");
		FacesContext.getCurrentInstance().addMessage(null, msg); 
	}

	public String edit(int id){
		partyEdited = FrontPartyService.getInstance().getParty(id);
		title = partyEdited.getTitle();
		description = partyEdited.getDescription();
		nbPlace = partyEdited.getNbPlace();
		price = partyEdited.getPrice();
		street = partyEdited.getStreet();
		town = partyEdited.getTown();
		place = partyEdited.getPlace();
		codepostal = partyEdited.getCP();
		dateParty = partyEdited.getDateParty().getTime();
		selectedHour = partyEdited.getTimeParty().getTime().getHours();
		selectedMinute = partyEdited.getTimeParty().getTime().getMinutes();
		dateBegin = partyEdited.getDateBegin().getTime();
		dateEnd = partyEdited.getDateEnd().getTime();
		selectedTheme = partyEdited.getTheme();

		selectedTexts = new ArrayList<String>();
		for(Artist a : partyEdited.getArtists()){
			selectedTexts.add(a.getName());
		}

		return "editParty";
	}

	public String validateEdit(){

		if(!validationDate())
			return "";

		partyEdited.setTitle(title);
		partyEdited.setDescription(description);
		partyEdited.setNbPlace(nbPlace);
		partyEdited.setTheme(selectedTheme);
		partyEdited.setPrice(price);
		partyEdited.setStreet(street);
		partyEdited.setTown(town);
		partyEdited.setCP(codepostal);
		partyEdited.setPlace(place);
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(dateBegin);
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(dateEnd);
		Calendar cal3 = Calendar.getInstance();
		cal3.setTime(dateParty);
		partyEdited.setDateBegin(cal1);
		partyEdited.setDateEnd(cal2);
		partyEdited.setDateParty(cal3);
		Calendar hourParty = new GregorianCalendar(0,0,0,selectedHour,selectedMinute);
		partyEdited.setTimeParty(hourParty);

		selectedArtists = new ArrayList<Artist>();
		for(String s : selectedTexts){
			LOG.debug("DEBUG: " + s);
			selectedArtists.add(FrontPartyService.getInstance().getArtistByName(s));
		}

		partyEdited.setArtists(selectedArtists);
		FrontPartyService.getInstance().updateParty(partyEdited);

		// On réinitialise le bean createParty (pour ne pas garder les valeurs losqu'on va dans la vue newParty)
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.removeAttribute("createParty");

		return "accueil";
	}

	public String cancel(){
		// On réinitialise le bean createParty (pour ne pas garder les valeurs losqu'on va dans la vue newParty)
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
		session.removeAttribute("createParty");
		return "accueil";
	}

}

//<label class="labelForm">Image</label>
//<p:fileUpload value="#{createParty.file}" mode="simple" required="true" requiredMessage="Champ Image non rempli"/>
