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
import net.ped.shared.PedHttpServlet;

import org.apache.commons.io.IOUtils;

/**
 * Servlet implementation class SaveAs
 */
public class StoreSvgTickets extends PedHttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final Logger LOG = LoggerFactory.getLogger(StoreSvgTickets.class);
//	private static final String tmpDirPath = System.getProperty("java.io.tmpdir");
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public StoreSvgTickets() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
		String webappPath = getServletContext().getRealPath("/");
		String absoluteTicketDirPath = new StringBuilder(webappPath)
		.append(getServletContext().getInitParameter("ticketsDir")).toString();
		File ticket = new File(absoluteTicketDirPath, idParty + ".svg");
		if (! ticket.exists()) {
			LOG.debug("This id : " + idParty+ " doesn't match to any ticket");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.info("jQuery.post received");
		request.getParameter("");
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
		
		if ( ! new PartyDaoImpl().containsParty(idParty)) {
			LOG.debug("This id : " + idParty+ " doesn't match to any party");
			return;
		}

		LOG.info(svgstr);
		String webappPath = getServletContext().getRealPath("/");
		String absoluteTicketDirPath = new StringBuilder(webappPath)
		.append(getServletContext().getInitParameter("ticketsDir")).toString();
		File ticket = new File(absoluteTicketDirPath, idParty + ".svg");
		FileOutputStream ticketoutput = new FileOutputStream( ticket );
		IOUtils.write(svgstr, ticketoutput);
		IOUtils.closeQuietly(ticketoutput);
		response.getWriter().println(true);
		
	}

	/**
	 *  Sends a file to the ServletResponse output stream.  Typically
	 *  you want the browser to receive a different name than the
	 *  name the file has been saved in your local database, since
	 *  your local names need to be unique.
	 *
	 *  @param request The request
	 *  @param response The response
	 *  @param filename The name of the file you want to download.
	 *  @param original_filename The name the browser should receive.
	 */

}