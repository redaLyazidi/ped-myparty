package net.ped.servlet;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ped.dao.BillingDaoImpl;
import net.ped.model.Ticket;
import net.ped.shared.Commons;


@SuppressWarnings("serial")
public class TicketCustomerRasterizer extends TicketRasterizer {
	protected TicketInformation getTicketInformations(HttpServletRequest request, HttpServletResponse response) {
		TicketInformation ticketInfos = new TicketInformation();

		try {
			ticketInfos.idParty    = getMandatoryIntParameter(request,    "idparty");
			ticketInfos.idClient   = getMandatoryIntParameter(request,    "idclient");
			ticketInfos.secretCode = getMandatoryStringParameter(request, "secretcode");
		} catch (Exception e) {
			return null;
		}
		return ticketInfos;
	}

	protected boolean checkValidTicketInformation(TicketInformation ticketInfos) {
		return (ticketInfos.idClient >= 0 && ticketInfos.idParty >= 0 && ticketInfos.secretCode
				.length() == 16);
	}

	protected Ticket checkAuthorizedAccess(TicketInformation ticketInfos) {
		Ticket ticket;
		try {
			ticket = new BillingDaoImpl().getTicket(ticketInfos.idParty, ticketInfos.idClient); // TODO : check param order !!
		} catch(Exception e) {
			return null;
		}
		if (ticketInfos.secretCode != ticket.getSecretCode())
			return null;
		return ticket;
	}

	@Override
	protected File getGenericSvgTicket(TicketInformation infos) {
		return Commons.getPartySvgFile(infos.idParty);
	}
}
