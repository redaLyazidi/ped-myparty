package net.ped.shared;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
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
	static StaticFileManager svgTicketFileManager = null;
	
	public static String getProjectConfigParameter(String name) {
		return myservlet.getServletContext().getInitParameter(name);
	}
	
	private static boolean checkParty(int idParty) {
		if (svgTicketFileManager == null) {
			svgTicketFileManager = new StaticFileManager("ticketsdir", "ticket", "svg");
		}
		
		if (idParty < 0) {
			LOG.debug("The id can't be lower than 0, id = " + idParty);
			return false;
		}
		if (new PartyDaoImpl().containsParty(idParty) == false) {
			LOG.debug("This id : " + idParty+ " doesn't match to any party");
			return false;
		}
		return true;
	}

	public static File getPartySvgFile(int idParty) {
		if (checkParty(idParty) == false)
			return null;
		try {
			return svgTicketFileManager.get(idParty);
		} catch (IOException e) {
			return null;
		}
	}
	
	public static URL getPartySvgURL(HttpServletRequest req, int idParty) {
		if (checkParty(idParty) == false)
			return null;

		try {
			return svgTicketFileManager.getURL(req, idParty);
		} catch (IOException e) {
			return null;
		}
	}

	public static String QRCodeString(Ticket t) {
		Customer c = t.getCustomer();
		String sep = " ";
		String content = c.getId() + sep + t.getParty().getId() + sep + t.getSecretCode();
		LOG.info("QRcode content : " + content);
		return content;
	}

	public static void sendFileDownloadResponse(HttpServletRequest request,
			HttpServletResponse response, File diskFile, String downloadName) throws IOException {
		LOG.debug("sendFileDownloadResponse : " + diskFile);
		
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

	/** take a request with a svg file in the svgstr parameter, and store the svg to the given file */ 
	public static void writeSvgInFileFromSvgSpecificRequest(InputStream svgstr, File svgFile) throws IOException {
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
	
	/** take a request with a svg file in the svgstr, store the svg localy and returns its id for further access */ 
	public static void answerSvgLocationFromSpecificRequest(HttpServletRequest request,
			HttpServletResponse response,TempFileManager tempFileManager) throws ServletException, IOException {
		InputStream svgstr = request.getInputStream();
		// create a temporary file in that directory
		File tempFile = tempFileManager.create();
		LOG.info(tempFile.getPath());

		Commons.writeSvgInFileFromSvgSpecificRequest(svgstr, tempFile);
		response.setContentType("text/plain");
		response.getWriter().println(tempFile.getName());
	}
}

