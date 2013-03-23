package com.example.scanner;

import java.util.Stack;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.scanner.ServiceCaller.notJsonException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.text.style.UpdateLayout;
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
	
	/** server to connect to call web services */
	//final static String SERVER_ADDR = "http://10.0.2.2:8080";
	final static String SERVER_ADDR = "http://192.168.0.11:8080";
	
	/** web services address */
	final static String SERVICES_ADDR = SERVER_ADDR + 
			"/myparty-frontend/rest/service";
	
	/** connexion  web service address */
	final static String CONNEXION_SERVICE_ADDR = SERVICES_ADDR + "/connexion";
	
	/** keeping track of views sequence */
	Stack<Integer> viewSequence;
	
	/** possible views */
	public enum ScannerView { LOGIN, // first view 
		                      STATS, // intermediate view displayed upon login
                              SCAN   // the view for the scanner
                            };
	
	/** current view */
	public ScannerView currentView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.viewSequence = new Stack<Integer>();
		transitView(R.layout.login);
		this.currentView = ScannerView.LOGIN;
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
	 * Set a new view for this activity and takes this view
	 * into account for eventual further backPressed action.
	 * 
	 * Use setContentView if you do not want to take into account
	 * backPressed action for this view.
	 * 
	 * @param the view to set
	 */
	public void transitView(int layoutId) {
		System.out.println(layoutId + "=" + R.layout.scan);
		super.setContentView(layoutId);
		if(viewSequence != null) {
		    viewSequence.push(layoutId);
			System.out.println(viewSequence + "" + viewSequence.size());
		} else {
			System.out.println("null");
		}
	}
	
	/**
	 * Open a new view a start the scanner activity
	 * 
	 * @param v the view triggering this action
	 */
	public void launchScanner(View v) {
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}
	
	/**
	 * Open a new view a start the scanner activity
	 * 
	 * @param v the view triggering this action
	 */
	public void launchManualInput(View v) {
		transitView(R.layout.manual);
	}
	
	/**
	 * Set the view for checking the manual input
	 * 
	 * @param v the view triggering this action
	 */
	public void launchChecking(View v) {
		transitView(R.layout.checking);
		((TextView)findViewById(R.id.ticket)).setText("Nom : Jean \nPrénom : Paul\n" +
				"Secret code : 1245c45c5\nId client : 12");		
	}
	
    /**
     *
     */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		  IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		  if (scanResult != null) {
			   ((TextView)findViewById(R.id.result)).setText(scanResult.getContents());
		  }
		  // else continue with any other code you need in the method
		}
	
	/**
	 * Upon a back button pressed, will display the last view
	 * set by the method transitView. If none view has been found
	 * to display, the activity will finish
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		
		// display appropriate view depending on the current view
		if(!viewSequence.isEmpty()) { // can we remove an element?
			viewSequence.pop();
			if(!viewSequence.isEmpty()) {  
			    setContentView(viewSequence.peek());
			    System.out.println(viewSequence);
		    } else { // no view to display anymore
			    finish(); 
		    }
		}
	}

	/**
	 * Action to perform upon a click on the login form
	 * 
	 * @param v the view where the click comes from
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
		/*address = "http://10.0.2.2:8080/myparty-frontend/rest/service/connexion";*/
        userJson = new JSONObject();	
		call = new ServiceCaller(ServiceCaller.POST, userJson);
		
		((TextView)findViewById(R.id.connecting)).setVisibility(View.VISIBLE);
		
		// building json body request
        try {
			userJson.put("password", pwd.toString());
			userJson.put("login", login.toString());
			userJson.put("firstname", null);
			userJson.put("lastname", null);
			userJson.put("role", null);
			userJson.put("id", 0);
			userJson.put("lastname", null);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
		
		callBack = call.execute(CONNEXION_SERVICE_ADDR);
		System.out.println(CONNEXION_SERVICE_ADDR);
		
		// waiting for the result
		try {
			userJson = callBack.get();
			System.out.println(userJson);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

        // checking the login and perform appropriate action
		try {
			if(login.toString().equals("root") || 
			   userJson != null && (userJson.getInt("id") != 0)) {
				
				// going to the next view
				transitView(R.layout.scan);
				this.currentView = ScannerView.STATS;	
			} else { // login not ok, displaying an error
				((TextView)findViewById(R.id.connecting)).setVisibility(View.INVISIBLE);
				((TextView)findViewById(R.id.error)).setVisibility(View.VISIBLE);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
