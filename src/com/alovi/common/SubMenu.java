package com.alovi.common;

import java.util.ArrayList;
import java.util.List;

import com.alovi.R;
import com.alovi.data.GlobalVariables;

import android.app.Activity;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public abstract class SubMenu {
	protected Gallery gallery;
	protected Integer[] pics;
	protected List<ImageView> imageViews;
	protected Activity activity;

	public SubMenu(Activity activity, Gallery gallery) {
		this.activity = activity;
		this.gallery = gallery;
		this.gallery.setSpacing(0);
		setControls();
	}

	public abstract void loadMenuImage();
	public abstract void handleClickItem(AdapterView<?> arg0, View arg1,int index, long id);
	public void setControls() {
		loadMenuImage();
		createImageList();
		
		gallery.setAdapter(new ImageMenuAdapter(imageViews));
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				imageViews.get(arg2).setAnimation(AnimationUtils.loadAnimation(activity, R.anim.item_grow));
				for (int i = 0; i < imageViews.size(); i++) {
					if (i != arg2) {
						imageViews.get(i).setAnimation(AnimationUtils.loadAnimation(activity, R.anim.item_shrink));
					}
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {				
				handleClickItem(arg0, arg1, arg2, arg3);
				
				// reset sub menus
				if(GlobalVariables.TYPE_MENU == 0){
					List<ImageView> list = new ArrayList<ImageView>();
					gallery.setAdapter(new ImageMenuAdapter(list));
					gallery.setBackgroundResource(R.drawable.submenu_bg_ads2);
				}
			}
		});
		// set selection
		int position = pics.length;
		if (position % 2 == 0) {
			position = pics.length / 2 - 1;
		}
		else {
			position = pics.length / 2; 			
		}
		gallery.setSelection(position, true);
	}

	public void createImageList() {
		loadMenuImage();
		imageViews = new ArrayList<ImageView>();
		Gallery.LayoutParams layoutParam = new Gallery.LayoutParams(154, 154);
		
		for (int i = 0; i < pics.length; i++) {
			Integer pic = pics[i];
			ImageView img = new ImageView(this.activity);

			img.setId(pic);
			img.setImageResource(pic);
			img.setLayoutParams(layoutParam);
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			imageViews.add(img);
		}
	}
}