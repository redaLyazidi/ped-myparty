package net.ped.service.rest;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ped.dao.InterfacePartyDao;
import net.ped.dao.InterfaceRESTDao;
import net.ped.dao.PartyDaoImpl;
import net.ped.dao.RESTDaoImpl;
import net.ped.model.Party;
import net.ped.model.User;

public class RestPartyService implements InterfaceRestPartyService{

	InterfaceRESTDao daoREST = new RESTDaoImpl();
	InterfacePartyDao daoParty = new PartyDaoImpl();
	
	private static final Logger LOG = LoggerFactory.getLogger(RestPartyService.class);
	
	public Response connexion(User u){
		User user = new User();
		try {
			user = daoREST.connexion(u.getLogin(), u.getPassword());
			return Response.ok(user).build();
		} catch (Exception e) {
			LOG.error("erreur creation connection couche service");
			e.printStackTrace();
			return Response.status(400).entity("Connexion failed!").build();
		}
	}
	
	public Party getParty(int id) {
		Party party = new Party();
		try {
			party = daoParty.getParty(id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return party;
	}

}
