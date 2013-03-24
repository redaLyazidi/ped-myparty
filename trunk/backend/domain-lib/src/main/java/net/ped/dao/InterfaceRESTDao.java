package net.ped.dao;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import net.ped.model.Customer;
import net.ped.model.Party;
import net.ped.model.ScannedTicket;
import net.ped.model.ScannedTicketManuel;
import net.ped.model.Ticket;
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
	 * Retourne id customer - id party - secret code en cas de succès
	 * Retourne id customer - id party - secret code vide sinon
	 *
	 * @param ScannedTicket
	 */
	public Ticket validateTicket(ScannedTicket st);
	
	/**
	 * Vérifie l'id customer - id party lors de la saisie manuelle
	 * Retourne id customer - id party en cas de succès
	 * Retourne id customer - id party vide sinon
	 *
	 * @param st
	 */
	public Ticket validateTicketManuel(ScannedTicketManuel st);
	
	/**
	 * incrémente dans la party le nombre de tickets scannés
	 */
	public void incrementScan(Party p) throws Exception;
	
}
