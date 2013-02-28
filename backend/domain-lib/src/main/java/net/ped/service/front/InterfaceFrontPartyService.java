package net.ped.service.front;

import java.util.Calendar;
import java.util.List;

import net.ped.model.Artist;
import net.ped.model.Party;

public interface InterfaceFrontPartyService {
	
	/**
	 * ajoute un artiste
	 */
	public void addArtist(Artist a);
	
	/**
	 * supprime un artiste
	 */
	public void deleteArtist(int id);
	
	/**
	 * obtient un artiste via son nom
	 */
	public Artist getArtistByName(String name);
	
	/**
	 * obtient tous les artistes
	 */
	public List<Artist> getAllArtists();
	
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
	 * obtient une party via son identifiant
	 * @return Party
	 */
	public Party getParty(int id);
	
	/**
	 * obtient toutes les party
	 * @return List<Party>
	 */
	public List<Party> getAllParties();
	
	/**
	 * obtient les party qui n'ont pas encore commencé
	 * @return List<Party>
	 */
	public List<Party> getPartiesNotBegun();
	
	/**
	 * obtient les "length" party qui n'ont pas encore commencé à partir de la position "startPosition"
	 * classement de la party la plus proche à la plus lointaine
	 * @return List<Party>
	 */
	public List<Party> getPartiesNotBegunMaxResult(int startPosition, int length);
	
	/**
	 * obtient toutes les party suivant différents critères
	 * @return List<Party>
	 */
	public List<Party> getPartiesCriteria(Double priceBegin, Double priceEnd, Calendar date, Calendar time);

}
