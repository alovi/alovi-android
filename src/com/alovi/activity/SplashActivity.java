package com.alovi.activity;

import java.lang.Thread.UncaughtExceptionHandler;

import com.alovi.R;
import com.alovi.common.Config;
import com.alovi.common.MessageTypes;
import com.alovi.common.Util;
import com.alovi.webservice.APIServiceVariables;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashActivity extends BaseActivity {
	boolean checkLoginResult = false;
	boolean checkRegisterResult = false;
	boolean checkInternetResult = false;

	protected boolean _active = true;
	protected int _splashTime = 5000; //time to display the splash screen in ms(5000ms = 5 sec)

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			setContentView(R.layout.splash);
			
			initControls();
			SplashHandler mHandler = new SplashHandler();

			Message msg = new Message();
			// Assign a unique code to the message. Later, this code will be used to identify the message in Handler class.
			msg.what = 0;
			// Send the message with a delay.
			mHandler.sendMessageDelayed(msg, _splashTime);
		}catch(Exception e){
			messageBox(MessageTypes.Info, e.getMessage());
		}
	}

	@Override
	public void onNewIntent(Intent intent) {
		setIntent(intent);
	}

	// return true and continue app. otherwise exit
	private void initControls() {
		// set exception handler
		GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler(Thread.getDefaultUncaughtExceptionHandler());
		Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);

		mTagContent = (LinearLayout) findViewById(R.id.list);
		// version
		TextView appVersionName = (TextView) findViewById(R.id.appVersionName);
		try {
			String packageName = getPackageName();
			PackageInfo pi = getPackageManager().getPackageInfo(packageName, 0);
			appVersionName.setText(pi.versionName);
		} catch (NameNotFoundException e) {}
	}

	private void checkAppReadyState() {
		// thread for displaying the SplashScreen
		Thread splashTread = new Thread() {
			@Override
			public void run() {
				try {
					if(Config.Read()==null) Config.Write(APIServiceVariables.getInstance().HOST(), APIServiceVariables.getInstance().SERVICE_CONTEXT());
					//PhoneNumber = Util.getPhoneNumberFromSystem(SplashActivity.this);
					//checkLoginResult = Util.getUserFromGlobalStore(SplashActivity.this);
					if(!checkLoginResult) {
						checkInternetResult = Util.checkInternet(SplashActivity.this);
						if (!checkInternetResult) {
							messageBox(MessageTypes.Info, getString(R.string.text_check_net_fail).toString());
							return;
						}
						Intent intent = new Intent(SplashActivity.this, UserLoginActivity.class);
						startActivity(intent);
					} else {
						// Now we have user loged - store it on GlobalResourse for use after
						Intent intent = new Intent(SplashActivity.this, MainActivity.class);
						startActivity(intent);
					}
				} catch (Exception ex) {
					messageBox(MessageTypes.Error, getString(R.string.msg_connect_server_fail) + ": " + ex.getMessage());
				} finally {
					finish();
				}
			}
		};
		splashTread.start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == MessageBoxActivity.REQUEST_MSGBOX && resultCode == Activity.RESULT_OK) {
			this.finish();
		}
	}

	// Handler class implementation to handle the message
	private class SplashHandler extends Handler {
		// This method is used to handle received messages
		@Override
		public void handleMessage(Message msg) {
			// switch to identify the message by its code
			switch (msg.what) {
			default:
			case 0:
				super.handleMessage(msg);
				checkAppReadyState();
			}
		}
	}

	private class GlobalExceptionHandler implements UncaughtExceptionHandler {
		// private UncaughtExceptionHandler oldHandler;
		GlobalExceptionHandler(UncaughtExceptionHandler oldHandler) {
			//this.oldHandler = oldHandler;
		}

		@Override
		public void uncaughtException(Thread thread, Throwable throwable) {
			messageBox(MessageTypes.Info, throwable.getMessage());
		}
	}
}