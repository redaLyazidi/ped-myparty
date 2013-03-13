package net.ped.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import net.ped.shared.*;
import org.apache.commons.io.IOUtils;

@SuppressWarnings("serial")
public class SaveAs extends PedHttpServlet {
	private static final String tmpDirPath = System
			.getProperty("java.io.tmpdir");

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.debug(tmpDirPath);
		String filename = request.getParameter("url");
		File diskFile = new File(tmpDirPath, filename);
		if (diskFile.exists())
			Commons.sendFileDownloadResponse(request, response, diskFile, "ticket.svg");
		else
			response.sendError(404);
	}


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		InputStream svgstr = request.getInputStream();
		// create a temporary file in that directory
		File tempFile = File.createTempFile("ticket", ".svg", new File(tmpDirPath));
		LOG.info(tempFile.getPath());
		LOG.info(getServletContext().getMimeType(tempFile.getName()));
		Commons.getPartySvgFile(0);
		// write to file
		FileWriter fw = new FileWriter(tempFile);
		try {
			IOUtils.skip(svgstr, "svgstr=".length());
			IOUtils.copy(svgstr, fw);
			fw.flush();
		} finally {
			response.setContentType("text/plain");
			response.getWriter().println(tempFile.getName());
			svgstr.close();
			fw.close();
		}
	}
}