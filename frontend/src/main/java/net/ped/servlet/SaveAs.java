package net.ped.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.ped.shared.Commons;
import net.ped.shared.FileStorage;
import net.ped.shared.PedHttpServlet;
import net.ped.shared.TempFileManager;

@SuppressWarnings("serial")
public class SaveAs extends PedHttpServlet {
	static TempFileManager tempFileManager = null;
	private static final Logger LOG = LoggerFactory.getLogger(SaveAs.class);
	
	public void init() {
		if (tempFileManager == null) {
			tempFileManager = new TempFileManager("saveas", "ticket", "svg", true);
		}
	}

	// step 1
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Commons.answerSvgLocationFromSpecificRequest(request,response,tempFileManager);
	}

	// step 2
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String filename = request.getParameter("url");
		if (filename == null)
			throw new ServletException("Missing config parameter : url");
		LOG.info("filename " + filename);
		File diskFile = tempFileManager.get(filename);
		LOG.debug("diskFile: " + diskFile.getAbsolutePath());
		if (FileStorage.exists(diskFile))
			Commons.sendFileDownloadResponse(request, response, diskFile,
					"ticket.svg");
		else
			response.sendError(404);
	}
}
