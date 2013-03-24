package net.ped.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ped.model.Customer;
import net.ped.model.Party;
import net.ped.model.ScannedTicket;
import net.ped.model.ScannedTicketManuel;
import net.ped.model.Ticket;
import net.ped.model.User;

public class RESTDaoImpl extends GenericDAO implements InterfaceRESTDao{

	private static final Logger LOG = LoggerFactory.getLogger(RESTDaoImpl.class);

	public User connexion(String login, String password) {
		LOG.info("> connexion");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		User u =new User();
		try{
			tx = em.getTransaction();
			tx.begin();
			Query query = em.createQuery("from User u" +
					" where u.login=:param1 and" +
					" u.password=:param2");
			query.setParameter("param1", login);
			query.setParameter("param2", password);
			u = (User)query.getSingleResult();
			tx.commit();
			LOG.debug("la recherche a reussi");
		}catch (NoResultException nre) {
			LOG.debug("L'utilisateur n'existe pas");
		} catch (RuntimeException re) {
			LOG.error("Erreur dans la fonction connexion", re);
			throw re;
		}finally{
			closeEntityManager();
		}
		return u;
	}

	public Ticket validateTicket(ScannedTicket st) {
		LOG.info("> validateTicket");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		Ticket ticket =new Ticket();
		try{
			tx = em.getTransaction();
			tx.begin();
			Query query = em.createQuery("from Ticket u" +
					" where u.party.id=:param1 and" +
					" u.customer.id=:param2 and" +
					" u.secretCode=:param3");
			query.setParameter("param1", st.getIdParty());
			query.setParameter("param2", st.getIdCustomer());
			query.setParameter("param3", st.getSecretCode());
			ticket = (Ticket)query.getSingleResult();
			tx.commit();
			LOG.debug("la recherche a reussi");
		}catch (NoResultException nre) {
			LOG.debug("Le ticket n'existe pas");
		} catch (RuntimeException re) {
			LOG.error("Erreur dans la fonction validateTicket", re);
			throw re;
		}finally{
			closeEntityManager();
		}
		return ticket;
	}
	
	public Ticket validateTicketManuel(ScannedTicketManuel st){
		LOG.info("> validateTicketManuel");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		Ticket ticket =new Ticket();
		try{
			tx = em.getTransaction();
			tx.begin();
			Query query = em.createQuery("from Ticket u" +
					" where u.party.id=:param1 and" +
					" u.customer.id=:param2");
			query.setParameter("param1", st.getIdParty());
			query.setParameter("param2", st.getIdCustomer());
			ticket = (Ticket)query.getSingleResult();
			tx.commit();
			LOG.debug("la recherche a reussi");
		}catch (NoResultException nre) {
			LOG.debug("Le ticket n'existe pas");
		} catch (RuntimeException re) {
			LOG.error("Erreur dans la fonction validateTicket", re);
			throw re;
		}finally{
			closeEntityManager();
		}
		return ticket;
	}
	
	public void incrementScan(Party p) throws Exception{
		LOG.info("> incrementScan");
		EntityManager em = createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
			em.merge(p);
			tx.commit();
			LOG.debug("Modification de la party");
		} catch (Exception re) {
			if (tx != null)
				LOG.error("Erreur dans la fonction incrementScan", re);
			tx.rollback();
			throw re;
		} finally {
			closeEntityManager();
		}
	}
}
