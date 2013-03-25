package net.ped.service.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import net.ped.model.Party;
import net.ped.model.PartyDescriptionAndId;
import net.ped.model.ScannedTicket;
import net.ped.model.ScannedTicketManuel;
import net.ped.model.User;

@Path("/service")
public interface InterfaceRestPartyService {

	/**
	 * Vérifie le login et mdp de l'utilisateur.
	 * Retourne le User concerné en cas de succès
	 * Retourne un User vide sinon
	 *
	 * @param user
	 */
	@POST
	@Path("/connexion")
	@Produces("application/json")
	public Response connexion(User user);
	
	
	/**
	 * Retourne l'ensemble des informations d'une party
	 * 
	 * @param id
	 * @return Party
	 */
	@GET
	@Path("/party/{id}")
	@Produces("application/json")
	public Party getParty(@PathParam("id")int id);
	
	/**
	 * Vérifie l'id customer - id party - secret code
	 * Retourne id customer - id party - secret code en cas de succès
	 * Retourne id customer - id party - secret code vide sinon
	 *
	 * @param u
	 */
	@POST
	@Path("/ticket")
	@Produces("application/json")
	public Response validateTicket(ScannedTicket st);
	
	/**
	 * Vérifie l'id customer - id party lors de la saisie manuelle
	 * Retourne id customer - id party en cas de succès
	 * Retourne id customer - id party vide sinon
	 *
	 * @param u
	 */
	@POST
	@Path("/ticketManuel")
	@Produces("application/json")
	public Response validateTicketManuel(ScannedTicketManuel st);

	/**
	 * Retourne les descriptions de toutes les parties avec leur id
	 */
	@GET
	@Path("/listParty")
	@Produces("application/json")
	public List<PartyDescriptionAndId> listPartiesDescriptionAndId();
}
