package com.alovi.vieweritems;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class OrderIconTextListAdapter extends IconTextListAdapter {
	
	public OrderIconTextListAdapter(Context context) {
		super(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IconTextView itemView;
		if (convertView == null) {
			itemView = new OrderIconTextView(mContext, mItems.get(position));
			itemView.setPadding(0,0,0,0);
		} else {
			itemView = (OrderIconTextView) convertView;
			itemView.setIcon(mItems.get(position).getIcon());
			
			itemView.setText(0, mItems.get(position).getData(1));
		}
		return itemView;
	}
}