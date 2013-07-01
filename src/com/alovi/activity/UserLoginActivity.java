package com.alovi.activity;

import java.util.ArrayList;

import com.alovi.R;
import com.alovi.common.Config;
import com.alovi.common.ImageMenuAdapter;
import com.alovi.common.MessageTypes;
import com.alovi.common.Util;
import com.alovi.controller.UserController;
import com.alovi.data.GlobalResource;
import com.alovi.data.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class UserLoginActivity extends BaseActivity {
	
	private TextView txtTitle;
	private ImageView imgLogo;
	private Button btnBack, btnLogin, btnRegister;
	private EditText txtUserName, txtPassword;
	private ArrayList<ImageView> imageViews;
	private Integer[] pics;
	private Gallery gallery;
	
	
	public UserLoginActivity() { }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Test
		//GlobalResource.getInstance().setUserLogin(1);
		//Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
		//UserLoginActivity.this.startActivity(intent);
		//UserLoginActivity.this.finish();
		// EndTest
		
		setContentView(R.layout.user_login);

		txtTitle = (TextView) findViewById(R.id.maintitle);
		txtTitle.setText(getString(R.string.user_login_title));
		imgLogo = (ImageView) findViewById(R.id.imgCommonlogo);
		imgLogo.setImageResource(R.drawable.common_logo);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtPassword.setHint(getText(R.string.user_register_password_en));
		
		TableLayout tableLayout1 = (TableLayout) findViewById(R.id.tableLayout1);
		int asPaddingTop = 70*GlobalResource.getInstance().getScreenHeigth()/480;
		tableLayout1.setPadding(0, asPaddingTop, 0, 0);
		
		pics = new Integer[] { R.drawable.login_ads01, R.drawable.login_ads02};
		imageViews = new ArrayList<ImageView>();
		int adsHeight = (int)Math.round(GlobalResource.getInstance().getScreenWidth()/2.3);
		
		Gallery.LayoutParams layoutParam = new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, adsHeight);
		
		for (int i = 0; i < pics.length; i++) {
			Integer pic = pics[i];
			ImageView img = new ImageView(this);
			
			img.setId(pic);
			img.setImageResource(pic);
			img.setLayoutParams(layoutParam);
			img.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
			imageViews.add(img);			
		}
		gallery = (Gallery) this.findViewById(R.id.Gallery01);
		gallery.setBackgroundResource(R.drawable.submenu_bg);
		gallery.setSpacing(0);
		gallery.setAdapter(new ImageMenuAdapter(imageViews));
		
		btnBack = (Button) findViewById(R.id.btn_common_back);
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalResource.getInstance().setUserLogin(2);
				finish();
			}
		});
		txtUserName = (EditText) findViewById(R.id.txtUserName);
		txtUserName.setHint(getText(R.string.user_register_username_en));
		txtUserName.addTextChangedListener(new TextWatcher(){
	        @Override
			public void afterTextChanged(Editable s) {
	        	
	        }
	        
	        @Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after){
	        	
	        }
	        
	        @Override
			public void onTextChanged(CharSequence s, int start, int before, int count){
	        	
	        }
	    });
		btnLogin = (Button) findViewById(R.id.btn_Login);
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				txtUserName.setText("tainguyenchi");
				txtPassword.setText("T21nguyenchi");
				final String userName = txtUserName.getText().toString();
				final String password = txtPassword.getText().toString();
				
				if (userName.length() == 0 || password.length() == 0) {
					toast(getString(R.string.msg_userreg_inputallfield));
					return ;
				}
				try {
					showDialog(PROGRESS_DIALOG);
					if (Util.checkInternet(UserLoginActivity.this)) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								UserData res=doLogin(userName, password);
								Message msg=handle.obtainMessage();
								msg.obj=res;
								handle.sendMessage(msg);
								if(res != null && GlobalResource.getInstance().getUserLogin()==0){
									GlobalResource.getInstance().setUserLogin(1);
									Intent intent = new Intent(UserLoginActivity.this, MainActivity.class);
									UserLoginActivity.this.startActivity(intent);
									UserLoginActivity.this.finish();
								}else if(res != null && GlobalResource.getInstance().getUserLogin()==1){
									UserLoginActivity.this.finish();
								}
							}
						}).start();
					} else {
						messageBox(MessageTypes.Error, getString(R.string.text_check_net_fail).toString());
						dismissDialog(PROGRESS_DIALOG);
					}
				} catch (Exception ex) {
					messageBox(MessageTypes.Error, ex.getMessage().toString());
					dismissDialog(PROGRESS_DIALOG);
				}
			}
		});
		btnRegister = (Button) findViewById(R.id.btn_register);
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(UserLoginActivity.this, UserRegisterActivity.class);
				startActivity(intent);
			}
		});
		
	}
	
	private UserData doLogin(String userName, String password) {
		if (userName.length() == 0 || password.length() == 0) {
			toast(getString(R.string.msg_userreg_inputallfield));
			return null;
		}
		UserData userData = UserController.getUser(userName, password);
		if (userData == null)
			return null;
		return userData;
	}
	
	@Override
	public void onBackPressed() {
		GlobalResource.getInstance().setUserLogin(2);
		this.finish();
	}
	
	final Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissDialog(PROGRESS_DIALOG);
			UserData user=(UserData)msg.obj;
			if (user!=null){
				toast("success!" );
			}else toast("lost!");
		}
	};
}