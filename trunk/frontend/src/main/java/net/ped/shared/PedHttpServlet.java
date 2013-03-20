package net.ped.shared;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public abstract class PedHttpServlet extends HttpServlet {
	private static final Logger LOG = LoggerFactory.getLogger(PedHttpServlet.class);

    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	init();
    }
    
    public void init() {}
	
	public static String getMandatoryStringParameter(HttpServletRequest request, String name) throws Exception {
		String param = request.getParameter(name);
		if (param == null)
			throw new Exception();
		return param;
	}

	public static int getMandatoryIntParameter(HttpServletRequest request, String name) throws Exception {
		return Integer.parseInt(getMandatoryStringParameter(request, name));
	}
}
