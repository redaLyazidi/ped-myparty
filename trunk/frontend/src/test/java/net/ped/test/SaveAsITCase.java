package net.ped.test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveAsITCase extends TestCase {
	private static final Logger LOG = LoggerFactory.getLogger(SaveAsITCase.class);
	
	public void testStandard() throws Exception {
		HttpClient http = new HttpClient();

		PostMethod post = new PostMethod("http://localhost:8080/myparty-frontend/saveAs");
		post.setParameter("svgstr", "totototo");
		http.executeMethod(post);
		BufferedReader input = new BufferedReader(new InputStreamReader(post.getResponseBodyAsStream()));
		while (input.readLine() != null) // wait for the response to be completely received (synchronous call)
			;
		
		//assertTrue(true);
	}
}
