package net.ped.service.rest;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ped.dao.BillingDaoImpl;
import net.ped.dao.InterfaceBillingDao;
import net.ped.dao.InterfacePartyDao;
import net.ped.dao.InterfaceRESTDao;
import net.ped.dao.PartyDaoImpl;
import net.ped.dao.RESTDaoImpl;
import net.ped.model.Customer;
import net.ped.model.Party;
import net.ped.model.ScannedTicket;
import net.ped.model.ScannedTicketManuel;
import net.ped.model.Ticket;
import net.ped.model.User;

public class RestPartyService implements InterfaceRestPartyService{

	InterfaceRESTDao daoREST = new RESTDaoImpl();
	InterfacePartyDao daoParty = new PartyDaoImpl();
	InterfaceBillingDao daoBilling = new BillingDaoImpl();

	private static final Logger LOG = LoggerFactory.getLogger(RestPartyService.class);

	public Response connexion(User user){
		User user2 = new User();
		try {
			user2 = daoREST.connexion(user.getLogin(), user.getPassword());
			return Response.ok(user2).build();
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

	public Response validateTicket(ScannedTicket st){
		Ticket ticket = new Ticket();
		ScannedTicket scannedTicket = new ScannedTicket();
		try {
			ticket = daoREST.validateTicket(st);
			if(ticket.getId() != 0){
				scannedTicket.setIdCustomer(ticket.getCustomer().getId());
				scannedTicket.setIdParty(ticket.getParty().getId());
				scannedTicket.setSecretCode(ticket.getSecretCode());
				scannedTicket.setValidated(ticket.isValidated());

				//on passe le ticket à validé
				if(!ticket.isValidated()){

					ticket.setValidated(true);
					daoBilling.updateTicket(ticket);

					// mise à jour du nb de billets scannés pour la party
					Party p = daoParty.getParty(ticket.getParty().getId());
					p.setNbPlaceScanned(p.getNbPlaceScanned()+1);
					daoREST.incrementScan(p);
				}
			}
			return Response.ok(scannedTicket).build();
		} catch (Exception e) {
			LOG.error("erreur validation ticket couche service");
			e.printStackTrace();
			return Response.status(400).entity("Connexion failed!").build();
		}
	}

	public Response validateTicketManuel(ScannedTicketManuel st){
		Ticket ticket = new Ticket();
		ScannedTicketManuel scannedTicketManuel = new ScannedTicketManuel();
		try {
			ticket = daoREST.validateTicketManuel(st);
			if(ticket.getId() != 0){
				scannedTicketManuel.setIdCustomer(ticket.getCustomer().getId());
				scannedTicketManuel.setIdParty(ticket.getParty().getId());
				scannedTicketManuel.setValidated(ticket.isValidated());

				//on passe le ticket à validé
				if(!ticket.isValidated()){

					ticket.setValidated(true);
					daoBilling.updateTicket(ticket);

					// mise à jour du nb de billets scannés pour la party
					Party p = daoParty.getParty(ticket.getParty().getId());
					p.setNbPlaceScanned(p.getNbPlaceScanned()+1);
					daoREST.incrementScan(p);
				}
			}

			return Response.ok(scannedTicketManuel).build();
		} catch (Exception e) {
			LOG.error("erreur validation ticket manuel couche service");
			e.printStackTrace();
			return Response.status(400).entity("Connexion failed!").build();
		}
	}

}
