package com.example.scanner;

import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.scanner.ServiceCaller.notJsonException;

import android.app.Activity;

import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class LoginActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}
	
	/**
	 * Clear a text field 
	 * 
	 * @param v the view where the click comes from
	 */
	public void clearField(View v) {
		EditText view = (EditText)v;
		view.setText("");
	}

	/**
	 * Action to perform upon a click on the login form
	 * 
	 * @param v the view where the click comes from
	 */
	public void performLogin(View v) {
		AsyncTask<String, Void, JSONObject> 
		          callBack;   // result of the web service call
		CharSequence login;   // login from the textfield
		CharSequence pwd;     // password from the textfield
		final String address; // web service address
		JSONObject userJson;  // login and password fields formatted into json
		ServiceCaller call;   // caller to call the web service
		
		login = ((TextView)findViewById(R.id.login)).getText();
		pwd = ((TextView)findViewById(R.id.pwd)).getText();
		address = "http://10.0.2.2:8080/myparty-frontend/rest/service/connexion";
        userJson = new JSONObject();
		call = new ServiceCaller(ServiceCaller.POST, userJson);
		
		// building json body request
        try {
			userJson.put("password", pwd.toString());
			userJson.put("login", login.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		
		callBack = call.execute(address);
		
		// waiting for the result
		try {
			userJson = callBack.get();
			System.out.println(userJson);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("result: " + userJson);
		
        // checking the login and perform appropriate action
		try {
			if(userJson.get("login").equals("null")) { // if login ok
				// going to the next view
				//setContentView(R.layout.scan);
				System.out.println("ok" + userJson.get("login"));
			} else { // login not ok, displaying an error
				((TextView)findViewById(R.id.error)).setVisibility(View.VISIBLE);
				//System.out.println("error");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
