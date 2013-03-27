package com.example.scanner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.SerializableEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.JsonWriter;
import android.util.Log;

/**
 * Utility class to call Rest/JSon based web services.
 * 
 */
public class ServiceCaller extends AsyncTask<String, Void, String> {
	
	/** Value to use to specify we want to use the post method */
	public static final String POST = "post";
	
	/** Value to use to specify we want to use the get method */
	public static final String GET = "get";
	
	/** The method used for the request (get or post) */
	protected String method;
	
	/** Result of the web service to call*/
	protected String result;
	
	/** Additional parameter for post method requests */
	protected JSONObject param;
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public JSONObject getParam() {
		return param;
	}

	public void setParam(JSONObject param) {
		this.param = param;
	}

	/**
	 * 
	 * @param method ServiceCaller.GET or ServiceCaller.POST
	 */
	public ServiceCaller(String method) {
		super();
		this.method = method;		
	}
	
	/**
	 * 
	 * @param method ServiceCaller.GET or ServiceCaller.GPOST
	 */
	public ServiceCaller(String method, JSONObject param) {
		super();
		this.method = method;		
		this.param = param;	
	}
	
	protected String doGetRequest(String uri) {
				
		String output, result = "";  
		
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
			HttpConnectionParams.setSoTimeout(httpClient.getParams(), 20000);
			
			HttpGet getRequest = new HttpGet(uri);
			getRequest.addHeader("accept", "application/json");
			
			System.out.println("get method" + uri);

			HttpResponse response = httpClient.execute(getRequest);
			
			System.out.println("ok");

			if (response.getStatusLine().getStatusCode() != 200) {
				Log.d("dogetrequest", "Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
				return result;
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader((response.getEntity().getContent())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				result += output;
			}

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	protected String doPostRequest(String uri, JSONObject param) {
		String   output, // intermediate result of the request
		         result; // final result of the request
		
		output = result =  "";     
		
		try {
 
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
			HttpConnectionParams.setSoTimeout(httpClient.getParams(), 20000);
			
			HttpPost postRequest = new HttpPost(uri);
			postRequest.addHeader(HTTP.CONTENT_TYPE, "application/json");
			//postRequest.addHeader("accept", "application/json");
			
			postRequest.setEntity(new StringEntity(param.toString()));

			HttpResponse response = httpClient.execute(postRequest);
			
			System.out.println(param.toString());

			if (response.getStatusLine().getStatusCode() != 200) {
				Log.d("dogetrequest", "Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
				return result;
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader((response.getEntity().getContent())));

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				result += output;
				System.out.println(output);
			}

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
	}

	protected String doInBackground(String ... uri) {	
		if(method.equals(GET))
		   result = doGetRequest(uri[0]);
		else if(method.equals(POST))
		   result = doPostRequest(uri[0], this.param);
		
		return result;
	}

}
