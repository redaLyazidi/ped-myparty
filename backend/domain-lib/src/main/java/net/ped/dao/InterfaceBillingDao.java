package net.ped.dao;

import net.ped.model.Customer;
import net.ped.model.Ticket;

public interface InterfaceBillingDao {
	
	/**
	 * ajoute un client
	 */
	public void addCustomer(Customer c) throws Exception;
	
	/**
	 * vérifie si le client existe
	 * @return boolean
	 */
	public boolean containsCustomer(Customer c) throws Exception;
	
	/**
	 * obtient un client à partir de nom/prenom/mail
	 * @return Customer
	 */
	public Customer getCustomer(String firstname, String lastname, String mail) throws Exception;
	
	/**
	 * obtient un client à partir de son identifiant
	 * @return Customer
	 */
	public Customer getCustomerById(int id) throws Exception;
	
	/**
	 * ajoute un ticket
	 */
	public void addTicket(Ticket t) throws Exception;
	
	/**
	 * obtient un ticket
	 * @return Ticket
	 */
	public Ticket getTicket(int idCustomer, int idParty) throws Exception;

}
