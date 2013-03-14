package net.ped.shared;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ped.dao.PartyDaoImpl;
import net.ped.model.Customer;
import net.ped.model.Ticket;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Commons {

	private static final String ticketsextension =".svg";

	private static final Logger LOG = LoggerFactory.getLogger(Commons.class);
	private static HttpServlet myservlet = MyHttpServlet.getInstance();


	public static String getProjectConfigParameter(String name) {
		return myservlet.getServletContext().getInitParameter(name);
	}
	
	
	public static File getPartySvgFile(int idParty) {
		if (idParty < 0) {
			LOG.debug("The id can't be lower than 0, id = " + idParty);
			return null;
		}

		if (new PartyDaoImpl().containsParty(idParty) == false) {
			LOG.debug("This id : " + idParty+ " doesn't match to any party");
			return null;
		}

		String ticketsDir = myservlet.getServletContext().getInitParameter("ticketsDir");
		File ticketOfIdParty;
		try {
			ticketOfIdParty = FileStorage.getPermanentFile(ticketsDir, idParty + ticketsextension);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		LOG.info("thisPartyticketPath: " + ticketOfIdParty);
		/*if (! ticketOfIdParty.exists()) {
			LOG.debug("There no svg file with name: " + idParty);
			return null;
		}*/
		return ticketOfIdParty;
	}

	public static String QRCodeString(Ticket t) {
		Customer c = t.getCustomer();
		LOG.info(getProjectConfigParameter("svgQRcodetag"));
		return c.getId() + '\n' + t.getParty().getId() + '\n' + t.getSecretCode();
	}

	public static void sendFileDownloadResponse(HttpServletRequest request,
			HttpServletResponse response, File diskFile, String downloadName) throws IOException {
		ServletOutputStream op = response.getOutputStream();
		ServletContext context = MyHttpServlet.getInstance().getServletContext();
		String mimetype = context.getMimeType(diskFile.getAbsolutePath());

		response.setCharacterEncoding(System.getProperty("file.encoding"));
		// Set the response and go!
		response.setContentType((mimetype != null) ? mimetype
				: "application/octet-stream");
		response.setContentLength((int) diskFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ downloadName + "\"");

		// Stream to the requester.
		DataInputStream in = new DataInputStream(new FileInputStream(diskFile));
		IOUtils.copy(in, op);
		in.close();
		op.flush();
		op.close();
	}
}
