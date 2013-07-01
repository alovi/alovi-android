package com.alovi.vieweritems;

import com.alovi.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;


public class StoreIconTextView extends IconTextView{
	public StoreIconTextView(Context context, IconTextItem aItem) {
		super(context);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.store, this, true);
		// Set Icon
		mIcon = (ImageView) findViewById(R.id.storeIcon);
		mIcon.setImageBitmap(aItem.getIcon());

		// Set Text 01
		mText01 = (TextView) findViewById(R.id.storedata1);

		mText01.setText(aItem.getData(1));
		//createControls(context);
		this.aItem = aItem;
	}
}
