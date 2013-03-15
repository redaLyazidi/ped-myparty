package net.ped.shared;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

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
	private static final Logger LOG = LoggerFactory.getLogger(Commons.class);
	private static HttpServlet myservlet = MyHttpServlet.getInstance();
	static PermanentFileManager permanentSvgTicketFileManager = null;

	
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

		if (permanentSvgTicketFileManager == null) {
			permanentSvgTicketFileManager = new PermanentFileManager("ticketsdir", "ticket", "svg", true);
		}
		try {
			return permanentSvgTicketFileManager.get(idParty);
		} catch (IOException e) {
			return null;
		}
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
		response.setContentType((mimetype != null) ? mimetype
				: "application/octet-stream");
		response.setContentLength((int) diskFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ downloadName + "\"");

		DataInputStream in = new DataInputStream(new FileInputStream(diskFile));
		IOUtils.copy(in, op);
		in.close();
		op.flush();
		op.close();
	}

	public static void writeSvgInServer(InputStream svgstr, File svgFile) throws IOException {
		FileWriter fw = new FileWriter(svgFile);
		try {
			IOUtils.skip(svgstr, "svgstr=".length());
			IOUtils.copy(svgstr, fw);
			fw.flush();
		} finally {
			IOUtils.closeQuietly(svgstr);
			IOUtils.closeQuietly(fw);
		}
	}
}

