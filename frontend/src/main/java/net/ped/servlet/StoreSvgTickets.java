package net.ped.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ped.dao.PartyDaoImpl;
import net.ped.shared.FileStorage;
import net.ped.shared.PedHttpServlet;

import org.apache.commons.io.IOUtils;


@SuppressWarnings("serial")
public class StoreSvgTickets extends PedHttpServlet {
	public StoreSvgTickets() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idPartyString = request.getParameter("idParty");
		int idParty;
		try {
			idParty = Integer.parseInt(idPartyString);
		} catch (NumberFormatException nfe) {
			LOG.debug("The idParty given isn't a number : " + idPartyString);
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}
		
		if ( ! new PartyDaoImpl().containsParty(idParty)) {
			LOG.debug("This id : " + idParty+ " doesn't match to any party");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		File ticket = FileStorage.getPermanentFile("ticketsdir", idParty + ".svg");
		if (! ticket.exists()) {
			LOG.debug("This id : " + idParty + " doesn't match to any ticket");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		InputStream ticketinput = new FileInputStream( ticket );
		List<String> contentTicket = IOUtils.readLines(ticketinput);
		StringBuilder svgstrbuilder = new StringBuilder();
		for (String is : contentTicket)
			svgstrbuilder.append(is);
		String svgstr = svgstrbuilder.toString();
		response.getWriter().println(svgstr);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("jQuery.post received");
		String svgstr = request.getParameter("svgstr");
		String idPartyString = request.getParameter("idParty");
		int idParty;
		try {
			idParty = Integer.parseInt(idPartyString);
		} catch (NumberFormatException nfe) {
			LOG.debug("The idParty given isn't a number : " + idPartyString);
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}
		
		if (! new PartyDaoImpl().containsParty(idParty)) {
			LOG.debug("This id : " + idParty+ " doesn't match to any party");
			return;
		}
		LOG.info(svgstr);
		
		File ticket = FileStorage.getPermanentFile("ticketsdir", idParty + ".svg");
		FileOutputStream ticketoutput = new FileOutputStream( ticket );
		IOUtils.write(svgstr, ticketoutput);
		IOUtils.closeQuietly(ticketoutput);
		response.getWriter().println(true);
	}
}