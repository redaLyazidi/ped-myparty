package net.ped.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ped.shared.Commons;
import net.ped.shared.DummyTicketInformation;
import net.ped.shared.TempFileManager;
import net.ped.shared.TicketInformation;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class TicketPreview extends TicketRasterizer {
	
	private static final Logger LOG = LoggerFactory.getLogger(TicketPreview.class);
	
	private File svgTicket;
	static TempFileManager tempFileManager = null;

	public void init() {
		super.init();
		if (tempFileManager == null) {
			tempFileManager = new TempFileManager("preview", "ticket", "svg", true);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Commons.answerSvgLocationFromSpecificRequest(request, response, tempFileManager);
	}

	@Override
	protected TicketInformation getTicketInformations(
			HttpServletRequest request, HttpServletResponse response) {
		TicketInformation ticketInfos = new DummyTicketInformation();
		LOG.debug("dummy get ticket information");
		return ticketInfos;
	}

	@Override
	protected File getGenericSvgTicket(HttpServletRequest request, TicketInformation infos) {
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
