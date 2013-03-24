package net.ped.dao;

import java.util.Calendar;
import java.util.List;

import net.ped.model.Artist;
import net.ped.model.Party;
import net.ped.model.User;


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
	 * obtient un artiste via son nom
	 */
	public Artist getArtistByName(String name) throws Exception;
	
	/**
	 * obtient tous les artistes
	 */
	public List<Artist> getAllArtists() throws Exception;
	
	/**
	 * ajoute une party
	 */
	public void addParty(Party p) throws Exception;
	
	/**
	 * met à jour une party
	 */
	public void updateParty(Party p) throws Exception;
	
	/**
	 * obtient toutes les party n'ayant pas été validées 
	 * @return List<Party>
	 */
	public List<Party> getAllPartiesNotValidated() throws Exception;
	
	/**
	 * supprime une party
	 */
	public void deleteParty(int id) throws Exception;
	
	/**
	 * vérifie si la party avec l'identifiant id existe
	 * @return boolean
	 */
	public boolean containsParty(int id) throws Exception;
	
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
	 * obtient les party qui n'ont pas encore commencé et étant validées
	 * @return List<Party>
	 */
	public List<Party> getPartiesNotBegun() throws Exception;
	
	/**
	 * obtient les "length" party qui n'ont pas encore commencé à partir de la position "startPosition" et étant validées
	 * classement de la party la plus proche à la plus lointaine
	 * @return List<Party>
	 */
	public List<Party> getPartiesNotBegunMaxResult(int startPosition, int length) throws Exception;
	
	/**
	 *  Retourne le nombre total de parties qui n'ont pas encore commencé
	 * @return int
	 * @throws Exception
	 */
	public int getNbPartiesNotBegun() throws Exception;
	
	/**
	 * obtient toutes les party suivant différents critères et étant validées
	 * prend également en compte le nombre de résultats retournés
	 * @return List<Party>
	 */
	public List<Party> getPartiesCriteria(int startPosition, int length, String place, double priceBegin, double priceEnd, Calendar date, Calendar time) throws Exception;
	
	/**
	 * obtient le nombre de party suivant différents critères et étant validées
	 * @return int
	 */
	public int getNbPartiesCriteria(String place, Double priceBegin, Double priceEnd, Calendar date, Calendar time);
	
	/**
	 * ajoute un user
	 */
	public void addUser(User u) throws Exception;
	
	/**
	 * obtient un user via son identifiant
	 * @return User
	 */
	public User getUser(int id) throws Exception;
	
	/**
	 *  Permet d'établir la connexion
	 */
	public User login(String login, String password) throws Exception;
	

}
