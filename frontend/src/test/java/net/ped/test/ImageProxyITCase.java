package net.ped.test;

import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sun.net.www.protocol.http.HttpURLConnection;


public class ImageProxyITCase extends TestCase {
	private static final Logger LOG = LoggerFactory.getLogger(ImageProxyITCase.class);
	
	static String proxyUrl = "http://localhost:8080/myparty-frontend/imgUrl?url=";
	
	String[] variousUrls = {
			"zozozo",
			"http://zozozo.fr",
			//"https://ssl.gstatic.com/images/icons/feature/filing_cabinet-g42.png", // https OK but not covered by tests (httpUrlConnection -> HttpsUrlConversion)
			"http://www.commentcamarche.net/forum/affich-24008559-tester-image-existe-sur-un-serveur-web-java", // not an image
			"http://google.fr"
		};
	
	private URL proxyForURL(String externalUrl) {
		try {
			return new URL(proxyUrl + externalUrl);
		} catch (MalformedURLException e) {
			assertTrue(false);
			return null;
		}
	}
	
	private void checkSameResponse(URL baseUrl, URL routedUrl) throws Exception {
		HttpURLConnection connection1 = (HttpURLConnection) baseUrl.openConnection();
		connection1.setInstanceFollowRedirects(false); // ImageProxy redirection doesn't work -> error 500
		HttpURLConnection connection2 = (HttpURLConnection) routedUrl.openConnection();
		connection2.setInstanceFollowRedirects(false);
		
		try { // is a valid url ?
			connection1.getResponseCode();
		} catch (Exception e) {
			assertTrue(connection2.getResponseCode() != 200);
			return;
		}
		
		LOG.debug("Response : " + connection1.getResponseCode() + ", proxy response : " + connection2.getResponseCode() + "-- for url : " + baseUrl);
		assertEquals (
				(connection1.getResponseCode() == 200),
				(connection2.getResponseCode() == 200)
				);
		if (connection1.getResponseCode() == 200 && connection2.getResponseCode() == 200) {
			// The two requests should have the same content (perhaps different headers, or one gzipped and not the other?)
			int length1 = Math.abs(connection1.getContentLength());
			int length2 = Math.abs(connection2.getContentLength());
			double diff = 1.3;
			
			LOG.debug("URL : " + baseUrl + ", length1 : " + length1 + ", length2 : " + length2);
			assertTrue(length1 / diff < length2);
			assertTrue(length1 * diff > length2);
		}
	}
	
	public void testValidUrl() throws Exception {
		URL baseUrl = new URL("http://openwalls.com/image/399/explosion_of_colors_1920x1200.jpg");
		URL routedUrl = proxyForURL(baseUrl.toString());
		
		HttpURLConnection connection1 = (HttpURLConnection) baseUrl.openConnection(); 
		assertTrue(connection1.getResponseCode() == 200);
		HttpURLConnection connection2 = (HttpURLConnection) routedUrl.openConnection(); 
		assertTrue(connection2.getResponseCode() == 200);
		checkSameResponse(baseUrl, routedUrl);
	}
	
	public void testSameResponse() throws Exception {
		for(String url : variousUrls) {
			URL routedUrl = proxyForURL(url);
			try {
				URL baseUrl = new URL(url);
				checkSameResponse(baseUrl, routedUrl);
			} catch (MalformedURLException e) {
				HttpURLConnection connection2 = (HttpURLConnection) routedUrl.openConnection();
				assertTrue(connection2.getResponseCode() != 200);
			}
		}
	}
}
