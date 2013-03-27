package net.ped.service.front;

import javax.servlet.http.HttpServletRequest;

import net.ped.model.Customer;
import net.ped.model.Party;
import net.ped.model.Ticket;

public interface InterfaceFrontBillingService {

	/**
	 * ajoute un client
	 */
	public void addCustomer(Customer c);
	
	/**
	 * obtient un client à partir de nom/prenom/mail
	 * @return Customer
	 */
	public Customer getCustomer(String firstname, String lastname, String mail);
	
	/**
	 * obtient un client à partir de son identifiant
	 * @return Customer
	 */
	public Customer getCustomerById(int id);
	
	/**
	 * ajoute un ticket
	 * incrémente de 1 le nombre de places achetées pour la party
	 * met à jour la hashMap contenant l'historique des billets achetés pour la party
	 */
	public void addTicket(int idCustomer, int idParty);
	
	/**
	 * obtient un ticket
	 * @return Ticket
	 */
	public Ticket getTicket(int idCustomer, int idParty);
	
	
	/**
	 * Envoyer un mail
	 */
	public void sendMail(Customer customer, Party party, String ticketLink);
	
	/**
	 * 
	 * Obtient l'url du ticket pour le client
	 */
	public String getURL(HttpServletRequest req, Ticket ticket);
}
