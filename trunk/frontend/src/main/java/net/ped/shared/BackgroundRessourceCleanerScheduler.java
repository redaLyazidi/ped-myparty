package net.ped.shared;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class BackgroundRessourceCleanerScheduler implements ServletContextListener {
	private ScheduledExecutorService scheduler;
	private static final int checkIntervalSeconds = 100; 

	@Override
	public void contextInitialized(ServletContextEvent event) {
		scheduler = Executors.newSingleThreadScheduledExecutor();
		BackgroundRessourceCleaner task = BackgroundRessourceCleaner.getInstance();
		task.setInterval(checkIntervalSeconds);
		scheduler.scheduleAtFixedRate(task, 0, checkIntervalSeconds, TimeUnit.SECONDS);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		scheduler.shutdownNow();
	}
}
