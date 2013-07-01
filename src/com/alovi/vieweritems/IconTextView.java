package com.alovi.vieweritems;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class IconTextView extends LinearLayout {

	public IconTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Icon
	 */
	protected ImageView mIcon;
	protected TextView mText01;
	protected TextView mText02;
	protected TextView mText03;
	protected TextView mText04;
	protected TextView mText05;

	protected IconTextItem aItem;

	
	
	/**
	 * set Text
	 * 
	 * @param index
	 * @param data
	 */
	public void setText(int index, String data) {
		if (index == 0) {
			mText01.setText(data);
		}
//		else if (index == 1) {
//			mText02.setText(data);
//		} else if (index == 2) {
//			mText03.setText(data);
//		} else if (index == 3) {
//			mText04.setText(data);
//		} else if (index == 4) {
//			mText05.setText(data);
//		} else {
//			// throw new IllegalArgumentException();
//		}
	}

	public IconTextItem getIconTextItem() {
		return aItem;
	}

	/**
	 * set Icon
	 * 
	 * @param icon
	 */
	public void setIcon(Bitmap icon) {
		mIcon.setImageBitmap(icon);
	}

}
