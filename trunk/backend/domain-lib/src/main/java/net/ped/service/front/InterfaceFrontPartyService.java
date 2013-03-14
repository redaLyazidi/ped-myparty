package net.ped.service.front;

import java.util.Calendar;
import java.util.List;

import net.ped.model.Artist;
import net.ped.model.Party;
import net.ped.model.User;

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
	 * valide une party -> l'étape de création de billet a été faite
	 * ajoute le chemin du billet à la party
	 */
	public void ValidateParty(int id);
	
	/**
	 * supprime une party
	 */
	public void deleteParty(int id);
	
	/**
	 * vérifie si la party avec l'identifiant id existe
	 * @return boolean
	 */
	public boolean containsParty(int id);
	
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
	 * obtient toutes les party n'ayant pas été validées 
	 * @return List<Party>
	 */
	public List<Party> getAllPartiesNotValidated();
	
	/**
	 * obtient les party qui n'ont pas encore commencé et étant validées
	 * @return List<Party>
	 */
	public List<Party> getPartiesNotBegun();
	
	/**
	 * obtient les "length" party qui n'ont pas encore commencé à partir de la position "startPosition" et étant validées
	 * classement de la party la plus proche à la plus lointaine
	 * @return List<Party>
	 */
	public List<Party> getPartiesNotBegunMaxResult(int startPosition, int length);
	
	/**
	 *  Retourne le nombre total de parties qui n'ont pas encore commencé
	 * @return int
	 */
	public int getNbPartiesNotBegun();
	
	/**
	 * obtient toutes les party suivant différents critères et étant validées
	 * @return List<Party>
	 */
	public List<Party> getPartiesCriteria(int startPosition, int length, String place, Double priceBegin, Double priceEnd, Calendar date, Calendar time);
	
	/**
	 * ajoute un user
	 */
	public void addUser(User u);
	
	/**
	 * obtient un user via son identifiant
	 * @return User
	 */
	public User getUser(int id);
	
	/**
	 * Permet d'établir la connexion
	 */
	public User login(String login, String password);
}
