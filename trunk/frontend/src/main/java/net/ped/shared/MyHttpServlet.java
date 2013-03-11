package net.ped.shared;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MyHttpServlet extends HttpServlet {
	private static final long serialVersionUID = -2248413601020787057L;
	private static MyHttpServlet instance = null;

	public MyHttpServlet() {
		super();
	}
	
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	instance = this;
    }

    public HttpServlet getInstance() {
    	return instance;
    }
}
