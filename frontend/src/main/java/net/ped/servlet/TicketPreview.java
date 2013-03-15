package net.ped.servlet;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import net.ped.model.Customer;
import net.ped.model.Party;
import net.ped.model.Ticket;
import net.ped.servlet.TicketRasterizer.TicketInformation;
import net.ped.shared.Commons;

@SuppressWarnings("serial")
public class TicketPreview extends TicketRasterizer {
	private File svgTicket = null;
	
	@Override
	protected TicketInformation getTicketInformations(HttpServletRequest request, HttpServletResponse response) {
		TicketInformation ticketInfos = new TicketInformation();
		ticketInfos.idParty = 0;
		LOG.debug("Get ticket information");
		
		// pas bon : il faut le svg et non l'id. => requête POST nécessaire
		// il faut créer un fichier temporaire lors de la requête post.
		// il faut remplir le champ svgTicket lors de la requête get.
		try {
			ticketInfos.idParty = getMandatoryIntParameter(request, "idparty");
		} catch (Exception e) {
			LOG.debug("No id given");
			return null;
		}
		return ticketInfos;
	}

	@Override
	protected boolean checkValidTicketInformation(TicketInformation ticketInfos) {
		return true;
	}

	@Override
	protected Ticket checkAuthorizedAccess(TicketInformation ticketInfos) {
		// long name
		Customer c = new Customer("Peyronnelle Clémentine", "Wong Wun Gatchunk", "clems@hotmail.fr");
		Ticket ticket = new Ticket(new Party(), c, "0123456789ABCDEF");
		return ticket;
	}
	
	@Override
	protected File getGenericSvgTicket(TicketInformation infos) {
		return svgTicket;
	}
	
	protected void cleanupRessources(File clientSvgTicket, File clientPdfTicket) {
		super.cleanupRessources(clientSvgTicket, clientPdfTicket);
		FileUtils.deleteQuietly(svgTicket);
	}
}
