package net.ped.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.ped.model.Party;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PartyDaoImpl extends GenericDAO implements InterfacePartyDao{
	
	private static final Logger LOG = LoggerFactory.getLogger(PartyDaoImpl.class);

	public void addParty(Party p) throws Exception{
		LOG.info("> addParty");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction(); 
			tx.begin();
			em.persist(p); 
			tx.commit();
			LOG.debug("Ajout de la party");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction addParty",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
	}

	public void updateParty(Party p) throws Exception{
		LOG.info("> updateParty");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction(); 
			tx.begin();
			em.merge(p); 
			tx.commit();
			LOG.debug("Modification de la party");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction updateParty",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
	}

	public void deleteParty(int id) throws Exception{
		LOG.info("> deleteParty");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction(); 
			tx.begin();
			Party p = em.find(Party.class, id);
			if (p == null) {
				LOG.error("Cette party n'existe pas");
				throw new Exception();
			}
			em.remove(p);
			tx.commit();
			LOG.debug("Suppression de la party");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction deleteParty",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
	}
	
	public Party getParty(int id) throws Exception{
		LOG.info("> getParty");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		Party p = new Party();
		try{
			tx = em.getTransaction(); 
			tx.begin();
			Query query = em.createQuery("from Party u " +
			"inner join fetch u.artists where u.id=:param");
			query.setParameter("param", id);
			p = (Party)query.getSingleResult();
			tx.commit();
			LOG.debug("Party trouvee");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction getParty",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
		return p;
	}

	public List<Party> getAllParties() throws Exception{
		LOG.info("> getAllParties");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		List<Party> list=new ArrayList<Party>();
		try{
			tx = em.getTransaction();
			tx.begin();
			list = em.createQuery("from Party u inner join fetch u.artists").getResultList();
			tx.commit();
			LOG.debug("la recherche a reussi, taille du resultat : "+ list.size());
		} catch (RuntimeException re) {
			LOG.error("Erreur dans la fonction getAllParties", re);
			throw re;
		}finally{
			closeEntityManager();
		}
		return list;
	}
	
	public List<Party> getPartiesNotBegun() throws Exception {
		LOG.info("> getPartiesNotBegun");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		List<Party> list=new ArrayList<Party>();
		try{
			tx = em.getTransaction();
			tx.begin();
			Query query = em.createQuery("from Party u " +
			"inner join fetch u.artists where u.dateParty>:param");
			query.setParameter("param", Calendar.getInstance());
			list = query.getResultList();
			tx.commit();
			LOG.debug("la recherche a reussi, taille du resultat : "+ list.size());
		} catch (RuntimeException re) {
			LOG.error("Erreur dans la fonction getPartiesNotBegun", re);
			throw re;
		}finally{
			closeEntityManager();
		}
		return list;
	}
	
	public List<Party> getPartiesNotBegunMaxResult(int startPosition, int length)
			throws Exception {
		LOG.info("> getPartiesNotBegunMaxResult");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		List<Party> list=new ArrayList<Party>();
		try{
			tx = em.getTransaction();
			tx.begin();
			Query query = em.createQuery("from Party u " +
			"inner join fetch u.artists where u.dateParty>:param order by u.dateParty");
			query.setParameter("param", Calendar.getInstance());
			query.setFirstResult(startPosition);
			query.setMaxResults(length);
			list = query.getResultList();
			tx.commit();
			LOG.debug("la recherche a reussi, taille du resultat : "+ list.size());
		} catch (RuntimeException re) {
			LOG.error("Erreur dans la fonction getPartiesNotBegunMaxResult", re);
			throw re;
		}finally{
			closeEntityManager();
		}
		return list;
	}

	public List<Party> getPartiesCriteria(double priceBegin, double priceEnd, Calendar date, Calendar time) throws Exception{
		LOG.info("> getPartiesCriteria");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		List<Party> results = new ArrayList<Party>();

		try{
			tx = em.getTransaction();
			tx.begin();
			CriteriaBuilder criteria = em.getCriteriaBuilder();
			CriteriaQuery<Party> criteriaQuery = criteria.createQuery(Party.class);
			Root<Party> from = criteriaQuery.from(Party.class);
			criteriaQuery.select(from);
			
			List<Predicate> list = new ArrayList<Predicate>();
			if(priceBegin != 0 && priceEnd != 0){
				LOG.debug("ajout prix");
				Predicate p1 = criteria.between(from.<Double>get("price"), priceBegin, priceEnd);
				list.add(p1);
			}
			
			if(date != null){
				LOG.debug("ajout date");
				Predicate p2 = criteria.equal(from.get("dateParty"), date);
				list.add(p2);
			}
			
			if(time != null){
				LOG.debug("ajout time");
				Predicate p3 = criteria.equal(from.get("timeParty"), time);
				list.add(p3);
			}

			Predicate[] predicates = new Predicate[list.size()];
			list.toArray(predicates);
			criteriaQuery.where(predicates);

			TypedQuery<Party> query = em.createQuery(criteriaQuery);
			results = query.getResultList();
			tx.commit();
			LOG.debug("la recherche a reussi, taille du resultat : "+ results.size());
		} catch (RuntimeException re) {
			LOG.error("Erreur dans la fonction getPartiesCriteria", re);
			throw re;
		}finally{
			closeEntityManager();
		}
		return results;
	}


}
