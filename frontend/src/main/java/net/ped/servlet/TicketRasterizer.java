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
import net.ped.shared.FileStorage;
import net.ped.shared.InkscapeSvgToPdf;
import net.ped.shared.PedHttpServlet;
import net.ped.shared.TempFileManager;
import net.ped.shared.TicketInformation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public abstract class TicketRasterizer extends PedHttpServlet {

	static TempFileManager pdfTempFileManager = null;
	static TempFileManager clientSvgTempFileManager = null;

	private static final Logger LOG = LoggerFactory.getLogger(TicketRasterizer.class);
	
	public void init() {
		if (pdfTempFileManager == null) {
			pdfTempFileManager = new TempFileManager("rasterized-pdf", "ticket", "pdf", true);
		}
		if (clientSvgTempFileManager == null) {
			LOG.debug("It was null");
			clientSvgTempFileManager = new TempFileManager("client-specific-ticket", "ticket", "svg", true);
		}
		super.init();
	}

	public String htmlPersonnalErrorMessage(TicketInformation infos) {
		return "<html><body><h2>Il y a eu une erreur...</h2><br/>"
				+ "<h3>Mais tout n'est pas perdu !</h3><br/>"
				+ "Veuillez contacter le service après vente avec les informations suivantes :<br/>"
				+ "identifiant client : " + infos.idClient   + "<br/>"
				+ "identifiant party : " +  infos.idParty    + "<br/>"
				+ "code secret : " +        infos.secretCode + "</body></html>";
	}
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		TicketInformation ticketInfos = getTicketInformations(request, response);
		if (ticketInfos == null || ticketInfos.checkValidTicketInformation() == false) {
			response.sendError(HttpServletResponse.SC_NOT_ACCEPTABLE);
			return;
		}

		LOG.debug("getTicketInformations passed");
		Ticket ticket = ticketInfos.checkAuthorizedAccess();
		if (ticket == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		LOG.debug("checkAuthorizedAccess passed");
		
		File clientSvgTicket = null;
		File clientPdfTicket = null;
		try {
			File genericSvgTicket = getGenericSvgTicket(request, ticketInfos);
			LOG.debug("genericSvgTicket : " + genericSvgTicket);

			if (FileStorage.exists(genericSvgTicket) == false) {
				LOG.error("Asking for a ticket present in database but not in the local filesystem : " + ticketInfos.idParty);
				throw new Exception();
			}
			LOG.debug("getGenericSvgTicket passed");
			clientSvgTicket = generateClientSpecificSvgTicket(ticket, genericSvgTicket);
			LOG.debug("generateClientSpecificSvgTicket passed");
			clientPdfTicket = convertSvgToPdf(clientSvgTicket);
			LOG.debug("convertSvgToPdf passed");

		} catch (Exception e) {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(htmlPersonnalErrorMessage(ticketInfos));
			return;
		} finally {
			cleanupRessources(clientSvgTicket, clientPdfTicket);
		}

		if (clientPdfTicket == null || clientPdfTicket.exists() == false) { 
			LOG.error("No pdf generated...");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write(htmlPersonnalErrorMessage(ticketInfos));
			cleanupRessources(clientSvgTicket, clientPdfTicket);
			return;
		}

		// Send valid response
		try {
			Commons.sendFileDownloadResponse(request, response, clientPdfTicket, "ticket.pdf");
		} catch(Exception e) {
			LOG.warn("Cannot sent file download response...");
		}
		cleanupRessources(clientSvgTicket, clientPdfTicket);
		LOG.info("Ticket pdf download : success !");
	}

	protected abstract TicketInformation getTicketInformations(HttpServletRequest request, HttpServletResponse response);
	protected abstract File getGenericSvgTicket(HttpServletRequest request, TicketInformation infos);

	private File generateClientSpecificSvgTicket(Ticket ticket, File generalTicket) throws Exception {
		// Generate qrcode
		String qrText = "Toto tata\nRololo !";
		ByteArrayOutputStream out = QRCode.from(qrText).to(ImageType.PNG).stream();
		String qrData = DatatypeConverter.printBase64Binary(out.toByteArray());
		String qrDataUri = "xlink:href=\"data:image/png;base64," + qrData + "\"";

		// replace qrcode path with the real image
		String SvgQrcodeTag = "xlink:href=\"images/myparty/qrcode.png\""; // oops ! could be on two lines !
		File specificFile = clientSvgTempFileManager.create();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(specificFile)));
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(generalTicket)));

		Customer customer = ticket.getCustomer();
		String clientName = customer.getFirstname() + " " + customer.getFirstname();

		//List<String> txt = IOUtils.readLines(new FileReader(generalTicket));
		//LOG.debug("Size : " + txt.size());
		
		String line;
		while((line = reader.readLine()) != null) {
			String line2 = line.replace(SvgQrcodeTag, qrDataUri)
					.replace("$NAME$", clientName); // TODO : add id
			writer.write(line2);
		}
		writer.flush();
		reader.close();
		writer.close();
		return specificFile;
	}


	private File convertSvgToPdf(File svg) {
		if (! FileStorage.exists(svg)) {
			LOG.error("There no such svg file: " + svg);
			return null;
		}
		File pdfOutput = pdfTempFileManager.create();
		if (new InkscapeSvgToPdf().convert(svg, pdfOutput) == true)
			return pdfOutput;
		if (new BatikSvgToPdf().convert(svg, pdfOutput))
			return pdfOutput;
		pdfOutput.delete();
		return null;
	}

	protected void cleanupRessources(File clientSvgTicket, File clientPdfTicket) {
		//FileUtils.deleteQuietly(clientSvgTicket);
		//FileUtils.deleteQuietly(clientPdfTicket);
	}

	protected void sendError(HttpServletResponse response, int error) {
		try {
			response.sendError(error);
		} catch (IOException e) {
		}
	}

}