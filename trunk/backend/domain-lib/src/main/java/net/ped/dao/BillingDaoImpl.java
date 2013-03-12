package net.ped.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import net.ped.model.Customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BillingDaoImpl extends GenericDAO implements InterfaceBillingDao{

	private static final Logger LOG = LoggerFactory.getLogger(BillingDaoImpl.class);

	public void addCustomer(Customer c) throws Exception {
		LOG.info("> addCustomer");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction(); 
			tx.begin();
			em.persist(c); 
			tx.commit();
			LOG.debug("Ajout du client");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction addCustomer",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
	}

	public boolean containsCustomer(Customer c) throws Exception {
		LOG.info("> containsCustomer");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		long number=0;
		try{
			tx = em.getTransaction(); 
			tx.begin();
			Query query=em.createQuery("select count(p.id) FROM Customer p "+
					"where p.firstname=:param1 and "+
					"p.lastname=:param2 and "+
					"p.mail=:param3");
			query.setParameter("param1", c.getFirstname());
			query.setParameter("param2", c.getLastname());
			query.setParameter("param3", c.getMail());
			number = (Long)query.getSingleResult();
			LOG.debug("Total Count result = "+number);
			tx.commit();
			LOG.debug("calcul effectue");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction containsCustomer",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
		if(number==0)
			return false;
		return true;
	}

	public Customer getCustomer(String firstname, String lastname, String mail){
		LOG.info("> getCustomer");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		Customer c = new Customer();
		try{
			tx = em.getTransaction(); 
			tx.begin();
			Query query = em.createQuery("from Customer p " +
					"where p.firstname=:param1 and "+
					"p.lastname=:param2 and "+
					"p.mail=:param3");
			query.setParameter("param1", firstname);
			query.setParameter("param2", lastname);
			query.setParameter("param3", mail);
			c = (Customer)query.getSingleResult();
			tx.commit();
			LOG.debug("Client trouve");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction getCustomer",re);
			tx.rollback();
		}finally{
			closeEntityManager();
		}
		return c;
	}
}





