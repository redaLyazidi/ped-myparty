package net.ped.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;

import net.ped.dao.PartyDaoImpl;
import net.ped.shared.PedHttpServlet;

import org.apache.commons.io.IOUtils;

/**
 * Servlet implementation class SaveAs
 */
public class StoreSvgTickets extends PedHttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final Logger LOG = LoggerFactory.getLogger(StoreSvgTickets.class);
	private static final String tmpDirPath = System.getProperty("java.io.tmpdir");
	private static final String ticketDirPath = "/resources/tickets";
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
		LOG.debug(tmpDirPath);
		//boolean b = FrontPartyService.getInstance().containsParty(id); TO test if the id matches a Party
		// Save this SVG into a file (required by SVG -> PDF transformation process)
		File svgFile = File.createTempFile("graphic-", ".svg");
		try {
			Transformer transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//		DOMSource source2 = new DOMSource(svgXmlDoc);
		//		FileOutputStream fOut = new FileOutputStream(svgFile);
		//		try { transformer.transform(source2, new StreamResult(fOut)); }
		//		finally { fOut.close(); }
		//
		//		// Convert the SVG into PDF
		//		File outputFile = File.createTempFile("result-", ".pdf");
		//		SVGConverter converter = new SVGConverter();
		//		converter.setDestinationType(DestinationType.PDF);
		//		converter.setSources(new String[] { svgFile.toString() });
		//		converter.setDst(outputFile);
		//		converter.execute();
		//		doDownload(request, response,tmpDirPath, request.getParameter("url"), "ticket.svg");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
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
		
		if ( ! new PartyDaoImpl().containsParty(idParty)) {
			LOG.debug("This id : " + idParty+ " doesn't match to any party");
			return;
		}

		LOG.info(svgstr);
		String webappPath = getServletContext().getRealPath("/");
		String absoluteTicketDirPath = new StringBuilder(webappPath).append(ticketDirPath).toString();
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