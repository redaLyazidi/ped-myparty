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
		try {
			setupDir();
			File dir = new File(FileStorage.getTemporaryDirPath(), context);
			pattern = Pattern.compile(name + "(.*)\\." + extension);
			if (autoDelete) 
				BackgroundRessourceCleaner.getInstance().watchFiles(dir, pattern);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public File create() {
		setupDir();
		return FileStorage.createTempFile(context, name, "." + extension);
	}
	
	public File get(int id) {
		setupDir();
		File f = FileStorage.getTempFile(context, name + id + "." + extension);
		LOG.debug("from FileStorage: " + f.toString());
		return f;
	}
	
	public File get(String name) {
		setupDir();
		if (pattern.matcher(name).find() == false) {
			LOG.info("Problem : getting file : " + name + " in fileManager : " + this);
			return null;
		}
		return FileStorage.getTempFile(context, name);
	}
	
	private void setupDir() {
		File dir = new File(FileStorage.getTemporaryDirPath(), context);
		dir.mkdirs();
		LOG.debug("setupDir is fine");
	}
}
