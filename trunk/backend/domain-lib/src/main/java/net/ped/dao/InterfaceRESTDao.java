package net.ped.dao;

import net.ped.model.Customer;
import net.ped.model.ScannedTicket;
import net.ped.model.User;

public interface InterfaceRESTDao {

	/**
	 * Vérifie le login et mdp de l'utilisateur.
	 * Retourne le User concerné en cas de succès
	 * Retourne un User vide sinon
	 *
	 * @param login
	 * @param password
	 */
	public User connexion(String login, String password);
	
	/**
	 * Vérifie l'id customer - id party - secret code
	 * Retourne le Customer concerné en cas de succès
	 * Retourne un Customer vide sinon
	 *
	 * @param ScannedTicket
	 */
	public Customer validateTicket(ScannedTicket st);
	
}
