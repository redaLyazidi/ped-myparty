package net.ped.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.IOUtils;

// This class is used as a proxy to download image from client side that come from another domain.
// It can retreive any http resource, not only images
// found at : http://www.adrianwalker.org/2009/09/http-proxy-servlet.html
// Use it like this : 
// http://localhost:8080/myparty-frontend/imgUrl?url=http://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50?s=200&r=pg&d=404
// http://localhost:8080/myparty-frontend/imgUrl?url=http://openwalls.com/image/399/explosion_of_colors_1920x1200.jpg

// Should improve security ? What if a hacker tries to access local resources?
// Doesn't follow url redirection... is it a pb ? Ex : http://localhost:8080/myparty-frontend/imgUrl?url=http://google.fr 

public final class ImageProxy extends HttpServlet {
	final String http =  "http://";
	final String https = "https://";
	final String start = "url=";


	@Override
	protected void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		System.out.println("Get ok");
		doAux(request, response, new GetMethod());
	}

	@Override
	protected void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		doAux(request, response, new PostMethod());
	}

	protected void doAux(HttpServletRequest request,
			final HttpServletResponse response, HttpMethodBase proxyMethod)
			throws ServletException, IOException {
		String strUrl = request.getQueryString();
		if (strUrl == null || strUrl.length() < start.length())
			throw new ServletException();
		strUrl = strUrl.substring(start.length()); // remove : url=
		String lowerUrl = strUrl.toLowerCase();
		if (lowerUrl.startsWith(http) == false && lowerUrl.startsWith(https) == false) {
			strUrl = http + strUrl;   // ensure we have an http or an https address (external address)
			//System.out.println("Added http");
		}
		URL url = new URL(strUrl);
		//System.out.println(url);
		proxyMethod.setPath(url.toString());
		HttpClient proxy = new HttpClient();
		proxy.getHostConfiguration().setHost(url.getHost());

		proxy.executeMethod(proxyMethod);
		write(proxyMethod.getResponseBodyAsStream(), response.getOutputStream());
	}

	// should optimize ??
	private void write(final InputStream inputStream,
			final OutputStream outputStream) throws IOException {
		BufferedInputStream input = new BufferedInputStream(inputStream);
		BufferedOutputStream output = new BufferedOutputStream(outputStream);
		IOUtils.copy(input, output);
		output.flush();
		IOUtils.closeQuietly(input);
		//IOUtils.closeQuietly(output);
	}
}