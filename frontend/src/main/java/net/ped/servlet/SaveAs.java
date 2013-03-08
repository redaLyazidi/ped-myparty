package net.ped.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.ped.shared.PedHttpServlet;

import org.apache.commons.io.IOUtils;

@SuppressWarnings("serial")
public class SaveAs extends PedHttpServlet {
	private static final String tmpDirPath = System
			.getProperty("java.io.tmpdir");

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		LOG.debug(tmpDirPath);
		doDownload(request, response, tmpDirPath, request.getParameter("url"),
				"ticket.svg");
	}


	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		InputStream svgstr = request.getInputStream();
		// create a temporary file in that directory
		File tempFile = File.createTempFile("ticket", ".svg");
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
			svgstr.close();
			fw.close();
		}

	}

	private void doDownload(HttpServletRequest request,
			HttpServletResponse response, String parent, String filename,
			String original_filename) throws IOException {
		File f = new File(parent, filename);
		ServletOutputStream op = response.getOutputStream();
		ServletContext context = getServletConfig().getServletContext();
		String mimetype = context.getMimeType(filename);

		// Set the response and go!
		response.setContentType((mimetype != null) ? mimetype
				: "application/octet-stream");
		response.setContentLength((int) f.length());
		response.setHeader("Content-Disposition", "attachment; filename=\""
				+ original_filename + "\"");

		// Stream to the requester.
		DataInputStream in = new DataInputStream(new FileInputStream(f));
		IOUtils.copy(in, op);
		in.close();
		op.flush();
		op.close();
	}
}