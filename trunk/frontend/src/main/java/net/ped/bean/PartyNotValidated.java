package net.ped.bean;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import net.ped.model.Party;
import net.ped.service.front.FrontPartyService;

@ManagedBean
@ViewScoped
public class PartyNotValidated {

	private List<Party> listParty = new ArrayList<Party>();
	private Calendar dateGreen;
	private Calendar dateOrange;
	
	public PartyNotValidated(){
		listParty = FrontPartyService.getInstance().getAllPartiesNotValidated();
		
		dateGreen=new GregorianCalendar();
		dateGreen.add(Calendar.DATE, 20);
		
		dateOrange=new GregorianCalendar();
		dateOrange.add(Calendar.DATE, 10);
	}
	
	public List<Party> getListParty() {
		return listParty;
	}

	public void setListParty(List<Party> listParty) {
		this.listParty = listParty;
	}

	public Calendar getDateGreen() {
		return dateGreen;
	}

	public void setDateGreen(Calendar dateGreen) {
		this.dateGreen = dateGreen;
	}

	public Calendar getDateOrange() {
		return dateOrange;
	}

	public void setDateOrange(Calendar dateOrange) {
		this.dateOrange = dateOrange;
	}
}
