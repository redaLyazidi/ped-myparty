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

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;


import net.ped.constante.ConstantesWeb.ThemeParty;
import net.ped.model.Party;
import net.ped.service.front.FrontPartyService;

@ManagedBean
@ViewScoped
public class CreateParty implements Serializable{

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
	private int selectedMinute;
	private List<Integer> hour;
	private List<Integer> minute;
	private Date dateBegin;
	private Date dateEnd;
	private String selectedTheme;
	private List<String> themes;

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

	public void save(){

		String filename="";
		if(file != null) {
			filename = FilenameUtils.getName(file.getFileName());
			InputStream input=null;
			OutputStream output=null;
			try {
				input = file.getInputstream();
				// à changer en mettant la destination de son workspace
				output = new FileOutputStream(new File("//D://Fac//AL//workspace//trunk//frontend//WebContent//resources//upload" + "//", filename));
				IOUtils.copy(input, output);
			}catch(Exception e){
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(input);
				IOUtils.closeQuietly(output);
			} 
		}  

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
		FrontPartyService.getInstance().addParty(party);

		FacesMessage msg = new FacesMessage("Succesful", "La party a été créée");  
		FacesContext.getCurrentInstance().addMessage(null, msg); 
	}
}
