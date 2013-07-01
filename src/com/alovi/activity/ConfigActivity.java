package com.alovi.activity;


import com.alovi.R;
import com.alovi.common.Config;
import com.alovi.common.MessageTypes;
import com.alovi.common.Util;
import com.alovi.data.GlobalResource;
import com.alovi.webservice.APIServiceVariables;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfigActivity extends BaseActivity {

	private TextView txtTitle;
	private ImageView imgLogo;
	private Button btnBack, btnRegister;
	private EditText txtConfigHost, txtConfigServiceAPI;
	public ConfigActivity() { }

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try{setContentView(R.layout.config);
		
		txtTitle = (TextView) findViewById(R.id.maintitle);
		imgLogo = (ImageView) findViewById(R.id.imgCommonlogo);
		imgLogo.setImageResource(R.drawable.common_logo);
		txtTitle.setText(getString(R.string.config_title));
		txtConfigHost = (EditText) findViewById(R.id.txtConfigHost);
		txtConfigHost.setText(APIServiceVariables.getInstance().HOST());
		txtConfigServiceAPI = (EditText) findViewById(R.id.txtConfigServiceContext);
		txtConfigServiceAPI.setText(APIServiceVariables.getInstance().SERVICE_CONTEXT());
		
		btnRegister = (Button) findViewById(R.id.btn_Register);
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final String configHost = txtConfigHost.getText().toString();
				final String configServiceAPI = txtConfigServiceAPI.getText().toString();
				
				if (configHost.length() == 0 || configServiceAPI.length() == 0) {
					toast(getString(R.string.msg_userreg_inputallfield));
					return ;
				}
				try {
					if (Util.checkInternet(ConfigActivity.this)) {
						showDialog(PROGRESS_DIALOG);
						new Thread(new Runnable() {
							@Override
							public void run() {
								String res=doConfig(configHost, configServiceAPI);
								Message msg=handle.obtainMessage();
								msg.obj=res;
								handle.sendMessage(msg);
							}
						}).start();
					} else {
						messageBox(MessageTypes.Error, getString(R.string.text_check_net_fail).toString());
					}
				} catch (Exception ex) {
					messageBox(MessageTypes.Error, ex.getMessage().toString());
				}
			}
		});
		
		btnBack = (Button) findViewById(R.id.btn_common_back);
		btnBack.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});}
		catch(Exception e){
			Log.e("read", "error: " + e.getMessage());
		}
	}
	
	private String doConfig(String configHost, String configServiceContext) {
		if(Config.Write(configHost, configServiceContext)){
			return "success";
		}
		return "error";
	}
    
	final Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissDialog(PROGRESS_DIALOG);
			String message=(String)msg.obj;
			if (message.equalsIgnoreCase("success")) {
				toast("Success!");
				GlobalResource.getInstance().setUserLogin(1);
				Intent intent = new Intent(ConfigActivity.this, UserLoginActivity.class);
				ConfigActivity.this.startActivity(intent);
				ConfigActivity.this.finish();
			}
			else if (message.equalsIgnoreCase("error")) {
				toast(getString(R.string.msg_userreg_dupusername));
			}
		}
	};
}