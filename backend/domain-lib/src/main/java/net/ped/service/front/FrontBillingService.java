package net.ped.service.front;

import java.util.Calendar;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ped.dao.BillingDaoImpl;
import net.ped.dao.InterfaceBillingDao;
import net.ped.dao.InterfacePartyDao;
import net.ped.dao.PartyDaoImpl;
import net.ped.model.Customer;
import net.ped.model.Party;
import net.ped.model.Ticket;
import net.ped.utils.Mail;

import static java.lang.Math.round;
import static java.lang.Math.random;
import static java.lang.Math.pow;
import static java.lang.Math.abs;
import static java.lang.Math.min;
import static org.apache.commons.lang3.StringUtils.leftPad;

public class FrontBillingService implements InterfaceFrontBillingService{

	private InterfaceBillingDao dao = new BillingDaoImpl();
	private InterfacePartyDao daoParty = new PartyDaoImpl();
	private static FrontBillingService instance;
	
	private static final Logger LOG = LoggerFactory
			.getLogger(FrontBillingService.class);
	
	private FrontBillingService() {
		instance = this;
	}
	
	public static FrontBillingService getInstance() {
		if(instance == null) {
			instance = new FrontBillingService();
		}
		
		return instance;
	}
	
	private static String generate(int length) {
	    StringBuffer sb = new StringBuffer();
	    for (int i = length; i > 0; i -= 12) {
	      int n = min(12, abs(i));
	      sb.append(leftPad(Long.toString(round(random() * pow(36, n)), 36), n, '0'));
	    }
	    return sb.toString();
	  }
	
	public void addCustomer(Customer c) {
		boolean exist = false;
		try {
			exist = dao.containsCustomer(c);
			if(!exist){
				dao.addCustomer(c);
			}
			else
				return;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Customer getCustomer(String firstname, String lastname, String mail) {
		Customer customer = new Customer();
		try {
			customer = dao.getCustomer(firstname, lastname, mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}
	
	public Customer getCustomerById(int id){
		Customer customer = new Customer();
		try {
			customer = dao.getCustomerById(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return customer;
	}
	
	public void addTicket(int idCustomer, int idParty){
		String secretCode = generate(16);
		try {
			Customer c = dao.getCustomerById(idCustomer);
			Party p = daoParty.getParty(idParty);
			
			//incrémente le nombre de places achetées
			p.setNbPlaceBought(p.getNbPlaceBought() + 1);
			
			//met à jour la hashMap contenant l'historique des achats
			boolean exist = false;
			for(Calendar mapKey : p.getStatTicketSold().keySet()){
				if(mapKey.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH) && 
						mapKey.get(Calendar.DAY_OF_MONTH) == Calendar.getInstance().get(Calendar.DAY_OF_MONTH)){
					p.getStatTicketSold().put(mapKey, p.getStatTicketSold().get(mapKey) + 1);
					exist = true;
					break;
				}
			}
			if(!exist){
				p.getStatTicketSold().put(Calendar.getInstance(), 1);
			}
			daoParty.updateParty(p);
			
			Ticket t = new Ticket(p, c, secretCode);
			dao.addTicket(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Ticket getTicket(int idCustomer, int idParty){
		Ticket ticket = new Ticket();
		try {
			ticket = dao.getTicket(idCustomer, idParty);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ticket;
	}
	
	public void sendMail(String dest) {
		try {
			Mail.getInstance().sendMail(dest);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
}
