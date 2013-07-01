package com.alovi.vieweritems;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class StoreIconTextListAdaper extends IconTextListAdapter{

	public StoreIconTextListAdaper(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IconTextView itemView;

		if (convertView == null) {
			itemView = new StoreIconTextView(mContext, mItems.get(position));
		} else {
			itemView = (StoreIconTextView) convertView;
			itemView.setIcon(mItems.get(position).getIcon());

			itemView.setText(0,mItems.get(position).getData(1));
//			itemView.setText(1, mItems.get(position).getData(1));
//			itemView.setText(2, mItems.get(position).getData(2));
//			itemView.setText(3, mItems.get(position).getData(3));
//			itemView.setText(4, mItems.get(position).getData(4));
//			itemView.setText(5, mItems.get(position).getData(5));
		}

		return itemView;
	}
}
