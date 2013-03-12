package net.ped.dao;

import net.ped.model.Customer;

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
	 * obtient un client
	 * @return Customer
	 */
	public Customer getCustomer(String firstname, String lastname, String mail) throws Exception;

}
