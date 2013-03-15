package net.ped.shared;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

public class PermanentFileManager {

	private String context;
	private String name;
	private String extension;

	public PermanentFileManager(String context, String name, String extension,
			boolean autoDelete) throws Exception {
		this.context = context;
		this.name = name;
		this.extension = extension;
		if (autoDelete) {
			File dir = new File(FileStorage.getServletDirPath(), context);
			Pattern filenamePattern = Pattern.compile(name + "(.*)\\." + extension);
			BackgroundRessourceCleaner.getInstance().watchFiles(dir, filenamePattern);
		}
	}

	public File get(int id) throws IOException {
		return FileStorage.getTempFile(context, name + id + "." + extension);
	}
}
