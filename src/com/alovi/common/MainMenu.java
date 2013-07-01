package com.alovi.common;

import com.alovi.R;
import com.alovi.activity.AboutActivity;
import com.alovi.activity.BaseActivity;
import com.alovi.activity.EventMenuActivity;
import com.alovi.activity.MainActivity;
import com.alovi.activity.MyCardActivity;
import com.alovi.controller.UserController;
import com.alovi.data.GlobalResource;
import com.alovi.data.GlobalVariables;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu {
	private Activity activity;
	private Button btnBack;
	private TextView txtMainTitle;
	//private TextView txtStoreName;
	private Button btnHome;
	private Button btnMycart;
	private Button btnMyWallet;
	private Button btnEvents;
	private Button btnCall;
	private ImageView imgLogo;

	// private ExpandableListView expandableList;
	// private ExpandableListAdapter arrayAdapter;

	private String TmpStoreCode = "";
	private Boolean ImageLogeType_Store = false;

	public MainMenu(Activity _context, Boolean hasBottomMenu, Boolean hasEvent) {
		this.activity = _context;

		btnBack = (Button) activity.findViewById(R.id.btn_common_back);
		txtMainTitle = (TextView) activity.findViewById(R.id.maintitle);
		imgLogo = (ImageView) activity.findViewById(R.id.imgCommonlogo);
		//txtStoreName = (TextView) activity.findViewById(R.id.txtStoreName);
		
		setButtonBack();
		if (hasEvent) {
			setImageLogo();
		}
		if (hasBottomMenu) {
			btnHome = (Button) activity.findViewById(R.id.btn_common_home);
			btnMycart = (Button) activity.findViewById(R.id.btn_common_mycart);
			btnMyWallet = (Button) activity.findViewById(R.id.btn_common_mywallet);
			btnEvents = (Button) activity.findViewById(R.id.btn_common_events);
			btnCall = (Button) activity.findViewById(R.id.btn_common_call);

			setButtonHome();
			setButtonMyCart();
			//setButtonMyWallet();
			//setButtonEvents();
			//setButtonCall();
		}
	}

	public MainMenu(Activity _context, Boolean hasBottomMenu) {
		this(_context, hasBottomMenu, true);
	}

	public void setButtonHomeStatus(boolean clicked) {
		if (clicked)
			btnHome.setBackgroundResource(R.drawable.common_home_bt_1);
		else
			btnHome.setBackgroundResource(R.drawable.common_home_bt);
	}

	public void setButtonMyCartStatus(boolean clicked) {
		if (clicked)
			btnMycart.setBackgroundResource(R.drawable.common_mycart_bt_1);
		else
			btnMycart.setBackgroundResource(R.drawable.common_mycart_bt);
	}

	public void setButtonMyWalletStatus(boolean clicked) {
		if (clicked)
			btnMyWallet.setBackgroundResource(R.drawable.common_mywallet_bt_1);
		else
			btnMyWallet.setBackgroundResource(R.drawable.common_mywallet_bt);
	}

	public void setButtonEventsStatus(boolean clicked) {
		if (clicked)
			btnEvents.setBackgroundResource(R.drawable.common_events_bt_1);
		else
			btnEvents.setBackgroundResource(R.drawable.common_events_bt);
	}

	public void setButtonCallStatus(boolean clicked) {
		if (clicked)
			btnCall.setBackgroundResource(R.drawable.common_call_bt_1);
		else
			btnCall.setBackgroundResource(R.drawable.common_call_bt);
	}

	public void setButtonBack() {
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				activity.finish();
			}
		});
	}

	public void setImageLogo() {
		imgLogo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = null;
				/*if (ImageLogeType_Store) {
					intent = new Intent(activity, StoreInfoActivity.class);
					intent.putExtra("storeCode", TmpStoreCode);
					activity.startActivity(intent);
				} else {*/
					intent = new Intent(activity, AboutActivity.class);
					activity.startActivity(intent);
				//}
			}
		});
	}

	public void setTextMainTitle(String text) {
		this.txtMainTitle.setText(text);
	}

	public void setLogoImage(Bitmap bm, String storeCode) {
		LayoutParams layout = this.imgLogo.getLayoutParams();
		layout.width = 118;
		layout.height = 35;

		this.imgLogo.setLayoutParams(layout);
		this.imgLogo.setImageBitmap(bm);

		TmpStoreCode = storeCode;
		ImageLogeType_Store = true;
		
		//String storeName = ((BaseActivity)this.activity).getStoreName();		
		//txtStoreName.setText(storeName);
	}

	public void setLogoImage() {
		//txtStoreName.setText("");
		this.imgLogo.setImageResource(R.drawable.common_logo);
	}

	public void setButtonHome() {
		btnHome.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this.activity, MainActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.startActivity(intent);

				activity.finish();
			}
		});
	}

	public void setButtonMyCart() {
		btnMycart.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalResource globalResource = GlobalResource.getInstance();
				String storeCode = globalResource.getStoreCode();

				if (storeCode == null || storeCode.isEmpty()) {
					String errorMsg = MainMenu.this.activity.getString(R.string.msg_store_info_not_exist).toString();
					Toast.makeText(MainMenu.this.activity, errorMsg, Toast.LENGTH_SHORT).show();

					return;
				}
				Intent intent = new Intent(MainMenu.this.activity, MyCardActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				MainMenu.this.activity.startActivity(intent);
				
				// close current activity
				activity.finish();
			}
		});
	}

	public void setButtonMyWallet() {
		btnMyWallet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(BaseActivity.UserName.equals(GlobalVariables.PHONE_NUMBER_NA))
				{
					((BaseActivity)MainMenu.this.activity).showDlgRegister();
					return;
				}
				//Intent intent = new Intent(MainMenu.this.activity,
				//		MyWalletSubmenuActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				//MainMenu.this.activity.startActivity(intent);
				
				// close current activity
				activity.finish();
				
				// expandableList.setAdapter(arrayAdapter);
			}
		});
	}

	public void setButtonEvents() {
		btnEvents.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainMenu.this.activity,
						EventMenuActivity.class);
				//intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				MainMenu.this.activity.startActivity(intent);
				
				// close current activity
				activity.finish();
			}
		});
	}

	public void setButtonCall() {
		btnCall.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				GlobalResource globalResource = GlobalResource.getInstance();

				String mobilePhone = globalResource.getUserName();
				String storeCode = globalResource.getStoreCode();
				String subCode = globalResource.getSubCode();

				if (storeCode == null || subCode == null) {
					String errorMsg = activity
							.getString(R.string.msg_store_info_not_exist);
					Toast.makeText(activity, errorMsg, Toast.LENGTH_SHORT).show();
					return;
				}
				String digitalCode = storeCode + subCode;
				UserController.callToPOS(mobilePhone, digitalCode);

				String msg = activity.getString(R.string.msg_call_to_pos);
				Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void setButtonHomeOn() {
		setButtonHomeStatus(true);
		setButtonMyCartStatus(false);
		setButtonMyWalletStatus(false);
		setButtonEventsStatus(false);
		setButtonCallStatus(false);
	}

	public void setButtonMycartOn() {
		setButtonHomeStatus(false);
		setButtonMyCartStatus(true);
		setButtonMyWalletStatus(false);
		setButtonEventsStatus(false);
		setButtonCallStatus(false);
	}
}