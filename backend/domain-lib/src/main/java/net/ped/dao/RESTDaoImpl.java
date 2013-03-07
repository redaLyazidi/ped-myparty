package net.ped.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
}
