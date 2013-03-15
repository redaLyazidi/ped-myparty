package net.ped.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ped.model.Customer;
import net.ped.model.Party;
import net.ped.model.Ticket;
import net.ped.shared.Commons;
import net.ped.shared.TempFileManager;

import org.apache.commons.io.FileUtils;

@SuppressWarnings("serial")
public class TicketPreview extends TicketRasterizer {
	private File svgTicket;
	static TempFileManager tempFileManager = null;

	public void init() {
		if (tempFileManager == null) {
			tempFileManager = new TempFileManager("preview", "ticket", "svg",
					true);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Commons.writeSvgInTmp(request, response, tempFileManager);
	}

	@Override
	protected TicketInformation getTicketInformations(
			HttpServletRequest request, HttpServletResponse response) {
		TicketInformation ticketInfos = new TicketInformation();
		LOG.debug("dummy get ticket information");
		return ticketInfos;
	}

	@Override
	protected boolean checkValidTicketInformation(TicketInformation ticketInfos) {
		return true;
	}

	@Override
	protected Ticket checkAuthorizedAccess(TicketInformation ticketInfos) {
		// long name
		Customer c = new Customer("Peyronnelle Clémentine",
				"Wong Wun Gatchunk", "clems@hotmail.fr");
		Ticket ticket = new Ticket(new Party(), c, "0123456789ABCDEF");
		return ticket;
	}

	@Override
	protected File getGenericSvgTicket(HttpServletRequest request,
			TicketInformation infos) {
		String filename = request.getParameter("url");
		if (filename == null) {
			LOG.info("Missing parameter url in request");
			return null;
		}
		LOG.info("filename " + filename);
		svgTicket = tempFileManager.get(filename);
		return svgTicket;
	}

	protected void cleanupRessources(File clientSvgTicket, File clientPdfTicket) {
		super.cleanupRessources(clientSvgTicket, clientPdfTicket);
		FileUtils.deleteQuietly(svgTicket);
	}
}
