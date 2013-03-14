package net.ped.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import net.ped.model.Customer;
import net.ped.constante.ConstantesWeb;
import net.ped.service.front.FrontBillingService;
import net.ped.service.front.FrontPartyService;

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
	
	
	@ManagedProperty(value="#{accueilBean}")
	private AccueilBean accueilBean;
	
	public PartyBean(){
		dateCourante = Calendar.getInstance();
	}
	
	public String buyTicket() {
		
		Customer c = new Customer(firstname, name, mail);
		FrontBillingService.getInstance().addCustomer(c);
		
		return "confirmBilling";
	}

	public boolean isDisableBuyButton() {
		if(accueilBean.getPartySelect().getDateBegin().after(Calendar.getInstance().getTime()) &&
				accueilBean.getPartySelect().getDateEnd().before(Calendar.getInstance().getTime()) &&
				accueilBean.getPartySelect().getNbPlace() <= accueilBean.getPartySelect().getNbPlaceBought()) {
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

	public AccueilBean getAccueilBean() {
		return accueilBean;
	}

	public void setAccueilBean(AccueilBean accueilBean) {
		this.accueilBean = accueilBean;
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
		FrontPartyService.getInstance().deleteParty(accueilBean.getIdParty());
		accueilBean.setListParty(FrontPartyService.getInstance().getPartiesNotBegunMaxResult(0, ConstantesWeb.NUMBER_PARTY_PAGE));
		return "accueil";
	}
	
}
