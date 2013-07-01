package com.alovi.common;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageMenuAdapter extends BaseAdapter {
	private List<ImageView> images;
	public ImageMenuAdapter(List<ImageView>views) {
		images=views;
	}
	
	@Override
	public int getCount() {
		return images.size();
	}

	@Override
	public Object getItem(int position) {
		if(position<images.size())
			return images.get(position);
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public void removeAllImages() {
		images.clear();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return images.get(position);
	}
}