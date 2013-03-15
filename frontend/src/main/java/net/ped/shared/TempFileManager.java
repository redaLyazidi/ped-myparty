package net.ped.shared;

import java.io.File;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempFileManager {
	protected static final Logger LOG = LoggerFactory.getLogger(TempFileManager.class);

	private String context;
	private String name;
	private String extension;
	private Pattern pattern;
	
	public TempFileManager(String context, String name, String extension, boolean autoDelete) {
		this.context = context;
		this.name = name;
		this.extension = extension;
		File dir = new File(FileStorage.getTemporaryDirPath(), context);
		pattern = Pattern.compile(name + "(.*)\\." + extension);
		if (autoDelete) {
			try {
				BackgroundRessourceCleaner.getInstance().watchFiles(dir, pattern);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public File create() {
		return FileStorage.createTempFile(context, name, "." + extension);
	}
	
	public File get(int id) {
		return FileStorage.getTempFile(context, name + id + "." + extension);
	}
	
	public File get(String name) {
		if (pattern.matcher(name).find() == false)
			LOG.info("Problem : getting file : " + name + " in fileManager : " + this);
		return FileStorage.getTempFile(context, name);
	}
}
