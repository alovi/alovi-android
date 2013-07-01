package com.alovi.activity;


import com.alovi.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.PeopleColumns;
import android.provider.Contacts.PhonesColumns;
import android.widget.TextView;

@SuppressWarnings("deprecation")
public class ContactDetails extends Activity {
	
	TextView nameField = null;
	TextView phoneField = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactdetails);
        
        nameField = (TextView) findViewById(R.id.contact_name);
        phoneField = (TextView) findViewById(R.id.contact_phone);
    }

	@Override
	protected void onStart() {
		super.onStart();
		
		Cursor cursor = managedQuery(getIntent().getData(), null, null, null, null);
		cursor.moveToFirst();
		
		nameField.setText(cursor.getString(cursor.getColumnIndex(PeopleColumns.NAME)));
		phoneField.setText(cursor.getString(cursor.getColumnIndex(PhonesColumns.NUMBER)));
	}
}