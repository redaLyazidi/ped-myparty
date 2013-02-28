package net.ped.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.ped.model.Artist;
import net.ped.model.Party;


public interface InterfacePartyDao {
	
	/**
	 * ajoute un artiste
	 */
	public void addArtist(Artist a) throws Exception;
	
	/**
	 * supprime un artiste
	 */
	public void deleteArtist(int id) throws Exception;
	
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
	 * obtient une party via son identifiant
	 * @return Party
	 */
	public Party getParty(int id) throws Exception;
	
	/**
	 * obtient toutes les party
	 * @return List<Party>
	 */
	public List<Party> getAllParties() throws Exception;
	
	/**
	 * obtient les party qui n'ont pas encore commencé
	 * @return List<Party>
	 */
	public List<Party> getPartiesNotBegun() throws Exception;
	
	/**
	 * obtient les "length" party qui n'ont pas encore commencé à partir de la position "startPosition"
	 * classement de la party la plus proche à la plus lointaine
	 * @return List<Party>
	 */
	public List<Party> getPartiesNotBegunMaxResult(int startPosition, int length) throws Exception;
	
	/**
	 * obtient toutes les party suivant différents critères
	 * @return List<Party>
	 */
	public List<Party> getPartiesCriteria(double priceBegin, double priceEnd, Calendar date, Calendar time) throws Exception;

}
