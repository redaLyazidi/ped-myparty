package net.ped.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import net.ped.model.Party;
import net.ped.service.front.FrontPartyService;
import net.ped.service.front.InterfaceFrontPartyService;

@ManagedBean
@SessionScoped
public class SearchParty implements Serializable{

	//InterfaceFrontPartyService service = new FrontPartyService();
	private String title;
	private String theme;
	private double priceMin=0.0;
	private double priceMax=30;
	private Date dateParty;
	private String lieu;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public double getPriceMin() {
		return priceMin;
	}
	public void setPriceMin(double priceMin) {
		this.priceMin = priceMin;
	}
	public double getPriceMax() {
		return priceMax;
	}
	public void setPriceMax(double priceMax) {
		this.priceMax = priceMax;
	}
	public Date getDateParty() {
		return dateParty;
	}
	public void setDateParty(Date dateParty) {
		this.dateParty = dateParty;
	}
	
	public void search(){
		List<Party> list = new ArrayList<Party>();
		Calendar dateParty2 = null;
		priceMin=0;
		priceMax=50.50;
		if(dateParty != null){
			dateParty2 = Calendar.getInstance();
			dateParty2.setTime(dateParty);
		}
		
		try {
			list = FrontPartyService.getInstance().getPartiesCriteria(priceMin, priceMax, dateParty2, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(Party p : list){
			System.out.println(p.getTitle());
		}
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	
}


