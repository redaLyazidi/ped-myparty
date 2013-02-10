package net.ped.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

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
		// TODO Auto-generated method stub
		
	}

	public void deleteParty(Party p) throws Exception{
		// TODO Auto-generated method stub
		
	}

	public List<Party> getAllParties() throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

	public Party getParty(int id) throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

	public List<Party> getPartiesCriteria() throws Exception{
		// TODO Auto-generated method stub
		return null;
	}

}
