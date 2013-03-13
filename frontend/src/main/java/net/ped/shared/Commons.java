package net.ped.shared;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Commons {

	private static final String ticketsextension =".svg";

	private static final Logger LOG = LoggerFactory.getLogger(Commons.class);
	private static HttpServlet myservlet = MyHttpServlet.getInstance();

	public static String getMandatoryStringParameter(HttpServletRequest request, String name) throws Exception {
		String param = request.getParameter(name);
		if (param == null)
			throw new Exception();
		return param;
	}

	public static int getMandatoryIntParameter(HttpServletRequest request, String name) throws Exception {
		return Integer.getInteger(getMandatoryStringParameter(request, name));
	}

	public static File getPartySvgFile(int idParty) throws IOException {
		
		if (idParty < 0) {
			LOG.debug("The id can't be lower than 0, id = " + idParty);
			return null;
		}
		
		
		
		String webappPath = myservlet.getServletContext().getRealPath("/");
		String fromparameter = myservlet.getServletContext().getInitParameter("ticketsDir");
		LOG.info("fromparameter: " + fromparameter);
		String absoluteticketsPath =new StringBuilder(webappPath).append(fromparameter).append("ticket").append(ticketsextension).toString();
		LOG.info("absoluteticketsPath: " + absoluteticketsPath);
		File ticketsDir = new File(absoluteticketsPath);
		String thisPartyticketPath = new StringBuilder(absoluteticketsPath).append(idParty).append(ticketsextension).toString();
		
		File ticketOfIdParty = new File(thisPartyticketPath);
		if (! ticketOfIdParty.exists()) {
			LOG.debug("There no svg file withe name: " + idParty);
			return null;
		}
		LOG.info(thisPartyticketPath);
		LOG.info( myservlet.getServletContext().getInitParameter("svgQRcodetag"));


		LOG.info("before the exec");
		Runtime runtime = Runtime.getRuntime();
		LOG.info(webappPath + fromparameter);
		String cmd = "inkscape -f " + webappPath + fromparameter + "github.svg -A " + webappPath + fromparameter + "github.pdf";
		LOG.info(cmd);
		runtime.exec(cmd);
		LOG.info("after the exec");
		if (new File(absoluteticketsPath).exists())
			LOG.info("I found it");
		return null;
	}
	
}
