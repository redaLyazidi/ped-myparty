package net.ped.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import net.ped.model.Party;
import net.ped.model.ScannedTicket;
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
	 * Retourne le Customer concerné en cas de succès
	 * Retourne null sinon
	 *
	 * @param u
	 */
	@POST
	@Path("/ticket")
	@Produces("application/json")
	public Response validateTicket(ScannedTicket st);

}
