package net.ped.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import net.ped.constante.ConstantesWeb;

import net.ped.model.Party;
import net.ped.service.front.FrontPartyService;

@ManagedBean(name="accueilBean")
@ViewScoped
public class AccueilBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<Party> listParty = new ArrayList<Party>();
	private boolean disableButtonPrev;
	private boolean disableButtonNext;
	private int numPage;
	private int nbPages;
	
	private String place;
	private double priceMin=0;
	private double priceMax=0;
	private Date dateParty;
	private int selectedHour;
	private int selectedMinute;
	private Calendar datePartyCriteria;
	private Calendar timeCriteria;

	private List<Integer> hour;
	private List<Integer> minute;
	
	private boolean searchMode;
	private boolean emptySearch;

	public AccueilBean(){
		hour = new ArrayList<Integer>();
		minute = new ArrayList<Integer>();
		
		searchMode = false;

		for(int i=0; i<=23; i++){
			hour.add(i);
		}
		for(int j=0; j<60; j=j+5){
			minute.add(j);
		}

		listParty = new ArrayList<Party>();
		
		initPages();
	}
	
	public void initPages() {
		
		numPage = 1;
		//La numérotation des pages commencent à 1 mais les parties commencent à 0, d'où le numPage -1
		listParty = FrontPartyService.getInstance().getPartiesNotBegunMaxResult((numPage - 1)*ConstantesWeb.NUMBER_PARTY_PAGE, ConstantesWeb.NUMBER_PARTY_PAGE);
		int nbParties = FrontPartyService.getInstance().getNbPartiesNotBegun();
		if(nbParties == ConstantesWeb.NUMBER_PARTY_PAGE) {
			nbPages = 1;
		}
		else {
			nbPages = (nbParties / ConstantesWeb.NUMBER_PARTY_PAGE) + 1;
		}
	}
	
	public void deleteSearch() {
		searchMode = false;
		
		this.place = "";
		this.priceMin = 0;
		this.priceMax = 0;
		this.dateParty = null;
		this.selectedHour = 0;
		this.selectedMinute = 0;
		emptySearch = false;
		
		initPages();
	}

	public void gotoPage(int page) {
		numPage = page;
		listParty.clear();
		
		listParty = FrontPartyService.getInstance().getPartiesNotBegunMaxResult((numPage - 1)*ConstantesWeb.NUMBER_PARTY_PAGE, ConstantesWeb.NUMBER_PARTY_PAGE);
	}
	
	public void nextPage() {
		numPage ++;
		listParty.clear();
		
		listParty = FrontPartyService.getInstance().getPartiesNotBegunMaxResult((numPage - 1)*ConstantesWeb.NUMBER_PARTY_PAGE, ConstantesWeb.NUMBER_PARTY_PAGE);
	}

	public void prevPage() {
		numPage --;
		listParty.clear();
		
		listParty = FrontPartyService.getInstance().getPartiesNotBegunMaxResult((numPage - 1)*ConstantesWeb.NUMBER_PARTY_PAGE, ConstantesWeb.NUMBER_PARTY_PAGE);
	}

	public List<Party> getListParty() {
		return listParty;
	}

	public void setListParty(List<Party> listParty) {
		this.listParty = listParty;
	}

	public int getNumPage() {
		return numPage;
	}

	public void setNumPage(int numPage) {
		this.numPage = numPage;
	}

	public boolean isDisableButtonPrev() {
		if(numPage == 1) {
			disableButtonPrev = true;
		}
		else {
			disableButtonPrev = false;
		}

		return disableButtonPrev;
	}

	public void setDisableButtonPrev(boolean disableButtonPrev) {
		this.disableButtonPrev = disableButtonPrev;
	}

	public boolean isDisableButtonNext() {
		if(numPage == nbPages) {
			disableButtonNext = true;
		}
		else {
			disableButtonNext = false;
		}

		return disableButtonNext;
	}

	public void setDisableButtonNext(boolean disableButtonNext) {
		this.disableButtonNext = disableButtonNext;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
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

	public void preRenderView() {  
		HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );  
	}

	public int getNbPages() {
		return nbPages;
	}

	public void setNbPages(int nbPages) {
		this.nbPages = nbPages;
	}

	public void search(){
		
		if(dateParty != null){
			datePartyCriteria = Calendar.getInstance();
			datePartyCriteria.setTime(dateParty);
		}
		if(selectedHour!=0){
			timeCriteria = new GregorianCalendar(0, 0, 0, selectedHour, selectedMinute, 00);
		}
		
		try {
			listParty.clear();
			listParty = FrontPartyService.getInstance().getPartiesCriteria(0,ConstantesWeb.NUMBER_PARTY_PAGE, place, priceMin, priceMax, datePartyCriteria, timeCriteria);
			
			if(listParty.isEmpty()) {
				emptySearch = true;
			}
			else {
				emptySearch = false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		searchMode = true;
	}

	public boolean isSearchMode() {
		return searchMode;
	}

	public void setSearchMode(boolean searchMode) {
		this.searchMode = searchMode;
	}

	public boolean isEmptySearch() {
		return emptySearch;
	}

	public void setEmptySearch(boolean emptySearch) {
		this.emptySearch = emptySearch;
	}
}
