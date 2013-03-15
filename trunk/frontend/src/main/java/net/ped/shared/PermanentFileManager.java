package net.ped.shared;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class PermanentFileManager {

	private String context;
	private String name;
	private String extension;

	public PermanentFileManager(String context, String name, String extension) {
		try {
			this.context = context;
			this.name = name;
			this.extension = extension;
			setupDir();
			/*if (autoDelete) {
				File dir = new File(FileStorage.getServletDirPath(), context);
				Pattern filenamePattern = Pattern.compile(name + "(.*)\\." + extension);
				BackgroundRessourceCleaner.getInstance().watchFiles(dir, filenamePattern);
			}*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public File get(int id) throws IOException {
		setupDir();
		return FileStorage.getPermanentFile(context, name + id + "." + extension);
	}
	
	private void setupDir() {
		File dir = new File(FileStorage.getServletDirPath(), context);
		dir.mkdirs();
	}
}
