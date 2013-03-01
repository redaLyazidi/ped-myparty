package net.ped.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.ped.model.Party;

@ManagedBean(name="partyBean")
@SessionScoped
public class PartyBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean disableBuyButton;
	
	private Party partySelect;
	private int nbPlaceRest;
	
	public PartyBean() {
		if(partySelect == null ) {
			System.out.println("yataaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		}
		else {
			System.out.println(partySelect.getTitle());
		}
	}

	public Party getPartySelect() {
		return partySelect;
	}

	public void setPartySelect(Party partySelect) {
		this.partySelect = partySelect;
	}
	
	public String buyTicket() {
		return "buyTicket";
	}

	public boolean isDisableBuyButton() {
		if(partySelect.getDateBegin().after(Calendar.getInstance().getTime()) &&
				partySelect.getDateEnd().before(Calendar.getInstance().getTime()) &&
				partySelect.getNbPlace() > partySelect.getNbPlaceBought()) {
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

	public int getNbPlaceRest() {
			nbPlaceRest = partySelect.getNbPlace() - partySelect.getNbPlaceBought();
		return nbPlaceRest;
	}

	public void setNbPlaceRest(int nbPlaceRest) {
		this.nbPlaceRest = nbPlaceRest;
	}

}
