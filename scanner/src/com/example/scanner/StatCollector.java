package com.example.scanner;

import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

/**
 * Upon run, call the web service to get the stat 
 * indefinitly.
 * 
 * @author blaze
 */
public class StatCollector extends Thread {

	String serviceAddr;

	LoginActivity context;
	
	boolean stop;

	/**
	 * 
	 * @param addr webservice address
	 * @param v the v where to display the stat
	 */
	public StatCollector(String addr, LoginActivity c) {
		serviceAddr = addr;
		context = c;
		stop = false;
	}

	public void run() {

	}
	
	public void stopExec() {
		stop = true;
	}
}

