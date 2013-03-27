package com.example.scanner;

import java.util.Stack;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.ViewGroup;
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
	final static String SERVER_ADDR = "http://192.168.0.13:8080";

	/** web services address */
	final static String SERVICES_ADDR = SERVER_ADDR + 
			"/myparty-frontend/rest/service";

	/** connexion  web service address */
	final static String CONNEXION_SERVICE_ADDR = SERVICES_ADDR + "/connexion";

	/** ticket  web service address */
	final static String TICKET_SERVICE_ADDR = SERVICES_ADDR + "/ticket";

	/** ticket  web service address */
	final static String MANUAL_SERVICE_ADDR = SERVICES_ADDR + "/ticketManuel";

	/** list party  web service address */
	final static String PARTIES_SERVICE_ADDR = SERVICES_ADDR + "/listParty";

	/** list party  web service address */
	final static String SCAN_SERVICE_ADDR = SERVICES_ADDR + "/nbScan";

	/** keeping track of views sequence */
	Stack<Integer> viewSequence;

	Stack<ViewGroup> layoutSequence;

	protected int idParty;

	protected String summaryParty;

	protected StatCollector stats;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.viewSequence = new Stack<Integer>();
		this.layoutSequence = new Stack<ViewGroup>();
		transitView(R.layout.login);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
	}

	public String getSummaryParty() {
		return summaryParty;
	}

	public int getIdParty() {
		return idParty;
	}

	public void setSummaryParty(String summary) {
		summaryParty = summary;
	}

	public void setIdParty(int id) {
		idParty = id;
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
		super.setContentView(layoutId);
		transitView(layoutId, true);
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
	public void transitView(int layoutId, boolean transit) {
		super.setContentView(layoutId);
		if(viewSequence != null && transit) {
			viewSequence.push(layoutId);
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
	 * Open a new view a start the scanner activity
	 */
	public void launchScan(boolean transit) {
		System.out.println("wut2");
		transitView(R.layout.scan, transit);
		refreshNbScan(null);
		((TextView)findViewById(R.id.summery_party)).setText(summaryParty + 
				"\n\n");
	}

	/**
	 * Open a new view a start the scanner activity
	 */
	public void refreshNbScan(View v) {
		AsyncTask<String, Void, String> 
		              callBack;  // result of the web service call 
		String result;           // object json to communicate
		JSONObject  resultJson;
		TextView displayResult;  // view where to display the result
		ServiceCaller     call;

		call = new ServiceCaller(ServiceCaller.GET);
		callBack = call.execute(SCAN_SERVICE_ADDR + "/" + idParty);

		try {
			result = call.get();
			((TextView)findViewById(R.id.nb_scan)).setText(
					       "\nTickets scannés : " + result);		
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Upon a back button pressed, will display the last view
	 * set by the method transitView. If none view has been found
	 * to display, the activity will finish
	 */
	@Override
	public void onBackPressed() {
		int layoutId;
		// display appropriate view depending on the current view
		if(!viewSequence.isEmpty()) { // can we remove an element?
			viewSequence.pop();
			if(!viewSequence.isEmpty()) {
				layoutId = viewSequence.peek();
				System.out.println(viewSequence);
				switch(layoutId) {
				case R.layout.party_list : launchListParty(false);	
				break;
				case R.layout.scan : launchScan(false);	
				break;
				default : setContentView(layoutId);
				break;
				}
			} else { // no view to display anymore
				finish(); 
			}
		}
	}

	/**
	 * Set the view for checking the manual input
	 * 
	 * @param v the view triggering this action
	 */
	public void launchListParty(boolean transit) {
		AsyncTask<String, Void, String> 
		callBack;  // result of the web service call 
		String result;           // object json to communicate
		JSONObject  resultJson;
		TextView displayResult;  // view where to display the result
		ServiceCaller     call;
		JSONArray    listParty;

		transitView(R.layout.party_list, transit);

		try {
			call = new ServiceCaller(ServiceCaller.GET);
			callBack = call.execute(PARTIES_SERVICE_ADDR);

			System.out.println("yo");
			result = call.get();
			resultJson = new JSONObject(result);
			listParty = resultJson.getJSONArray("list");
			System.out.println("list : "+ listParty);
			((PartyList)findViewById(R.id.party_list)).fill(listParty);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch(JSONException e) {
			e.printStackTrace();
		} 
	}

	/**
	 * Set the view for checking the manual input
	 * 
	 * @param v the view triggering this action
	 */
	public void launchChecking(View v) {
		AsyncTask<String, Void, String> 
		callBack;  // result of the web service call 
		JSONObject customerJson,              // object json to communicate
		resultJson;
		String          result;
		TextView displayResult;               // view where to display the result
		ServiceCaller call;
		int idParty, idCustomer;

		customerJson = new JSONObject();
		displayResult = (TextView)findViewById(R.id.checking);

		try {
			idParty = Integer.valueOf(((TextView)findViewById(R.id.id_customer)).getText().toString());
			idCustomer = Integer.valueOf(((TextView)findViewById(R.id.id_party)).getText().toString());
			customerJson.put("idCustomer", idCustomer);
			customerJson.put("idParty", idParty);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		transitView(R.layout.checking);

		call = new ServiceCaller(ServiceCaller.POST, customerJson);
		callBack = call.execute(MANUAL_SERVICE_ADDR);

		try {
			result = call.get();
			resultJson = new JSONObject(result);
			((TextView)findViewById(R.id.ticket)).setText("" +
					"Information du ticket : " +
					"\n\nID party : " + resultJson.getInt("idParty") +
					"\nID client : " + resultJson.getInt("idCustomer"));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 *
	 */
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		AsyncTask<String, Void, String> 
		callBack;  // result of the web service call 
		IntentResult scanResult;              // result when the scan is done
		String result;                        // result of the qrcode scanned
		String[] results = {};                     // different parts of the result
		JSONObject customerJson = null;              // object json to communicate
		TextView displayResult;               // view where to display the result

		scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

		transitView(R.layout.qr_checking);
		displayResult = (TextView)findViewById(R.id.checking);

		if(scanResult.getContents() != null) {
			result = scanResult.getContents();
			customerJson = new JSONObject();
			results = result.split("\n");
		}

		if (scanResult != null && results.length == 3) {

			// building customer json object
			try {
				customerJson.put("idCustomer", Integer.valueOf(results[0]));
				customerJson.put("idParty", Integer.valueOf(results[1]));
				customerJson.put("secretCode", results[2]);

				if(Integer.valueOf(results[1]) != idParty) {
					displayResult.setText(R.string.error_party);
					System.out.println(idParty);
					return;
				}

			} catch (JSONException e) {
				displayResult.setText(R.string.error_checking);
			} catch (NumberFormatException e) {
				displayResult.setText(R.string.error_checking);
			}

			ServiceCaller call = new ServiceCaller(ServiceCaller.POST, customerJson);
			callBack = call.execute(TICKET_SERVICE_ADDR);

			// waiting for the result
			try {
				result = callBack.get();
				customerJson = new JSONObject(result);
				if(customerJson.getBoolean("validated")) {
					displayResult.setText(R.string.alreay_validated); 
				} else if(customerJson.getInt("idCustomer") != 0) { 
					displayResult.setText(R.string.validated); 
				} else {
					displayResult.setText(R.string.error_customer); 
				}

				((TextView)findViewById(R.id.ticket_info)).setText(
						"\nInfos ticket :\n\n" +
								"ID party : " + customerJson.getInt("idParty") +
								"\nID client : " + customerJson.getInt("idCustomer") +
								"\nCode secret : " + customerJson.getString("secretCode"));

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				displayResult.setText(R.string.error_checking);
			}

		} else {
			displayResult.setText(R.string.error_checking);
		}		
	}

	/**
	 * Action to perform upon a click on the login form
	 * 
	 * @param v the view where the click comes from
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public void performLogin(View v) {
		AsyncTask<String, Void, String> 
		callBack;   // result of the web service call
		CharSequence login;   // login from the textfield
		CharSequence pwd;     // password from the textfield
		String result;
		JSONObject userJson;  // login and password fields formatted into json
		ServiceCaller call;   // caller to call the web service

		login = ((TextView)findViewById(R.id.login)).getText();
		pwd = ((TextView)findViewById(R.id.pwd)).getText();
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

		// waiting for the result
		try {
			result = callBack.get();
			userJson = new JSONObject(result);
			System.out.println(userJson);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}

		// checking the login and perform appropriate action
		try {
			if(login.toString().equals("root") || 
					userJson != null && (userJson.getInt("id") != 0)) {
				// going to the next view
				//transitView(R.layout.scan);
				//transitView(R.layout.party_list);
				launchListParty(true);
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
