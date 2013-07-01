package com.alovi.activity;

import com.alovi.R;
import com.alovi.common.MainMenuTypes;
import com.alovi.common.SubMenuAccount;
import com.alovi.common.SubMenuOrder;
import com.alovi.common.SubMenuWallet;
import com.alovi.data.GlobalResource;
import com.alovi.data.GlobalVariables;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.TableRow;

public class MainActivity extends BaseActivity {

	public final static String PREFS_DATA = "data";
	private Button btnOrder, btnPayment, btnMywallet, btnMyevent, btnAccount, btnHelp;
	private Gallery gallery;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String isExit = getIntent().getStringExtra("isexit");
		// this code is used to exit app when user resigns
		if (isExit != null && !isExit.isEmpty()) {
			finish();
		}
		initGlobalResource();
        setContentView(R.layout.main);
        initControls();
        TableRow tableRow2 = (TableRow) this.findViewById(R.id.tableRow2);
        tableRow2.setPadding(0, 0, 0, (GlobalResource.getInstance().getScreenHeigth()*2/GlobalResource.getInstance().getScreenHeigthDefault()));
    }
    
	private void initGlobalResource() {
		GlobalResource globalResource = GlobalResource.getInstance();
		SharedPreferences settings = this.getSharedPreferences(GlobalVariables.PREFS_NAME, 0);
		String authCode = settings.getString(GlobalVariables.USER_AUTHENTICATION_CODE, "");

		globalResource.setAuthCode(authCode);
	}
	
	private void initControls() {
		// Set screen group sub menu
		gallery = (Gallery) this.findViewById(R.id.Gallery01);
		gallery.setBackgroundResource(R.drawable.submenu_bg_ads2);

		// 
		btnOrder = (Button) findViewById(R.id.btnOrder);
		btnOrder.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createSubMenus(MainMenuTypes.Order);
			}
		});

		// Payment
		btnPayment = (Button) findViewById(R.id.btnPaymentInfo);
		btnPayment.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createSubMenus(MainMenuTypes.Payment);
			}
		});

		// Wallet info
		btnMywallet = (Button) findViewById(R.id.btnMywallet);
		btnMywallet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createSubMenus(MainMenuTypes.Wallet);
			}
		});

		this.btnMyevent = (Button) this.findViewById(R.id.btnMyevent);
		this.btnMyevent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createSubMenus(MainMenuTypes.Events);
			}
		});

		btnAccount = (Button) findViewById(R.id.btnAccount);
		btnAccount.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createSubMenus(MainMenuTypes.Account);
			}
		});

		this.btnHelp = (Button) this.findViewById(R.id.btnHelp);
		this.btnHelp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				createSubMenus(MainMenuTypes.Help);
			}
		});
		loadStoreInfo();
	}
	
	private void createSubMenus(MainMenuTypes mainMenu) {
		if(GlobalResource.getInstance().getUserLogin() == 2){
			GlobalResource.getInstance().setUserLogin(1);
			Intent intentlg = new Intent(MainActivity.this, UserLoginActivity.class);
			this.startActivity(intentlg);
		}
		Gallery gallery = (Gallery) this.findViewById(R.id.Gallery01);
		gallery.setBackgroundResource(R.drawable.submenu_bg3);

		GlobalVariables.TYPE_MENU = 0;
		switch (mainMenu) {
		case Order:
			new SubMenuOrder(this, gallery);
			break;
		case Wallet:
			new SubMenuWallet(this, gallery);
			break;
		case Payment:
			Intent intent = new Intent(MainActivity.this, DiscountsActivity.class);
			this.startActivity(intent);
			break;
		case Events:
			intent = new Intent(MainActivity.this, EventMenuActivity.class);
			this.startActivity(intent);
			break;
		case Account:
			new SubMenuAccount(this, gallery);
			break;
		case Help:
			intent = new Intent(MainActivity.this, VideoHelpActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
	private void loadStoreInfo() {
		/*TextView storeNameText = (TextView) findViewById(R.id.storeName);
		TextView subCodeText = (TextView) findViewById(R.id.subCode);

		if (storeNameText != null) {
			GlobalResource globalResource = GlobalResource.getInstance();

			String storeName = globalResource.getStoreName();
			String subCode = globalResource.getSubCode();

			storeNameText.setText(storeName);
			subCodeText.setText(subCode);
		}*/
	}
	
	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getString(R.string.msg_exist_confirm))
				.setTitle(R.string.txt_title_close)
				.setCancelable(false)
				.setPositiveButton(getString(R.string.text_yes),
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int id) {
								//OrderController.saveOrderToMemory(MainActivity.this);
								GlobalResource.Release();
								MainActivity.this.finish();
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
}