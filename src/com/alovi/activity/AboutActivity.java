package com.alovi.activity;

import com.alovi.R;
import com.alovi.common.MainMenu;

import android.os.Bundle;

public class AboutActivity extends BaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.about_alovi);
		
		MainMenu menu = new MainMenu(this, true, false);
		menu.setTextMainTitle(getString(R.string.text_alovi_about).toString());
		menu.setLogoImage();
	}
}