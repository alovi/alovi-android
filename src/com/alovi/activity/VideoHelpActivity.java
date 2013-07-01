package com.alovi.activity;

import com.alovi.R;
import com.alovi.common.MainMenu;

import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoHelpActivity extends BaseActivity {

	private VideoView preview;
	private MainMenu menu;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.video_help);
		menu = new MainMenu(this, false);
		menu.setTextMainTitle(getString(R.string.text_help_title_menu));
		preview = (VideoView) findViewById(R.id.video_helpid);
		preview.setVideoURI(Uri.parse("android.resource://com.alovi/" + R.raw.sample_video));
		MediaController mediaControler = new MediaController(this);
		preview.setMediaController(mediaControler);
		mediaControler.setAnchorView(preview);
		preview.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		preview.stopPlayback();
	}
}