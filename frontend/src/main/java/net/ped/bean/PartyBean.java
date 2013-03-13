package net.ped.bean;

import java.io.Serializable;
import java.util.Calendar;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import net.ped.model.Party;

@ManagedBean(name="partyBean")
@SessionScoped
public class PartyBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean disableBuyButton;
	
	@ManagedProperty(value="#{accueilBean}")
	private AccueilBean accueilBean;
	
	public String buyTicket() {
		return "buyTicket";
	}

	public boolean isDisableBuyButton() {
		if(accueilBean.getPartySelect().getDateBegin().after(Calendar.getInstance().getTime()) &&
				accueilBean.getPartySelect().getDateEnd().before(Calendar.getInstance().getTime()) &&
				accueilBean.getPartySelect().getNbPlace() > accueilBean.getPartySelect().getNbPlaceBought()) {
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
}
