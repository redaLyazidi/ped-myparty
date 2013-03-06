package net.ped.bean;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	public AccueilBean(){
				
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
	
	public void preRenderView() {  
	      HttpSession session = ( HttpSession ) FacesContext.getCurrentInstance().getExternalContext().getSession( true );  
	} 
	 

}
