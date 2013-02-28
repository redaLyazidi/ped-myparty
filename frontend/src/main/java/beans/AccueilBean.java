package beans;

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

import constantes.ConstantesWeb;
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
		numPage = 1;
		listParty = FrontPartyService.getInstance().getPartiesNotBegunMaxResult(numPage, ConstantesWeb.NUMBER_PARTY_PAGE);
		
		/*
		Adress a1 = new Adress("20 rue du bidon", "Lyon", "6900");
		Adress a2 = new Adress("12 place bidon", "La Rochelle", "17000");
		Adress a3 = new Adress("1 avenue bidon", "Bordeaux", "33000");
		Adress a4 = new Adress("3 rue bidon", "Bordeaux", "33000");
		Adress a5 = new Adress("45 avenue bidon", "Paris", "75000");
		Adress a6 = new Adress("52 rue bidon", "Paris", "75000");
		Adress a7 = new Adress("38 avenue bidon", "Paris", "75000");
		Adress a8 = new Adress("20 rue bidon", "Niort", "79000");
		
		Calendar date = new GregorianCalendar(2013, 04, 15);
		
		Party p1 = new Party();
		p1.setTitle("Super concert");
		p1.setDateParty(date);
		p1.setAdress(a1);
		p1.setPrice(12);

		Party p2 = new Party();
		p2.setTitle("Guy tar");
		p2.setDateParty(date);
		p2.setAdress(a2);
		p2.setPrice(15);
		
		Party p3 = new Party();
		p3.setTitle("Concert 3 blabla");
		p3.setDateParty(date);
		p3.setAdress(a3);
		p3.setPrice(18);
		
		Party p4 = new Party();
		p4.setTitle("Concert 4 blabla");
		p4.setDateParty(date);
		p4.setAdress(a4);
		p4.setPrice(49);
		
		Party p5 = new Party();
		p5.setTitle("Concert 5 blabla");
		p5.setDateParty(date);
		p5.setAdress(a5);
		p5.setPrice(25);
		
		Party p6 = new Party();
		p6.setTitle("Concert 6 blabla");
		p6.setDateParty(date);
		p6.setAdress(a6);
		p6.setPrice(18);
		
		Party p7 = new Party();
		p7.setTitle("Concert 7 blabla");
		p7.setDateParty(date);
		p7.setAdress(a7);
		p7.setPrice(30);
		
		Party p8 = new Party();
		p8.setTitle("Concert 8 blabla");
		p8.setDateParty(date);
		p8.setAdress(a8);
		p8.setPrice(40);
		
		listParty.add(p1);
		listParty.add(p2);
		listParty.add(p3);
		listParty.add(p4);
		listParty.add(p5);
		listParty.add(p6);
		listParty.add(p7);
		listParty.add(p8);
		*/
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
