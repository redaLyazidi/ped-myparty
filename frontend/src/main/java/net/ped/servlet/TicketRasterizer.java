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
import net.ped.dao.BillingDaoImpl;
import net.ped.model.Customer;
import net.ped.model.Ticket;
import net.ped.shared.Commons;
import net.ped.shared.PedHttpServlet;

@SuppressWarnings("serial")
public class TicketRasterizer extends PedHttpServlet {

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

		// Get parameters
		// parameters : Id party, Id client, Secret code
		TicketInformation ticketInfos = new TicketInformation();

		try {
			ticketInfos.idParty = Commons.getMandatoryIntParameter(request,
					"idparty");
			ticketInfos.idClient = Commons.getMandatoryIntParameter(request,
					"idclient");
			ticketInfos.secretCode = Commons.getMandatoryStringParameter(
					request, "secretcode");
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}

		// Check valid parameters
		if (checkValidTicketInformation(ticketInfos) == false) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
		}

		Ticket ticket = checkAuthorizedAccess(ticketInfos);
		if (ticket == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
		}

		File clientPdfTicket;
		try {
			File genericSvgTicket = Commons.getPartySvgFile(ticketInfos.idParty);
			File clientSvgTicket = generateClientSpecificSvgTicket(ticket, genericSvgTicket);
			clientPdfTicket = convertSvgToPdf(clientSvgTicket);
		} catch (Exception e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					htmlPersonnalErrorMessage(ticketInfos));
			return;
		}
		
		if (clientPdfTicket == null || clientPdfTicket.exists() == false) { 
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
					htmlPersonnalErrorMessage(ticketInfos));
			return;
		}
		
		// Send valid response
		Commons.sendFileDownloadResponse(request, response, clientPdfTicket, "ticket.pdf");
	}

	private boolean checkValidTicketInformation(TicketInformation ticketInfos) {
		return (ticketInfos.idClient >= 0 && ticketInfos.idParty >= 0 && ticketInfos.secretCode
				.length() == 16);
	}

	private Ticket checkAuthorizedAccess(TicketInformation ticketInfos) {
		Ticket ticket;
		try {
			ticket = new BillingDaoImpl().getTicket(ticketInfos.idParty, ticketInfos.idClient); // TODO : check param order !!
		} catch(Exception e) {
			return null;
		}
		if (ticketInfos.secretCode != ticket.getSecretCode())
			return null;
		return ticket;
	}

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
		return null;
	}
}
