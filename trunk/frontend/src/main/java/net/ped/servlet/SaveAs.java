package net.ped.servlet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ped.shared.Commons;
import net.ped.shared.FileStorage;
import net.ped.shared.PedHttpServlet;
import net.ped.shared.TempFileManager;

import org.apache.commons.io.IOUtils;

@SuppressWarnings("serial")
public class SaveAs extends PedHttpServlet {
	static TempFileManager tempFileManager = null;

	public void init() {
		if (tempFileManager == null) {
			try {
				tempFileManager = new TempFileManager("saveas", "ticket", "svg", true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// step 1
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		InputStream svgstr = request.getInputStream();
		// create a temporary file in that directory
		File tempFile = tempFileManager.create();
		LOG.info(tempFile.getPath());
		LOG.info(getServletContext().getMimeType(tempFile.getName()));

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

	// step 2
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("url");
		if (filename == null)
			throw new ServletException("Missing config parameter : url");
		File diskFile = tempFileManager.get(filename);
		if (FileStorage.exists(diskFile))
			Commons.sendFileDownloadResponse(request, response, diskFile,
					"ticket.svg");
		else
			response.sendError(404);
	}
}
