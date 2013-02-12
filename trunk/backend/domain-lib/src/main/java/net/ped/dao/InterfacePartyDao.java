package net.ped.dao;

import java.util.List;

import net.ped.model.Party;


public interface InterfacePartyDao {
	
	/**
	 * ajoute une party
	 */
	public void addParty(Party p) throws Exception;
	
	/**
	 * met à jour une party
	 */
	public void updateParty(Party p) throws Exception;
	
	/**
	 * supprime une party
	 */
	public void deleteParty(int id) throws Exception;
	
	/**
	 * obtient toutes les party
	 * @return List<Party>
	 */
	public List<Party> getAllParties() throws Exception;
	
	/**
	 * obtient une party via son identifiant
	 * @return Party
	 */
	public Party getParty(int id) throws Exception;
	
	/**
	 * obtient toutes les party suivant différents critères
	 * @return List<Party>
	 */
	public List<Party> getPartiesCriteria() throws Exception;

}
