package net.ped.shared;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

public class Commons {
	public static String getMandatoryStringParameter(HttpServletRequest request, String name) throws Exception {
		String param = request.getParameter(name);
		if (param == null)
			throw new Exception();
		return param;
	}
	
	public static int getMandatoryIntParameter(HttpServletRequest request, String name) throws Exception {
		return Integer.getInteger(getMandatoryStringParameter(request, name));
	}
	
	public File getPartySvgFile(int idParty) {
		return null;
	}
}
