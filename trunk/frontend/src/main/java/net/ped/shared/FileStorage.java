package net.ped.shared;

import java.io.File;

import javax.servlet.http.HttpServlet;

public class FileStorage {
	private static HttpServlet myservlet = MyHttpServlet.getInstance();
	private static final String tmpDirPath = System.getProperty("java.io.tmpdir");

	public static boolean exists(File f) {
		return (f != null && f.exists());
	}
	
	public static File getServletDirPath() {
		return new File(myservlet.getServletContext().getRealPath("/"));
	}
	
	public static File getTemporaryDirPath() {
		return new File(myservlet.getServletContext().getRealPath("/"));
	}
	
	public static File createTempFile(String context, String name, String extension) {
		try {
			File dir = new File(tmpDirPath + context);
			return File.createTempFile(name, extension, dir);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static File getTempFile(String context, String name) {
		try {
			return new File(tmpDirPath + context, name);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static File getPermanentFile(String context, String name) {
		try {
			File dir = new File(getServletDirPath() + context);
			return new File(dir.getAbsolutePath(), name);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
