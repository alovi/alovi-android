package com.alovi.common;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.alovi.R;
import com.alovi.data.GlobalResource;
import com.alovi.data.GlobalVariables;
import com.alovi.data.UserData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;


public class Util {
	private Util() {
	}

	public static String getStorecodeFromTag(String tag) {
		if (tag == null || "".equals(tag) || tag.length() != 15) {
			return "";
		}

		return tag.substring(0, 12);
	}

	public static int strToInt(String str) {
		int result = 0;
		try {
			result = Integer.parseInt(str);
		} catch (NumberFormatException e) {
			result = 0;
		}

		return result;
	}

	public static boolean checkInternet(Activity activity) {
		ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		boolean isWifiConn = ni.isConnected();
		ni = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		boolean isMobileConn = ni.isConnected();
		if (isWifiConn || isMobileConn)	return true;
		return false;
	}

	public static void showDialog(Context context, String title, String msg) {
		AlertDialog.Builder bld = new AlertDialog.Builder(context);
		bld.setTitle(title);
		bld.setMessage(msg);
		bld.setIcon(R.drawable.icon);
		bld.setPositiveButton(R.string.text_yes, null);
		AlertDialog alert = bld.create();
		alert.show();
	}

	public static String getPhoneNumberFromSystem(Context context) {
		SharedPreferences settings = context.getSharedPreferences(GlobalVariables.PREFS_NAME, 0);
		String phoneNumber = settings.getString(GlobalVariables.USER_PHONE_NUMBER, "");

		if (phoneNumber == null || phoneNumber.isEmpty()) {
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			phoneNumber = telephonyManager.getLine1Number();

			// TODO Test only on emulator
			// phoneNumber = "84903666777";
			if (phoneNumber != null && !phoneNumber.isEmpty()) {
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(GlobalVariables.USER_PHONE_NUMBER, phoneNumber);

				// Commit the edits!
				editor.commit();
			} else {
				phoneNumber = GlobalVariables.PHONE_NUMBER_NA;
			}
		}

		return phoneNumber;
	}

	public static boolean getUserFromGlobalStore(Context context) {
		UserData user = new UserData();
		GlobalResource globalResource = GlobalResource.getInstance();
		//TODO read data user from StorageCard
		try{
			//user.response.user.userName = "tainguyenchi";
			//user.response.user.password = "T21nguyenchi";
			//user.response.user.FirstName = "Tai";
			//if(user.response.user.userName != ""){
				globalResource.setUser(user);
				return true;
			//}
		}catch(Exception ex){
			Log.e("ReadGlobalStore", "ex: " + ex.getMessage());
		}
		return false;
	}

	/*
	 * @return not empty: success
	 */
	public static VerifyStoreState validateDigitalCode(String digitalCode,
			String phoneNumber) {
		if (digitalCode.length() < 12) {
			return VerifyStoreState.STORE_INVALID;
		}
		String[] codes = getStoreSubCode(digitalCode);
		if (codes == null)
			return VerifyStoreState.STORE_INVALID;
		// Check store infomartion by server
		//String res = StoreController.checkStoreCodeAndSubCode(codes[0],
			//	codes[1], phoneNumber);
		//if (res.isEmpty())
			//return VerifyStoreState.STORE_INVALID;
		//if (res.equals("TABLE_OCCUPIED"))
			//return VerifyStoreState.OCCUPIED;
		return VerifyStoreState.SUCCESS;
	}

	public static String formatCurrency(double db) {
		String orderAmount = NumberFormat.getInstance().format(db);
		//DecimalFormat df = new DecimalFormat("#,###");
		//String orderAmount = df.format(db);
		return orderAmount;
	}

	public static boolean checkStoreExist(Context context) {
		GlobalResource globalResource = GlobalResource.getInstance();
		String storeCode = globalResource.getStoreCode();

		if (storeCode == null || storeCode.isEmpty()) {
			String errorMsg = context.getString(
					R.string.msg_store_info_not_exist).toString();
			Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();

			return false;
		}
		return true;
	}

	public static String[] getStoreSubCode(String digitalCode) {
		if (digitalCode == null || digitalCode.isEmpty())
			return null;
		
		if (digitalCode.length() > 16) {
			digitalCode = digitalCode.substring(digitalCode.length() - 16);
		}
		
		if (digitalCode.contains("-")) {
			String[] res = digitalCode.split("-");
			if (res.length == 2)
				return res;
		} else {
			if (digitalCode.length() > 12) {
				String[] res = new String[2];
				res[0] = digitalCode.substring(0, 12);
				res[1] = digitalCode.substring(12);
				return res;
			}
		}
		return null;
	}

	/**
	 * Alert and vibrate phone on state
	 * 
	 * @param orderState
	 *            : order status
	 */
	public static void alertOrderState(Context context, OrderState orderState) {
		Vibrator vibe = (Vibrator) context
				.getSystemService(Context.VIBRATOR_SERVICE);

		switch (orderState) {
		case Created:
			Toast.makeText(
					context,
					context.getString(R.string.msg_mycart_order_created)
							.toString(), Toast.LENGTH_SHORT).show();
			break;

		case Canceled:
			Toast.makeText(
					context,
					context.getString(R.string.msg_mycart_order_canceled)
							.toString(), Toast.LENGTH_SHORT).show();
			break;

		case Paid:
			Toast.makeText(
					context,
					context.getString(R.string.msg_mycart_order_paid)
							.toString(), Toast.LENGTH_SHORT).show();
			break;

		case Processing:
			break;

		case Requested:
			Toast.makeText(
					context,
					context.getString(R.string.msg_mycart_order_requested)
							.toString(), Toast.LENGTH_SHORT).show();
			vibe.vibrate(500);
			break;

		case Served:
			Toast.makeText(
					context,
					context.getString(R.string.msg_mycart_order_served)
							.toString(), Toast.LENGTH_SHORT).show();
			vibe.vibrate(500);
			break;

		default:
			break;
		}
	}
	public static String getCurrentTime()
	{
		String dateFormat="yyyy/MM/dd HH:mm";
		Calendar calendar=Calendar.getInstance();
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		return sdf.format(calendar.getTime()).trim();
	}
}
