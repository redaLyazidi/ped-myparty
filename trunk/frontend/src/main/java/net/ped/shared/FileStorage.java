package net.ped.shared;

import java.io.File;

import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileStorage {
	protected static final Logger LOG = LoggerFactory.getLogger(FileStorage.class);

	private static HttpServlet myservlet = MyHttpServlet.getInstance();
	private static final String tmpDirPath = System.getProperty("java.io.tmpdir");

	public static boolean exists(File f) {
		return (f != null && f.exists());
	}
	
	public static File getServletDirPath() {
		return new File(myservlet.getServletContext().getRealPath("/"));
	}
	
	public static File getTemporaryDirPath() {
		return new File(tmpDirPath);
	}
	
	public static File createTempFile(String context, String name, String extension) {
		try {
			File dir = new File(tmpDirPath, context);
			/*LOG.debug("tmpDirPath: " + tmpDirPath);
			LOG.debug("name: " + name);
			LOG.debug("extension: " + extension);
			LOG.debug("dir: " + dir);*/
			File f =  File.createTempFile(name, extension, dir);
			LOG.debug("created temp file : "+f);
			return f;
		} catch(Exception e) {
			LOG.warn("Error when creating temp file");
			e.printStackTrace();
			return null;
		}
	}
	
	public static File getTempFile(String context, String name) {
		try {
			File dir = new File(tmpDirPath, context);
			return new File(dir, name);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static File getPermanentFile(String context, String name) {
		try {
			File dir = new File(getServletDirPath(), context);
			return new File(dir, name);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
