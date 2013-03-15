package net.ped.shared;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InkscapeSvgToPdf implements SvgToPdf {
	
	protected static final Logger LOG = LoggerFactory.getLogger(SvgToPdf.class);

	@Override
	public File convert(File svg) {
		Runtime runtime = Runtime.getRuntime();
		StringBuilder cmdBuilder = new StringBuilder();
		
		File pdf = null;
		pdf = FileStorage.createTempFile("ticketpdf", "ticket", ".pdf");

		cmdBuilder.append("inkscape -f ").append(svg.getAbsolutePath()).append(" -A ").append(svg.getParent())
		.append('/').append(svg.getName()).append(".pdf");
//		String cmd = "inkscape -f " + svg.getAbsolutePath() + " -A " + svg.getParent() + '/' + svg.getName() + ".pdf";
		String cmd = cmdBuilder.toString();
		LOG.info("cmd: " + cmd);
		if(! cmd.isEmpty()) {
			try {
				runtime.exec(cmd);
			} catch (SecurityException se) {
				LOG.debug("You're not allowed to use that command on this server\n" + cmd);
				return null;
			} catch (IOException ioe) {
				LOG.debug("An error occured during the execution of the command:\n" + cmd);
				return null;
			}
		}
		return pdf;
	}

}
