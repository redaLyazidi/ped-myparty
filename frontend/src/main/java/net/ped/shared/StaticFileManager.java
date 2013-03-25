package net.ped.shared;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticFileManager implements FileManagerItf {
	protected static final Logger LOG = LoggerFactory.getLogger(StaticFileManager.class);

	private String context;
	private String name;
	private String extension;

	public StaticFileManager(String context, String name, String extension) {
		try {
			this.context = context;
			this.name = name;
			this.extension = extension;
			setupDir();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public File get(int id) throws IOException {
		setupDir();
		return FileStorage.getStaticFile(context, name + id + "." + extension);
	}
	
	public URL getURL(HttpServletRequest req, int id) throws IOException {
		File f = get(id);
		if (FileStorage.exists(f) == false)
			return null;
		
		setupDir();
	    String scheme = req.getScheme();             // http
	    String serverName = req.getServerName();     // hostname.com
	    int serverPort = req.getServerPort();        // 80
	    String contextPath = MyHttpServlet.getInstance().getServletContext().getContextPath();   // /myparty-frontend

	    // Reconstruct original requesting URL
	    StringBuffer url =  new StringBuffer();
	    url.append(scheme).append("://").append(serverName);

	    if ((serverPort != 80) && (serverPort != 443)) {
	        url.append(":").append(serverPort);
	    }
	    url.append(contextPath);
	    url.append("/") // desired path in the server
	       .append(FileStorage.getServletStaticDirPath().getName())
	       .append("/")
	       .append(context)
	       .append("/")
	       .append(f.getName());
	    LOG.info(url.toString());
	    return new URL(url.toString());
	}
	
	private void setupDir() {
		File dir = new File(FileStorage.getServletStaticDirPath(), context);
		dir.mkdirs();
	}
}
