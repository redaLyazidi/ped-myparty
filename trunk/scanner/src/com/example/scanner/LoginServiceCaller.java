package com.example.scanner;

import java.security.PublicKey;

import org.json.JSONObject;

import android.view.View;
import android.widget.TextView;

public class LoginServiceCaller extends ServiceCaller {
	
	protected TextView displayConnecting;
	
	protected TextView displayError;

	public LoginServiceCaller(String method) {
		super(method);
		// TODO Auto-generated constructor stub
	}
	
	public LoginServiceCaller(String method, JSONObject json) {
		super(method, json);
	}
	
	public LoginServiceCaller(String method, JSONObject json, 
			                  TextView connecting, TextView errorLogin) {
		super(method, json);
		displayConnecting = connecting;
		displayError = errorLogin;
	}
	
	@Override
	protected JSONObject doInBackground(String ... uri) {
		return super.doInBackground(uri);
	}
	
    @Override
    protected void onPreExecute() {
    	System.out.println("yolo");
    	displayConnecting.setVisibility(View.VISIBLE);
    }
    
    @Override
    protected void onPostExecute(JSONObject result) {
    	displayConnecting.setVisibility(View.GONE);
    }

}
