package net.ped.shared;

import net.ped.dao.BillingDaoImpl;
import net.ped.model.Ticket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TicketInformation {
	protected static final Logger LOG = LoggerFactory.getLogger(TicketInformation.class);

	public int idParty, idClient;
	public String secretCode;

	public Ticket checkAuthorizedAccess() {
		Ticket ticket;
		try {
			ticket = new BillingDaoImpl().getTicket(idClient, idParty);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		LOG.debug("Secret code : " + secretCode + " "
				+ ticket.getSecretCode());
		if (secretCode.compareTo(ticket.getSecretCode()) != 0)
			return null;
		return ticket;
	}
	
	public boolean checkValidTicketInformation() {
		return (idClient >= 0 && idParty >= 0 &&
				secretCode.length() == 16);
	}
}