package com.alovi.activity;

import java.util.List;

import com.alovi.R;
import com.alovi.common.MessageTypes;
import com.alovi.common.Util;
import com.alovi.common.VerifyStoreState;
import com.alovi.data.GlobalResource;
import com.alovi.data.GlobalVariables;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class BaseActivity extends Activity {

	public static String UserName;
	ProgressDialog progressDialog;
	final public int PROGRESS_DIALOG = 0;
	protected String _scannedTagText = "";
	protected LinearLayout mTagContent;

	protected PendingIntent mPendingIntent;
	protected IntentFilter[] mFilters;
	protected String[][] mTechLists;

	protected void initializeBase() {
		mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
				getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		
		WindowManager w = getWindowManager();

		Display d = w.getDefaultDisplay(); 
		GlobalResource globalResource = GlobalResource.getInstance();
		globalResource.setScreenWidth(d.getWidth()); 
		globalResource.setScreenHeight(d.getHeight());
	}

	public String getStoreCode() {
		return "";
	}

	protected String getSubCode() {
		return "";
	}

	public String getStoreName() {
		return "";
	}

	protected Bitmap getStoreLogo() {
		return null;
	}

	protected void toast(String strContent) {
		if (strContent == null)
			strContent = "NULL";
		Toast.makeText(this, strContent.toString(), Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initializeBase();
		// remove title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		// Remove notification bar
		// this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}

	protected void setDigitalCode(String storeCode, String subCode) {

		try {

			// Only reload Store Data and logo when change store or this
			// information does not exists on Global Resource
			// Get store bitmap
			Bitmap bitmap = null;
			if (bitmap == null) {
				bitmap = BitmapFactory.decodeResource(getResources(),
						R.drawable.blank);
			}
			// Store Store Code in Shared Prefrencense for restore the current
			// Order when application starts up.
		} catch (Exception e) {
			Log.e("setDigitalCode", e.getMessage());
		}

	}

	public void processKill() {
		// #1. first check api level.
		int sdkVersion = Integer.parseInt(Build.VERSION.SDK);
		if (sdkVersion < 8) {
			// #2. if we can use restartPackage method, just use it.
			ActivityManager am = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
			am.restartPackage(getPackageName());
		} else {
			// #3. else, we should use killBackgroundProcesses method.
			new Thread(new Runnable() {
				@Override
				public void run() {
					ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
					String name = getApplicationInfo().processName;
					// pooling the current application process importance
					// information.
					while (true) {
						List<RunningAppProcessInfo> list = am
								.getRunningAppProcesses();
						for (RunningAppProcessInfo i : list) {
							if (i.processName.equals(name) == true) {
								// #4. kill the process,
								// only if current application importance is
								// less than IMPORTANCE_BACKGROUND
								if (i.importance >= RunningAppProcessInfo.IMPORTANCE_BACKGROUND)
									am.restartPackage(getPackageName()); // simple
																			// wrapper
																			// of
																			// killBackgrounProcess
								else
									Thread.yield();
								break;
							}
						}
					}
				}
			}, "Process Killer").start();
		}
	}

	// MessageBox
	public void messageBox(MessageTypes messageType, String strContent) {
		Intent intent = new Intent(this, MessageBoxActivity.class);
		intent.putExtra("Message", strContent);
		this.startActivityForResult(intent, MessageBoxActivity.REQUEST_MSGBOX);
	}

	// MessageBox
	public void messageDialog(String strTitle, String strContent) {
		AlertDialog.Builder bld = new AlertDialog.Builder(this);
		bld.setTitle(getString(R.string.txt_dialog_title).toString());
		bld.setMessage(strContent);
		bld.setIcon(R.drawable.icon);
		bld.setPositiveButton("OK", null);
		bld.show();
	}

	public void showDlgRegister() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.txt_request_register))
				.setTitle(R.string.txt_warning)
				.setCancelable(false)
				.setPositiveButton(getString(R.string.text_yes),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent(BaseActivity.this, UserRegisterActivity.class);
								intent.putExtra(GlobalVariables.ACTION_REGISTER_DOPAYMENT,true);
								startActivity(intent);
							}
						})
				.setNegativeButton(getString(R.string.text_no),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	public void hideKeyboard(EditText textBox) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(textBox.getWindowToken(), 0);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case (PROGRESS_DIALOG):
			progressDialog = new ProgressDialog(this);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setMessage(getString(R.string.msg_processing));
			return progressDialog;
		}
		return null;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		this.finish();
	}

	@Override
	protected void onResume() {
		super.onResume();
		//if (mAdapter != null)
			//mAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters, mTechLists);
	}

	@Override
	public void onPause() {
		super.onPause();
		//if (mAdapter != null)
			//mAdapter.disableForegroundDispatch(this);
	}

	protected VerifyStoreState validateDigitalCode() {
		Log.d("SplashActivity", "validateDigitalCode:" + _scannedTagText);
		VerifyStoreState result = Util.validateDigitalCode(_scannedTagText, UserName);
		if (result == VerifyStoreState.SUCCESS) {
			String[] codes = Util.getStoreSubCode(_scannedTagText);
			if (codes != null)
				setDigitalCode(codes[0], codes[1]);
		}
		return result;
	}

	@Override
	public void onNewIntent(Intent intent) {
		
	}
}