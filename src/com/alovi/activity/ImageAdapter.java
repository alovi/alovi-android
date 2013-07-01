package com.alovi.activity;

import com.alovi.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	/** The parent context */
	private Context myContext;
	// Put some images to project-folder: /res/drawable/
	// format: jpg, gif, png, bmp, ...
	private int[] myImageIds = { R.drawable.login_ads01, R.drawable.login_ads02 };

	/** Simple Constructor saving the 'parent' context. */
	public ImageAdapter(Context content) {
		this.myContext = content;
		
        /*TypedArray a = c.obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = a.getResourceId(
        		R.styleable.HelloGallery_android_galleryItemBackground, 0);
        a.recycle();*/
	}

	// inherited abstract methods - must be implemented
	// Returns count of images, and individual IDs
	@Override
	public int getCount() {
		return this.myImageIds.length;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	// Returns a new ImageView to be displayed,
	@Override
	public View getView(int position, View convertView, 
			ViewGroup parent) {

		// Get a View to display image data 					
		ImageView iv = new ImageView(this.myContext);
		iv.setImageResource(this.myImageIds[position]);

		// Image should be scaled somehow
		//iv.setScaleType(ImageView.ScaleType.CENTER);
		//iv.setScaleType(ImageView.ScaleType.CENTER_CROP);			
		iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
		//iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
		//iv.setScaleType(ImageView.ScaleType.FIT_XY);
		//iv.setScaleType(ImageView.ScaleType.FIT_END);
		
		// Set the Width & Height of the individual images
		iv.setLayoutParams(new Gallery.LayoutParams(400, 175));
		//iv.setBackgroundResource(R.drawable.vietnamobile);

		return iv;
	}
}// ImageAdapter