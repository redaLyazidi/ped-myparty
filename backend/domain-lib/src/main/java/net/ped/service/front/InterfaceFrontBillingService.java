package net.ped.service.front;

import net.ped.model.Customer;

public interface InterfaceFrontBillingService {

	/**
	 * ajoute un client
	 */
	public void addCustomer(Customer c);
	
	/**
	 * obtient un client
	 * @return Customer
	 */
	public Customer getCustomer(String firstname, String lastname, String mail);
}
