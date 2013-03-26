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

import net.ped.bean.PartyBean;
import net.ped.dao.PartyDaoImpl;
import net.ped.shared.Commons;
import net.ped.shared.FileStorage;
import net.ped.shared.PedHttpServlet;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("serial")
public class StoreSvgTickets extends PedHttpServlet {

	private static final Logger LOG = LoggerFactory.getLogger(StoreSvgTickets.class);
	
	/** Called when the admin wants to edit the ticket (and load it) after having validated it */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idPartyString = request.getParameter("idParty");
		int idParty = -1;
		try {
			idParty= getMandatoryIntParameter(request, "idparty");
		} catch (Exception e) {
			LOG.info("Bad parameter" + idParty);
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

	/** Validate the ticket */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("post received");
		int idParty = -1;
		String svgstr = null;
		try {
			idParty= getMandatoryIntParameter(request, "idparty");
			svgstr = getMandatoryStringParameter(request, "svgstr"); // should stream here
		} catch (Exception e) {
			LOG.info("Bad parameters" + idParty + svgstr);
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}
		
		if (! new PartyDaoImpl().containsParty(idParty)) {
			LOG.debug("This id : " + idParty+ " doesn't match to any party");
			return;
		}
		LOG.debug(svgstr);

		File ticket = Commons.getPartySvgFile(idParty);
		LOG.debug("TICKET : " + ticket);
		FileOutputStream ticketoutput = new FileOutputStream(ticket);
		IOUtils.write(svgstr, ticketoutput);
		IOUtils.closeQuietly(ticketoutput);
		//response.getWriter().println(true);
		LOG.debug("Post ok");
		
		PartyBean bean = (PartyBean) request.getSession().getAttribute("partyBean");
		LOG.debug("passage du booleen hasTicket dans le bean partyBean à true");
		bean.setHasTicket(true);
	}
}