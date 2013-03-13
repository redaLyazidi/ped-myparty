package net.ped.service.front;

import net.ped.model.Customer;
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
	 */
	public void addTicket(int idCustomer, int idParty);
	
	/**
	 * obtient un ticket
	 * @return Ticket
	 */
	public Ticket getTicket(int idCustomer, int idParty);
}