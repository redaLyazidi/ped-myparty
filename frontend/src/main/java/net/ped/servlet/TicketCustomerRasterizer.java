package net.ped.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import net.ped.dao.BillingDaoImpl;
import net.ped.model.Ticket;
import net.ped.shared.Commons;


@SuppressWarnings("serial")
public class TicketCustomerRasterizer extends TicketRasterizer {
	protected TicketInformation getTicketInformations(HttpServletRequest request, HttpServletResponse response) {
		TicketInformation ticketInfos = new TicketInformation();

		try {
			ticketInfos.idParty    = getMandatoryIntParameter(request,    "idparty");
			ticketInfos.idClient   = getMandatoryIntParameter(request,    "idclient");
			ticketInfos.secretCode = getMandatoryStringParameter(request, "secretcode");
		} catch (Exception e) {
			return null;
		}
		return ticketInfos;
	}

	protected boolean checkValidTicketInformation(TicketInformation ticketInfos) {
		return (ticketInfos.idClient >= 0 && ticketInfos.idParty >= 0 && ticketInfos.secretCode
				.length() == 16);
	}

	protected Ticket checkAuthorizedAccess(TicketInformation ticketInfos) {
		Ticket ticket;
		try {
			ticket = new BillingDaoImpl().getTicket(ticketInfos.idClient, ticketInfos.idParty);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
		LOG.debug("Secret code : " + ticketInfos.secretCode + " " + ticket.getSecretCode());
		if (ticketInfos.secretCode.compareTo(ticket.getSecretCode()) != 0)
			return null;
		return ticket;
	}

	@Override
	protected File getGenericSvgTicket(TicketInformation infos) {
		return Commons.getPartySvgFile(infos.idParty);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String svgstr = request.getParameter("svgstr");
		LOG.info("svgstr: " + svgstr);
		String preview = "preview";
		File tmpDir = new File(tmpDirPath);
		File previewsvg = File.createTempFile(preview, ".svg", tmpDir);
		LOG.info("before the outputstream");
		FileOutputStream previewOutput = new FileOutputStream(previewsvg);
		IOUtils.write(svgstr, previewOutput);
		IOUtils.closeQuietly(previewOutput);

		LOG.info("before the exec");
		Runtime runtime = Runtime.getRuntime();
		LOG.info("svg tempPath: " + previewsvg.getAbsolutePath());
		String cmd = "inkscape -f " + previewsvg.getAbsolutePath() + " -A " + previewsvg.getParent() + '/' + preview + ".pdf";
		LOG.info(cmd);
		runtime.exec(cmd);
		LOG.info("after the exec");

		File previewpdf = new File(previewsvg.getParent(), preview + ".pdf");
		LOG.info(previewpdf.getAbsolutePath());
		if (previewpdf.exists()) {
			response.getWriter().println(previewpdf.getName());
			//Commons.sendFileDownloadResponse(request, response, previewpdf, previewpdf.getName());
		}
	}
}
