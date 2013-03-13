package net.ped.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.ped.model.Artist;
import net.ped.model.Party;
import net.ped.model.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PartyDaoImpl extends GenericDAO implements InterfacePartyDao{

	private static final Logger LOG = LoggerFactory.getLogger(PartyDaoImpl.class);

	public void addArtist(Artist a) throws Exception{
		LOG.info("> addArtist");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction(); 
			tx.begin();
			em.persist(a); 
			tx.commit();
			LOG.debug("Ajout de l'artiste");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction addArtist",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
	}

	public void deleteArtist(int id){
		LOG.info("> deleteArtist");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction(); 
			tx.begin();
			Artist a = em.find(Artist.class, id);
			if (a == null) {
				LOG.error("Cet artiste n'existe pas");
				throw new Exception();
			}
			em.remove(a);
			tx.commit();
			LOG.debug("Suppression de l'artiste");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction deleteArtist",re);
			tx.rollback();
		}finally{
			closeEntityManager();
		}
	}
	
	public Artist getArtistByName(String name) throws Exception{
		LOG.info("> getArtistByName");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		Artist a = new Artist();
		try{
			tx = em.getTransaction(); 
			tx.begin();
			Query query = em.createQuery("from Artist u where u.name=:param");
			query.setParameter("param", name);
			a = (Artist)query.getSingleResult();
			tx.commit();
			LOG.debug("Artiste trouve");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction getArtistByName",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
		return a;
	}
	
	public List<Artist> getAllArtists() throws Exception{
		LOG.info("> getAllArtists");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		List<Artist> list=new ArrayList<Artist>();
		try{
			tx = em.getTransaction();
			tx.begin();
			list = em.createQuery("from Artist u").getResultList();
			tx.commit();
			LOG.debug("la recherche a reussi, taille du resultat : "+ list.size());
		} catch (RuntimeException re) {
			LOG.error("Erreur dans la fonction getAllArtists", re);
			throw re;
		}finally{
			closeEntityManager();
		}
		return list;
	}

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
	
	public List<Party> getAllPartiesNotValidated(){
		LOG.info("> getAllPartiesNotValidated");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		List<Party> list=new ArrayList<Party>();
		try{
			tx = em.getTransaction();
			tx.begin();
			list = em.createQuery("from Party u inner join fetch u.artists " +
			"where u.validated=false").getResultList();
			tx.commit();
			LOG.debug("la recherche a reussi, taille du resultat : "+ list.size());
		} catch (RuntimeException re) {
			LOG.error("Erreur dans la fonction getAllPartiesNotValidated", re);
			throw re;
		}finally{
			closeEntityManager();
		}
		return list;
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
	
	public boolean containsParty(int id)throws Exception{
		LOG.info("> containsParty");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		long number=0;
		try{
			tx = em.getTransaction(); 
			tx.begin();
			Query query=em.createQuery("select count(p.id) FROM Party p where p.id=:param");
			query.setParameter("param", id);
			number = (Long)query.getSingleResult();
			LOG.debug("Total Count result = "+number);
			tx.commit();
			LOG.debug("calcul effectue");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction containsParty",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
		if(number==0)
			return false;
		return true;
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
					"inner join fetch u.artists where u.dateParty>:param and u.validated=true");
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
					"inner join fetch u.artists where u.dateParty>:param and u.validated=true order by u.dateParty");
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
	
	public int getNbPartiesNotBegun() throws Exception {
		long nbParties = 0;
		
		LOG.info("> getNbPartiesNotBegun");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction(); 
			tx.begin();
			Query query=em.createQuery("select count(p.id) from Party p " +
					"where p.dateParty>:param and p.validated=true");
			query.setParameter("param", Calendar.getInstance());
			nbParties = (Long)query.getSingleResult();
			LOG.debug("Total Count result = " + nbParties);
			tx.commit();
			LOG.debug("calcul effectue");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction getNbPartiesNotBegun",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
		
		return (int) nbParties;
	}

	public List<Party> getPartiesCriteria(int startPosition, int length, double priceBegin, double priceEnd, Calendar date, Calendar time) throws Exception{
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
			
			Predicate p0 = criteria.equal(from.get("validated"), true);
			list.add(p0);
			
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
			query.setFirstResult(startPosition);
			query.setMaxResults(length);
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
	
	public void addUser(User u) throws Exception{
		LOG.info("> addUser");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		try{
			tx = em.getTransaction(); 
			tx.begin();
			em.persist(u); 
			tx.commit();
			LOG.debug("Ajout du user");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction addUser",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
	}
	
	public User getUser(int id) throws Exception{
		LOG.info("> getUser");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		User u = new User();
		try{
			tx = em.getTransaction(); 
			tx.begin();
			Query query = em.createQuery("from User u where u.id=:param");
			query.setParameter("param", id);
			u = (User)query.getSingleResult();
			tx.commit();
			LOG.debug("User trouve");
		}catch(Exception re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction getUser",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
		return u;
	}
	
	public User login(String login, String password) throws Exception {
		LOG.info("> login");
		EntityManager em = createEntityManager(); 
		EntityTransaction tx = null;
		User u = new User();
		try{
			tx = em.getTransaction(); 
			tx.begin();
			Query query = em.createQuery("from User u where u.login=:login and u.password=:password");
			query.setParameter("login", login);
			query.setParameter("password", password);
			u = (User)query.getSingleResult();
			tx.commit();
			LOG.debug("User trouve, login ok");
		}catch (NoResultException nre) {
			LOG.debug("L'utilisateur n'existe pas");
		}catch(RuntimeException re){
			if(tx!=null)
				LOG.error("Erreur dans la fonction login",re);
			tx.rollback();
			throw re;
		}finally{
			closeEntityManager();
		}
		return u;
	}


}
