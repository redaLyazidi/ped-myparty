package net.ped.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ped.dao.PartyDaoImpl;
import net.ped.shared.Commons;
import net.ped.shared.FileStorage;
import net.ped.shared.PedHttpServlet;

import org.apache.commons.io.IOUtils;


@SuppressWarnings("serial")
public class StoreSvgTickets extends PedHttpServlet {

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

		File ticket = Commons.getPartySvgFile(idParty);
		if (! FileStorage.exists(ticket)) {
			LOG.debug("This id : " + idParty + " doesn't match to any ticket");
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		InputStream ticketinput = new FileInputStream(ticket);
		OutputStream output = response.getOutputStream();
		IOUtils.copy(ticketinput, output);
		IOUtils.closeQuietly(ticketinput);
		IOUtils.closeQuietly(output);
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
		LOG.debug(svgstr);

		File ticket = Commons.getPartySvgFile(idParty);
		FileOutputStream ticketoutput = new FileOutputStream( ticket );
		IOUtils.write(svgstr, ticketoutput);
		IOUtils.closeQuietly(ticketoutput);
		//response.getWriter().println(true);
	}
}