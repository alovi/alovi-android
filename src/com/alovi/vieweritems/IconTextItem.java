package com.alovi.vieweritems;

import android.graphics.Bitmap;

public class IconTextItem {

	private Bitmap bitmap;
	private String[] mData;
	private boolean mSelectable = true;
	
	public IconTextItem(Bitmap icon, String[] data) {
		bitmap = icon;
		mData=data;
	}

	/**
	 * True if this item is selectable
	 */
	public boolean isSelectable() {
		return mSelectable;
	}

	/**
	 * Set selectable flag
	 */
	public void setSelectable(boolean selectable) {
		mSelectable = selectable;
	}

	/**
	 * Get data array
	 * 
	 * @return
	 */
	public String[] getData() {
		return mData;
	}
	
	public String getData(int index){
		if(mData==null||mData.length<=index)
			return "";
		return mData[index];
	}

	/**
	 * Set data array
	 * 
	 * @param obj
	 */
	public void setData(String[] obj) {
		mData = obj;

	//	adapter.notifyDataSetChanged();
	}
	
	public void setData(int index,String data)
	{
		if(index>=mData.length)
			return;
		mData[index]=data;
	}
	/**
	 * Set icon
	 * 
	 * @param icon
	 */
	public void setIcon(Bitmap icon) {
		bitmap = icon;
	}

	/**
	 * Get icon
	 * 
	 * @return
	 */
	public Bitmap getIcon() {
		return bitmap;
	}
}