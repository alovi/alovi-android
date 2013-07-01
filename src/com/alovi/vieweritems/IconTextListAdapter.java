package com.alovi.vieweritems;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class IconTextListAdapter extends BaseAdapter {

	protected Context mContext;
	protected List<IconTextItem> mItems = new ArrayList<IconTextItem>();

	public IconTextListAdapter(Context context) {
		mContext = context;
	}

	public void addItem(IconTextItem it) {
		mItems.add(it);
	}

	public void removeAllItems() {
		mItems.clear();
	}
	
	public void removeItem(int index){
		if(index>=mItems.size())
			return;
		mItems.remove(index);
	}
	
	public void removeItem(Object obj){
		mItems.remove(obj);
	}
	
	public void setListItems(List<IconTextItem> lit) {
		mItems = lit;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public Object getItem(int position) {
		return mItems.get(position);
	}

	public boolean areAllItemsSelectable() {
		return false;
	}

	public boolean isSelectable(int position) {
		try {
			return mItems.get(position).isSelectable();
		} catch (IndexOutOfBoundsException ex) {
			return false;
		}
	}
	
	public List<IconTextItem>getItems(){
		return this.mItems;
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		IconTextView itemView;

		if (convertView == null) {
			;//itemView = new CategoryIconTextView(mContext, mItems.get(position));
		} else {
			itemView = (IconTextView) convertView;
			itemView.setIcon(mItems.get(position).getIcon());
		}
		return null;// itemView;
	}
}