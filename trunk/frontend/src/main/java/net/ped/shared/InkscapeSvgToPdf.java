package net.ped.shared;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InkscapeSvgToPdf implements SvgToPdf {
	
	protected static final Logger LOG = LoggerFactory.getLogger(SvgToPdf.class);
	protected static final int bufferSize = 4096;
	
	@Override
	public boolean convert(File svg, File output) {
		//String svgfullname = svg.getName();
		//String svgname = svgfullname.substring(0, svgfullname.length() -4);
		StringBuilder cmdBuilder = new StringBuilder();
		cmdBuilder.append("inkscape -f ").append(svg.getAbsolutePath()).append(" -A ").append(output.getAbsolutePath());
		//String cmd = "inkscape -f " + svg.getAbsolutePath() + " -A " + svg.getParent() + '/' + svg.getName() + ".pdf";
		String cmd = cmdBuilder.toString();
		LOG.info("cmd: " + cmd);
		try {
			Runtime runtime = Runtime.getRuntime();
			Process process = runtime.exec(cmd);
			BufferedInputStream bufin = new BufferedInputStream(process.getInputStream());
			byte[] buffer = new byte[bufferSize];
			
			while (bufin.read(buffer) != -1) // empty the command standard output buffer to avoid a deadlock
				; // please note that there can still be a deadlock with the error output buffer.
			
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				LOG.debug("inkscape was interrupted");
				e.printStackTrace();
				return false;
			}
	        LOG.debug("done !");
		} catch (SecurityException se) {
			LOG.debug("You're not allowed to use that command on this server\n" + cmd);
			return false;
		} catch (IOException ioe) {
			LOG.debug("An error occured during the execution of the command");
			LOG.debug(cmd);
			return false;
		}
		return true;
	}
}
