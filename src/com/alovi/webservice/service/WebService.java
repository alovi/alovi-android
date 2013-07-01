package com.alovi.webservice.service;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;

import com.alovi.activity.MainActivity;
import com.alovi.R;
import com.alovi.wakeful.WakefulIntentService;
import com.alovi.webservice.RequestMethod;
import com.alovi.webservice.RestClient;


public class WebService extends WakefulIntentService implements Runnable {

	public static final String EXTRAS_RESPONSE_MESSAGE = "tylersmith.webservice.response_message";
	public static final String EXTRAS_SUCCESS = "tylersmith.webservice.success";

	public static final String WEB_INTENT_FILTER = "tylersmith.webservice.intent.filter";
	private final IBinder binder = new WebBinder();

	//private Handler handler = new Handler();
	protected Context context;


	public WebService() {
		super("UpdateService");
	}

	@Override
	protected void doWakefulWork(Intent intent) {
		context = getApplicationContext();
		//preventing this from running unless specifically called from within the activity or through the alarm manager
		if(!intent.getBooleanExtra("fromApplication", false)){
			new Thread(this).start();
			//alertWarning(1);
		}
	}

	@Override
	public void run() {
		String twitterUrl = 
				"http://moretarget.com/index.php/moretargetads/getcatlist";
				//"https://alovi.com.vn/index.php/api/user/info";
				//"http://search.twitter.com/search.json";
				//"http://www.cheesejedi.com/rest_services/get_big_cheese.php?puzzle=1";
		RestClient client = new RestClient(twitterUrl);
		//client.addParam("q", "android");
		//client.addBasicAuthentication("tainguyenchi", "T21nguyenchi");
		try {
			client.execute(RequestMethod.GET);
			if(client.getResponseCode() == 200) {
				alertWarning(1);
				//Successfully connected
				JSONObject jObj = new JSONObject(client.getResponse());
				JSONArray jResults = jObj.getJSONArray("results");

				SharedPreferences.Editor editPrefs =
	                PreferenceManager.getDefaultSharedPreferences(context).edit();
	            editPrefs.putString(MainActivity.PREFS_DATA, jResults.toString());
	            editPrefs.commit();
	            broadCast(true, "Success");

			} else {
				alertWarning(0);
				//error connecting to server, lets just return an error
				broadCast(false, "Error Connecting");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}
	
	public void alertWarning(int success){
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if(success == 1)
			builder.setMessage(getString(R.string.success))
			.setTitle(R.string.message)
			.setCancelable(false)
			.setNeutralButton(getString(R.string.ok),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
		else
			builder.setMessage(getString(R.string.fail))
			.setTitle(R.string.message)
			.setCancelable(false)
			.setNeutralButton(getString(R.string.ok),
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
		AlertDialog alert = builder.create();
		alert.show();
	}

	private void broadCast(boolean success, String message) {
		Intent intent = new Intent();
		intent.putExtra(EXTRAS_SUCCESS, success);
		intent.putExtra(EXTRAS_RESPONSE_MESSAGE, message);
		intent.setAction(WEB_INTENT_FILTER);
		sendBroadcast(intent);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	public class WebBinder extends Binder {
		public WebService getService() {
			return WebService.this;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
