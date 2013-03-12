package net.ped.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
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
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private List<Party> listParty = new ArrayList<Party>();
	private boolean disableButtonPrev;
	private boolean disableButtonNext;
	private int numPage;
	private Party partySelect;
	
	private String place;
	private double priceMin=0;
	private double priceMax=0;
	private Date dateParty;
	private int selectedHour;
	private int selectedMinute;
	
	private List<Integer> hour;
	private List<Integer> minute;
	
	
	public AccueilBean(){
		hour = new ArrayList<Integer>();
		minute = new ArrayList<Integer>();
		
		for(int i=0; i<=23; i++){
			hour.add(i);
		}
		for(int j=0; j<60; j=j+5){
			minute.add(j);
		}
				
		listParty = new ArrayList<Party>();
		numPage = 0;
		listParty = FrontPartyService.getInstance().getPartiesNotBegunMaxResult(numPage, ConstantesWeb.NUMBER_PARTY_PAGE);

	}
	
	public String outcome() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ELContext el = facesContext.getELContext();
		Application app = facesContext.getApplication();
		ExpressionFactory ef = app.getExpressionFactory();
		ValueExpression ve = ef.createValueExpression(el, "#{partyBean}", PartyBean.class);
		PartyBean b = new PartyBean();
		
		this.partySelect = getPartySelect(facesContext);
		b.setPartySelect(partySelect);
		ve.setValue(el, b);

		// puis redirection vers ce bean
		return "party";
	}
	
	//get value from "f:param"
	public Party getPartySelect(FacesContext fc){
		Map<String,String> params = fc.getExternalContext().getRequestParameterMap();
		Party party = null;
		for(Party p : listParty) {
			if (Integer.valueOf(params.get("idParty")).compareTo(Integer.valueOf(p.getId())) == 0) {
				party = p;
				break;
			}
		}
			
		return party;
	}
	
	
	public void nextPage() {
		numPage ++;
		listParty.clear();
		listParty = FrontPartyService.getInstance().getPartiesNotBegunMaxResult(numPage, ConstantesWeb.NUMBER_PARTY_PAGE);
	}
	
	public void prevPage() {
		numPage --;
		listParty.clear();
		listParty = FrontPartyService.getInstance().getPartiesNotBegunMaxResult(numPage, ConstantesWeb.NUMBER_PARTY_PAGE);
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
		if(listParty.size() < ConstantesWeb.NUMBER_PARTY_PAGE) {
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

	public Party getPartySelect() {
		return partySelect;
	}

	public void setPartySelect(Party partySelect) {
		this.partySelect = partySelect;
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

	public void search(){
		Calendar dateParty2 = null;
		if(dateParty != null){
			dateParty2 = Calendar.getInstance();
			dateParty2.setTime(dateParty);
		}
		
		try {
			listParty = FrontPartyService.getInstance().getPartiesCriteria(0,ConstantesWeb.NUMBER_PARTY_PAGE,priceMin, priceMax, dateParty2, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void preRenderView() {  
	      HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );  
	} 
	 

}
