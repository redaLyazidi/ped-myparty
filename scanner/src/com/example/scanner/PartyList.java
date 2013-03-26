package com.example.scanner;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class PartyList extends ListView {

	/* when can we start to search for parties */
	private final static int LIMIT_RESEARCH = 3;

	protected EditText searchBar;

	protected ArrayAdapter<String> parties;

	String[] partyList;
	
	int[] partyIds;

	ArrayList<String> resultParty;
	
	ArrayList<Integer> resultId;

	public PartyList(Context context) {
		super(context);
		init(context);
	}

	public PartyList(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PartyList(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	protected void init(Context context) {
		searchBar = new EditText(context);
		parties = new ArrayAdapter<String>(context, R.layout.edit_text);
		addHeaderView(searchBar);
		setAdapter(parties);
		setOnItemClickListener(new MyListener());
		searchBar.addTextChangedListener(new TextListener());

	}

	public void fill(JSONArray parties) {
		final int LENGTH = parties.length();
		partyList = new String[LENGTH];
		partyIds = new int[LENGTH];
		for(int i=0; i<parties.length(); i++) {
			try {
				partyList[i] = parties.getJSONObject(i).getString("summary");
				partyIds[i] = parties.getJSONObject(i).getInt("id");;
			} catch (JSONException e) {
				e.printStackTrace();
			}			
		}
		this.parties = new ArrayAdapter<String>(getContext(), R.layout.edit_text, partyList);
		setAdapter(this.parties);
	}

	/**
	 * Search for items containing the string in
	 * the searchbar and adapt the list consequently.
	 * 
	 * @param query
	 */
	public void search(String query) {
		String queryLower = query.toLowerCase();
		resultParty = new ArrayList<String>();
		resultId = new ArrayList<Integer>();
		
		if(queryLower.length() >= LIMIT_RESEARCH) {
			for(int i=0; i<partyList.length; i++) {
				String partyLower = partyList[i].toLowerCase();
				if(partyLower.contains(queryLower)) {
					resultParty.add(partyList[i]);
					resultId.add(partyIds[i]);
				}
			}
			setAdapter(new ArrayAdapter<String>(getContext(), R.layout.edit_text, 
					resultParty));
		} else {
			setAdapter(parties);
		}


	}

	protected class MyListener implements OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> ad, View v, int position,
				long rowId) {
			LoginActivity activity = ((LoginActivity)getContext());
			activity.transitView(R.layout.scan);
			activity.setSummaryParty(((TextView)v).getText().toString());
			activity.setIdParty(partyIds[position]);	
			((TextView)activity.findViewById(R.id.summery_party)).setText(
					                   activity.getSummaryParty() + "\n");
		}	
	}

	protected class TextListener implements TextWatcher {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			System.out.println("yo" + count + "s:"+s+"start"+start+"bef"+before);
			search(s.toString());

		}

	}



}
