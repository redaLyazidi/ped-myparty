package net.ped.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import net.ped.model.Customer;
import net.ped.model.Party;
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
	
	private Party partySelect;
	
	public PartyBean(){
		dateCourante = Calendar.getInstance();
	}
	
	public String showParty(int id){
		for(Party p : FrontPartyService.getInstance().getPartiesNotBegun()) {
			if (Integer.valueOf(id).compareTo(Integer.valueOf(p.getId())) == 0) {
				partySelect = p;
				break;
			}
		}	
		return "party";
	}
	
	public String buyTicket() {
		
		Customer c = new Customer(firstname, name, mail);
		FrontBillingService.getInstance().addCustomer(c);
		
		return "confirmBilling";
	}

	public boolean isDisableBuyButton() {
		if(partySelect.getDateBegin().after(Calendar.getInstance().getTime()) &&
		partySelect.getDateEnd().before(Calendar.getInstance().getTime()) &&
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
	
}
