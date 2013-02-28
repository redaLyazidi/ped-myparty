package net.ped.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.ped.model.Party;

@ManagedBean(name="partyBean")
@ViewScoped
public class PartyBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Party partySelect;

	public Party getPartySelect() {
		return partySelect;
	}

	public void setPartySelect(Party partySelect) {
		this.partySelect = partySelect;
	}

}
