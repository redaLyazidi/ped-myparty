package net.ped.bean;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.model.chart.CartesianChartModel;
import org.primefaces.model.chart.LineChartSeries;

import net.ped.model.Customer;
import net.ped.model.Party;
import net.ped.constante.ConstantesWeb;
import net.ped.service.front.FrontBillingService;
import net.ped.service.front.FrontPartyService;
import net.ped.shared.Commons;

@ManagedBean(name="partyBean")
@SessionScoped
public class PartyBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean disableBuyButton;
	private Calendar dateCourante;
	
	//Réservation d'un ticket
	private String mail;
	private String name;
	private String firstname;
	
	private Party partySelect;
	private CartesianChartModel chartTicket;
	private String urlTicket;
	
	public PartyBean(){
		dateCourante = Calendar.getInstance();
		disableBuyButton = false;
	}
	
	public String showPartyFromAccueil(int id){
		
		partySelect = FrontPartyService.getInstance().getParty(id);
		
		//récupère l'URL du ticket si il existe
		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (request == null)
		{
			throw new RuntimeException("Sorry. Got a null request from faces context");
		}
		urlTicket = Commons.getPartySvgURL(request, partySelect.getId()).toString();
		System.out.println(urlTicket);
		createChartTicket();
		return "party";
	}
	
	public String showPartyFromNotValidated(int id){
		
		partySelect = FrontPartyService.getInstance().getParty(id);
		
		createChartTicket();
		return "party";
	}
	
	public String buyTicket() {
		
		Customer c = new Customer(firstname, name, mail);
		FrontBillingService.getInstance().addCustomer(c);
		Customer customer = FrontBillingService.getInstance().getCustomer(firstname, name, mail);
		FrontBillingService.getInstance().addTicket(customer.getId(), partySelect.getId());
		FrontBillingService.getInstance().sendMail(mail);
		//customer-test@hotmail.fr
		
		return "confirmBilling";
	}
	
	private void createChartTicket() {  
    	
		chartTicket = new CartesianChartModel();
		
		Map<Calendar,Integer> ticketSold = partySelect.getStatTicketSold();
		
		LineChartSeries serie = new LineChartSeries();  
        serie.setLabel("tickets vendus");
        
        Set<Calendar> keySetData = ticketSold.keySet();
        Set<Calendar> dataSorted = new TreeSet<Calendar>();
		for(Calendar c : keySetData) {
			dataSorted.add(c);
		}
        for(Calendar c : dataSorted) {
        	serie.set(c.getTime(), ticketSold.get(c));
    	}
        
        chartTicket.addSeries(serie);
    } 

	public boolean isDisableBuyButton() {
		if(partySelect.getDateBegin().after(Calendar.getInstance()) ||
		partySelect.getDateEnd().before(Calendar.getInstance()) ||
		partySelect.getNbPlace() <= partySelect.getNbPlaceBought()) {
			disableBuyButton = true;
		}
		else {
			disableBuyButton = false;
		}
		return disableBuyButton;
	}

	public void setDisableBuyButton(boolean disableBuyButton) {
		this.disableBuyButton = disableBuyButton;
	}

	public Party getPartySelect() {
		return partySelect;
	}

	public void setPartySelect(Party partySelect) {
		this.partySelect = partySelect;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public Calendar getDateCourante() {
		return dateCourante;
	}

	public void setDateCourante(Calendar dateCourante) {
		this.dateCourante = dateCourante;
	}

	public String deleteParty(){
		FrontPartyService.getInstance().deleteParty(partySelect.getId());
		return "accueil";
	}
	
	public void createTicket() throws IOException {
//		 FacesContext.getCurrentInstance().getExternalContext().redirect("http://localhost:8080/myparty-frontend/resources/ticket-designer/svg-editor.html?idParty="+partySelect.getId());
		FacesContext.getCurrentInstance().getExternalContext().redirect("../ticket-designer/svg-editor.html?idParty="+partySelect.getId());
	}

	public CartesianChartModel getChartTicket() {
		return chartTicket;
	}

	public void setChartTicket(CartesianChartModel chartTicket) {
		this.chartTicket = chartTicket;
	}
	
	public String getUrlTicket() {
		return urlTicket;
	}

	public void setUrlTicket(String urlTicket) {
		this.urlTicket = urlTicket;
	}

	public String reserveParty(){
		mail = "";
		name = "";
		firstname = "";
		return "reserveParty";
	}
	
	public void validateParty(){
		FrontPartyService.getInstance().ValidateParty(partySelect.getId());
		FacesMessage msg = new FacesMessage("La party a été validée");
		FacesContext.getCurrentInstance().addMessage(null, msg); 
	}
}
