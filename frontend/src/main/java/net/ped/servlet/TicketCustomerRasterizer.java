package net.ped.servlet;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ped.shared.Commons;
import net.ped.shared.TicketInformation;


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

	@Override
	protected File getGenericSvgTicket(HttpServletRequest request, TicketInformation infos) {
		return Commons.getPartySvgFile(infos.idParty);
	}
}