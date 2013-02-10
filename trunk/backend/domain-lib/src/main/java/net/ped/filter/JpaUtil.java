package net.ped.filter;

import javax.persistence.EntityManager;

public class JpaUtil {
	public static final ThreadLocal<EntityManager>
	ENTITY_MANAGERS = new ThreadLocal<EntityManager>();
	
	/** Returns a fresh EntityManager */
	public static EntityManager getEntityManager(){
		return ENTITY_MANAGERS.get();
	}
}

