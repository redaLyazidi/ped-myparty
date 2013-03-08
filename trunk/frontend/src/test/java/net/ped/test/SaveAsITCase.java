package net.ped.test;

import junit.framework.TestCase;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveAsITCase extends TestCase {
	private static final Logger LOG = LoggerFactory.getLogger(SaveAsITCase.class);
	
	static String saveAsUrl = "http://localhost:8080/myparty-frontend/saveas";
	
	public void testStandard() throws Exception {
		String content = new String("<svg>totototo</svg>".getBytes(), "UTF-8");
		
		HttpClient http = new HttpClient();
		PostMethod post = new PostMethod(saveAsUrl);
		//post.setRequestHeader("Content-Type", "text/plain; charset=UTF-8");
		post.addParameter(new NameValuePair("svgstr", content));
		http.executeMethod(post);
		String filename = post.getResponseBodyAsString();
		LOG.info("Save : " + filename);
		
		GetMethod get = new GetMethod(saveAsUrl + "?url=" + filename);
		//get.setRequestHeader("Content-Type", "charset=UTF-8");
		http.executeMethod(get);
		String repContent = get.getResponseBodyAsString();
		LOG.info("Rep : " + repContent);

		assertEquals(content, repContent); // encoding problem !
		// should test if the file is still on disk ?
	}
}
