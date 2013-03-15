package net.ped.servlet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import net.ped.model.Customer;
import net.ped.model.Ticket;
import net.ped.shared.BatikSvgToPdf;
import net.ped.shared.Commons;
import net.ped.shared.InkscapeSvgToPdf;
import net.ped.shared.PedHttpServlet;

import org.apache.commons.io.FileUtils;

@SuppressWarnings("serial")
public abstract class TicketRasterizer extends PedHttpServlet {

	protected static final String tmpDirPath = System
			.getProperty("java.io.tmpdir");

	class TicketInformation {
		public int idParty, idClient;
		public String secretCode;
	}

	public String htmlPersonnalErrorMessage(TicketInformation infos) {
		return "<html><body><h1>Il y a eu une erreur...</h1><br/>"
				+ "Mais tout n'est pas perdu ! Veuillez contacter le service après vente avec les informations suivantes :<br/>"
				+ "identifiant client : " + infos.idClient
				+ "identifiant party : " + infos.idParty + "code secret : "
				+ infos.secretCode + "</body></html>";
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		TicketInformation ticketInfos = getTicketInformations(request, response);
		if (ticketInfos == null || checkValidTicketInformation(ticketInfos) == false) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}

		Ticket ticket = checkAuthorizedAccess(ticketInfos);
		if (ticket == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		File clientSvgTicket = null;
		File clientPdfTicket = null;
		try {
			File genericSvgTicket = getGenericSvgTicket(ticketInfos);
			clientSvgTicket = generateClientSpecificSvgTicket(ticket, genericSvgTicket);
			clientPdfTicket = convertSvgToPdf(clientSvgTicket);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					htmlPersonnalErrorMessage(ticketInfos));
			return;
		} finally {
			cleanupRessources(clientSvgTicket, clientPdfTicket);
		}

		if (clientPdfTicket == null || clientPdfTicket.exists() == false) { 
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					htmlPersonnalErrorMessage(ticketInfos));
			cleanupRessources(clientSvgTicket, clientPdfTicket);
			return;
		}

		// Send valid response
		Commons.sendFileDownloadResponse(request, response, clientPdfTicket, "ticket.pdf");
		cleanupRessources(clientSvgTicket, clientPdfTicket);
	}

	protected abstract TicketInformation getTicketInformations(HttpServletRequest request, HttpServletResponse response);
	protected abstract boolean checkValidTicketInformation(TicketInformation ticketInfos);
	protected abstract Ticket checkAuthorizedAccess(TicketInformation ticketInfos);
	protected abstract File getGenericSvgTicket(TicketInformation infos);

	private File generateClientSpecificSvgTicket(Ticket ticket, File generalTicket) throws Exception {
		// Generate qrcode
		String qrText = "Toto tata\nRololo !"; //Commons.QRCodeString(null, null);
		ByteArrayOutputStream out = QRCode.from(qrText).to(ImageType.PNG).stream();
		String QRDataUri = "data:image/png;base64," +
				DatatypeConverter.printBase64Binary(out.toByteArray());

		// replace qrcode path with the real image
		String SvgQrcodeTag = "xlink:href=\"images/myparty/qrcode.png\"";
		File specificFile = File.createTempFile("specific-ticket", ".svg");
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(generalTicket)));
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(generalTicket)));

		Customer customer = ticket.getCustomer();
		String clientName = customer.getFirstname() + " " + customer.getFirstname();

		String line;
		while((line = reader.readLine()) != null) {
			String line2 = line.replace(SvgQrcodeTag, QRDataUri)
					.replace("$NAME", clientName);
			writer.write(line2);
		}
		reader.close();
		writer.close();
		return specificFile;
	}


	private File convertSvgToPdf(File svg) {
		if (! svg.exists()) {
			LOG.debug("There no such svg file: " + svg.getAbsolutePath());
			return null;
		}
		File pdf = new InkscapeSvgToPdf().convert(svg);
		if (pdf == null) {
			 pdf = new BatikSvgToPdf().convert(svg);
		}
		return pdf;
		/*
		LOG.info("before the exec");
		Runtime runtime = Runtime.getRuntime();
		String cmd = "inkscape -f " + svg.getAbsolutePath() + " -A " + svg.getParent() + '/' + svg.getName() + ".pdf";
		if(! cmd.isEmpty()) {
			LOG.info(cmd);
			try {
				runtime.exec(cmd);
			} catch (SecurityException se) {
				LOG.debug("You're not allowed to use that command on this server\n" + cmd);
				return null;
			} catch (IOException ioe) {
				LOG.debug("An error occured during the execution of the command:\n" + cmd);
			}
		}
		LOG.info("after the exec");

		return null; */
	}

	protected void cleanupRessources(File clientSvgTicket, File clientPdfTicket) {
		FileUtils.deleteQuietly(clientSvgTicket);
		FileUtils.deleteQuietly(clientPdfTicket);
	}

	protected void sendError(HttpServletResponse response, int error) {
		try {
			response.sendError(error);
		} catch (IOException e) {
		}
	}

}
