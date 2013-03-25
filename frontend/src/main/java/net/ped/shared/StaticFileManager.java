package net.ped.shared;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
		try {
			URL url = MyHttpServlet.getInstance().getServletContext().getResource("/resources/ticketsdir/ticket3.svg");
			LOG.error(url.toURI().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File get(int id) throws IOException {
		setupDir();
		return FileStorage.getStaticFile(context, name + id + "." + extension);
	}
	
	private void setupDir() {
		File dir = new File(FileStorage.getServletStaticDirPath(), context);
		dir.mkdirs();
	}
}
