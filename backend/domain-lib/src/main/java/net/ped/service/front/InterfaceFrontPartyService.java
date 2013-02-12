package net.ped.service.front;

import java.util.List;

import net.ped.model.Party;

public interface InterfaceFrontPartyService {
	
	/**
	 * ajoute une party
	 */
	public void addParty(Party p);
	
	/**
	 * met à jour une party
	 */
	public void updateParty(Party p);
	
	/**
	 * supprime une party
	 */
	public void deleteParty(int id);
	
	/**
	 * obtient toutes les party
	 * @return List<Party>
	 */
	public List<Party> getAllParties();
	
	/**
	 * obtient une party via son identifiant
	 * @return Party
	 */
	public Party getParty(int id);
	
	/**
	 * obtient toutes les party suivant différents critères
	 * @return List<Party>
	 */
	public List<Party> getPartiesCriteria();

}
