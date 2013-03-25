package com.example.scanner;

import java.util.ArrayList;

import org.json.JSONObject;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class PartyList extends ListView {
	
	protected EditText searchBar;
	
	protected ArrayAdapter<String> parties;
	
	String[] myList = new String[] {"Hello","World","Foo","Bar"};  

	public PartyList(Context context) {
		super(context);
		searchBar = new EditText(context);
		parties = new ArrayAdapter<String>(context, R.layout.edit_text, myList);
		addHeaderView(searchBar);
		setAdapter(parties);
		fill(null);
	}
	
	public void fill(JSONObject parties) {

	}
	
}
