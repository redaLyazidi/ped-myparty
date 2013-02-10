package net.ped.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.ped.filter.JpaUtil;

public abstract class GenericDAO {
	
	static private EntityManager em;
	
	public static EntityManager createEntityManager(){
		//em = JpaUtil.getEntityManager();
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("domain-lib");
		em = factory.createEntityManager();
		
		return em;	
	}
	
	public static void closeEntityManager(){
		em.close();
	}
}
