package net.ped.shared;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServlet;

public class FileStorage {
	private static HttpServlet myservlet = MyHttpServlet.getInstance();
	private static final String tmpDirPath = System.getProperty("java.io.tmpdir");

	
	public static File getServletDirPath() {
		return new File(myservlet.getServletContext().getRealPath("/"));
	}
	
	public static File getTemporaryDirPath() {
		return new File(myservlet.getServletContext().getRealPath("/"));
	}
	
	public static File createTempFile(String context, String name, String extension) throws IOException {
		File dir = new File(tmpDirPath + context);
		return File.createTempFile(name, extension, dir);
	}
	
	public static File getTempFile(String context, String name) throws IOException {
		return new File(tmpDirPath + context, name);
	}

	public static File getPermanentFile(String context, String name) throws IOException {
		File dir = new File(getServletDirPath() + context);
		return new File(dir.getAbsolutePath(), name);
	}
}
