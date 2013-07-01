package com.alovi.activity;

import android.app.ListActivity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.Contacts.People;
import android.provider.Contacts.PeopleColumns;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

@SuppressWarnings("deprecation")
public class ContactActivity extends ListActivity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);
        
        String[] projection = new String[] { BaseColumns._ID, PeopleColumns.NAME };
        Cursor cursor = managedQuery(People.CONTENT_URI, projection, null, null, PeopleColumns.NAME + " ASC");
        
        ListAdapter adapter = new SimpleCursorAdapter(
        	this,
        	android.R.layout.two_line_list_item,
        	cursor,
        	new String[] { BaseColumns._ID, PeopleColumns.NAME },
        	new int[] { android.R.id.text1, android.R.id.text2 }
        );
        setListAdapter(adapter);
        getListView().setOnItemClickListener((OnItemClickListener) this);
    }

	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		Uri contactUri = ContentUris.withAppendedId(People.CONTENT_URI, id);
		Intent intent = new Intent(this, ContactDetails.class);
		intent.setData(contactUri);
		startActivity(intent);
		
	}
}