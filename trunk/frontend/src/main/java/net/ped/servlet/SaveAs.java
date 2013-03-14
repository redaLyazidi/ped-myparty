package net.ped.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Pattern;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ped.shared.BackgroundRessourceCleaner;
import net.ped.shared.Commons;
import net.ped.shared.FileStorage;
import net.ped.shared.PedHttpServlet;

import org.apache.commons.io.IOUtils;

@SuppressWarnings("serial")
public class SaveAs extends PedHttpServlet {	
    public void init() {
    	try {
    		BackgroundRessourceCleaner.getInstance().watchFiles(
    				FileStorage.getTemporaryDirPath(), Pattern.compile("ticket(.*)\\.svg"));
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    }
	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("url");
		if (filename == null)
			throw new ServletException("Missing config parameter : url");
		File diskFile = FileStorage.getTempFile("saveas", filename);
		if (diskFile.exists())
			Commons.sendFileDownloadResponse(request, response, diskFile, "ticket.svg");
		else
			response.sendError(404);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		InputStream svgstr = request.getInputStream();
		// create a temporary file in that directory
		File tempFile = FileStorage.createTempFile("saveas", "ticket", ".svg");
		LOG.info(tempFile.getPath());
		LOG.info(getServletContext().getMimeType(tempFile.getName()));
		
		//	Commons.getPartySvgFile(8);
		// write to file
		FileWriter fw = new FileWriter(tempFile);
		try {
			IOUtils.skip(svgstr, "svgstr=".length());
			IOUtils.copy(svgstr, fw);
			fw.flush();
		} finally {
			response.setContentType("text/plain");
			response.getWriter().println(tempFile.getName());
			IOUtils.closeQuietly(svgstr);
			IOUtils.closeQuietly(fw);
		}
	}
}