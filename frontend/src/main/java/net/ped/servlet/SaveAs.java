package net.ped.servlet;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet implementation class SaveAs
 */
public class SaveAs extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(SaveAs.class);
	private static final int BUFSIZE = 1024;
	private static final String tmpDirPath = System.getProperty("java.io.tmpdir");
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SaveAs() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.debug(tmpDirPath);
		doDownload(request, response,tmpDirPath, request.getParameter("url"), "ticket.svg");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//		PrintWriter pw = response.getWriter();
		String svgstr = request.getParameter("svgstr");
		//		System.out.println(svgstr);
		//File tempDir = (File) getServletContext().getAttribute( "svgstr" );
		// create a temporary file in that directory
		File tempFile = File.createTempFile( "ticket", ".svg");//, tempDir );
		LOG.info(tempFile.getPath());
		LOG.info(getServletContext().getMimeType(tempFile.getName()));

		// write to file
		FileWriter fw = new FileWriter( tempFile );
		try {
			fw.write( svgstr );
			LOG.debug("essai");
		}
		finally {
			response.setContentType("text/plain");
			response.getWriter().println(tempFile.getName());
			fw.close();
		}

	}

	/**
	 *  Sends a file to the ServletResponse output stream.  Typically
	 *  you want the browser to receive a different name than the
	 *  name the file has been saved in your local database, since
	 *  your local names need to be unique.
	 *
	 *  @param request The request
	 *  @param response The response
	 *  @param filename The name of the file you want to download.
	 *  @param original_filename The name the browser should receive.
	 */
	private void doDownload( HttpServletRequest request, HttpServletResponse response,
			String parent, String filename , String original_filename )
					throws IOException
					{
		File                f        = new File(parent, filename);
		int                 length   = 0;
		ServletOutputStream op       = response.getOutputStream();
		ServletContext      context  = getServletConfig().getServletContext();
		String              mimetype = context.getMimeType( filename );

		//
		//  Set the response and go!
		//
		//
		response.setContentType( (mimetype != null) ? mimetype : "application/octet-stream" );
		response.setContentLength( (int)f.length() );
		response.setHeader( "Content-Disposition", "attachment; filename=\"" + original_filename + "\"" );

		//
		//  Stream to the requester.
		//
		byte[] bbuf = new byte[BUFSIZE];
		DataInputStream in = new DataInputStream(new FileInputStream(f));

		while ((in != null) && ((length = in.read(bbuf)) != -1))
		{
			op.write(bbuf,0,length);
		} 
		in.close();
		op.flush();
		op.close();
					}
}