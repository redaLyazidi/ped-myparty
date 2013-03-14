package net.ped.shared;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackgroundRessourceCleaner implements Runnable {
	protected static final Logger LOG = LoggerFactory	.getLogger(BackgroundRessourceCleaner.class);
	private static BackgroundRessourceCleaner instance = null;
	private List<Pair<File, Pattern>> locations = new ArrayList<Pair<File, Pattern>>();
	private int checkIntervalSeconds;
	
	private BackgroundRessourceCleaner(int checkIntervalSeconds) {
		this.checkIntervalSeconds = checkIntervalSeconds;
	}

	public static BackgroundRessourceCleaner getInstance() {//int checkIntervalSeconds) {
		int checkIntervalSeconds = 10;
		if (instance == null)
			instance = new BackgroundRessourceCleaner(checkIntervalSeconds);
		return instance;
	}

	public void watchFiles(File directory, Pattern filenamePattern)
			throws Exception {
		if (!directory.isDirectory())
			throw new Exception();
		LOG.info("Added an entry to watch and auto delete files : "
				+ directory.getAbsolutePath());
		synchronized (locations) {
			locations.add(Pair.of(directory, filenamePattern));
		}
	}

	@Override
	public void run() {
		LOG.info("Cleaning the resources...");
		List<Pair<File, Pattern>> locationsCopy = null;
		synchronized (locations) {
			locationsCopy = new ArrayList<Pair<File, Pattern>>(locations);
		}

		for (Pair<File, Pattern> p : locationsCopy) {
			File dir = p.getLeft();
			Pattern filenamePattern = p.getRight();
			try {
				File[] contents = dir.listFiles();
				if (contents == null) {
					LOG.warn("Directory : " + dir + " isn't readable");
					continue;
				}
				for (File f : contents) {
					deleteIfTooOld(f, filenamePattern);
				}
			} catch (Exception e) {
				LOG.warn("Directory : " + dir + " isn't readable");
			}
		}
		LOG.info("Cleaning the resources... done !");
	}

	private void deleteIfTooOld(File f, Pattern filenamePattern) {
		if (f.isDirectory() == true)
			return;

		Matcher m = filenamePattern.matcher(f.getName());
		if (m.find() == true) {
			LOG.debug("Checking : " + f.getName());
			if (isTooOld(f.lastModified()) == false) {
				LOG.debug(f.getName() + " is recent");
				return;
			}

			LOG.info("Delete old file : " + f.getName());
			try {
				f.delete();
			} catch (Exception e) {
				LOG.info("Can't delete old file : " + f.getName());
			}
		}
	}

	private boolean isTooOld(long lastModif) {
		DateTime now = new DateTime();
		DateTime lastModif2 = new DateTime(new Date(lastModif));
		Duration ellapsed = new Duration(lastModif2, now);
		LOG.debug("time ellapsed : " + ellapsed.getStandardSeconds());
		return (ellapsed.getStandardSeconds() >= this.checkIntervalSeconds);
	}
}
