package com.alovi.activity;

import com.alovi.R;
import com.alovi.common.Config;
import com.alovi.common.MainMenu;
import com.alovi.common.MessageTypes;
import com.alovi.common.Util;
import com.alovi.controller.UserController;
import com.alovi.data.GlobalResource;
import com.alovi.data.UserData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MyWalletActivity extends BaseActivity {
	private MainMenu menu;
	private Button btnOk;
	private View.OnClickListener clickListener;
	private EditText txtUserName, txtPhone, txtSoDu;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.mywallet_info);
		menu = new MainMenu(this, true);
		menu.setTextMainTitle(getString(R.string.txt_mywallet_info_title).toString());
		menu.setButtonMyWalletStatus(true);
		btnOk = (Button) findViewById(R.id.btn_edit_info_ok);
		txtUserName = (EditText) findViewById(R.id.txt_edit_user_UserName);
		txtPhone = (EditText) findViewById(R.id.txtphoneNumber1);
		txtSoDu = (EditText) findViewById(R.id.txt_editinfo_sodu);

		setControls();
		getUserInfo();
	}

	private void setControls() {
		clickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v instanceof Button) {
					if (v.getId() == R.id.btn_edit_info_ok) {
						MyWalletActivity.this.finish();
						return;
					}
				}
			}
		};
		this.btnOk.setOnClickListener(clickListener);
	}

	private void loadData() {
		GlobalResource g = GlobalResource.getInstance();
		UserData user = g.getUser();
		txtUserName.setText(user.response.user.userName);
		txtPhone.setText(user.response.user.MobilePhone);
		txtSoDu.setText(Util.formatCurrency(Double.parseDouble(user.response.user.money)) + " " + getString(R.string.txt_unit_vietnamdong));
	}

	private void getUserInfo() {
		try {
			if (Util.checkInternet(this)) {
				showDialog(PROGRESS_DIALOG);
				new Thread(new Runnable() {
					@Override
					public void run() {
						GlobalResource g = GlobalResource.getInstance();
						UserData res=UserController.getUser(g.getUserName(), g.getPassword());
						Message msg = handlerUpdate.obtainMessage();
						if(res!=null) msg.obj = true;
						handlerUpdate.sendMessage(msg);
					}
				}).start();
			} else {
				messageBox(MessageTypes.Error, getString(R.string.text_check_net_fail).toString());
			}
		} catch (Exception ex) {
			Config.WriteLog(ex.getMessage());
		}
	}

	final Handler handlerUpdate = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissDialog(PROGRESS_DIALOG);
			if ((Boolean) msg.obj) {
				loadData();
			}
		}
	};
}