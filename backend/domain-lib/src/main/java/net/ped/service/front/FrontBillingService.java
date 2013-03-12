package net.ped.service.front;

import net.ped.dao.BillingDaoImpl;
import net.ped.dao.InterfaceBillingDao;
import net.ped.model.Customer;

public class FrontBillingService implements InterfaceFrontBillingService{

	private InterfaceBillingDao dao = new BillingDaoImpl();
	private static FrontBillingService instance;
	
	private FrontBillingService() {
		instance = this;
	}
	
	public static FrontBillingService getInstance() {
		if(instance == null) {
			instance = new FrontBillingService();
		}
		
		return instance;
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
}
