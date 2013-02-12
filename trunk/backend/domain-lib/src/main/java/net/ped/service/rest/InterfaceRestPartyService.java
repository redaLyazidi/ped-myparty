package net.ped.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import net.ped.model.Party;

@Path("/service")
public interface InterfaceRestPartyService {

	//@BadgerFish
	@GET
	@Path("/party/{id}")
	@Produces("application/json")
	public Party getParty(@PathParam("id")int id);

}
