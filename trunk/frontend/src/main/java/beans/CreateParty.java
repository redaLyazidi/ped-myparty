package beans;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.primefaces.model.UploadedFile;

import net.ped.model.Party;
import net.ped.service.front.FrontPartyService;

@ManagedBean
@SessionScoped
public class CreateParty implements Serializable{

	//InterfaceFrontPartyService service = new FrontPartyService();
	private UploadedFile file;
	private String title;
	private String description;
	private int nbPlace;
	private String theme;
	private double price;
	private String street;
	private String town;
	private String place;
	private String codepostal;
	private Date dateParty;
	private int hour;
	private int minute;
	private Date dateBegin;
	private Date dateEnd;

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

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
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
		party.setTheme(theme);
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
		Calendar hourParty = new GregorianCalendar(0,0,0,hour,minute);
		party.setTimeParty(hourParty);
		FrontPartyService.getInstance().addParty(party);
		
		FacesMessage msg = new FacesMessage("Succesful", "La party a été créée");  
		FacesContext.getCurrentInstance().addMessage(null, msg); 
	}

}
