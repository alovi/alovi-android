package com.alovi.activity;

import com.alovi.R;
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

public class ChangePasswordActivity extends BaseActivity {
	private EditText txtOldPass, txtNewPass, txtRePass;
	private Button btnSave;
	private String newPassword = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepassword);
		
		MainMenu menu = new MainMenu(this, true);
		menu.setTextMainTitle(getString(R.string.txt_changepassword_title).toString());
		
		txtOldPass = (EditText) findViewById(R.id.txtOldPassword);
		txtNewPass = (EditText) findViewById(R.id.txtNewPassword);
		txtRePass = (EditText) findViewById(R.id.txtReNewPassword);
		btnSave = (Button) findViewById(R.id.btn_changepass_save);
		setControls();
	}

	public void setControls() {
		btnSave.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					if (Util.checkInternet(ChangePasswordActivity.this)) {
						showDialog(PROGRESS_DIALOG);
						new Thread(new Runnable() {
							@Override
							public void run() {
								GlobalResource g = GlobalResource.getInstance();
								String oldPass = g.getPassword();
								newPassword = txtNewPass.getText().toString();
								if(txtOldPass.getText().toString()!= oldPass){
									Util.showDialog(ChangePasswordActivity.this,getString(R.string.txt_warning), getString(R.string.msg_changepassword_null));
									return;
								}
								if(newPassword.isEmpty()) {
									Util.showDialog(ChangePasswordActivity.this,getString(R.string.txt_warning), getString(R.string.msg_changepassword_null));
									return;
								}
								if(txtNewPass.getText().toString()!=txtRePass.getText().toString()){
									Util.showDialog(ChangePasswordActivity.this,getString(R.string.txt_warning), getString(R.string.msg_changepassword_null));
									return;
								}
								UserData res=UserController.changePassword(newPassword);
								if(res!=null) GlobalResource.getInstance().setPassword(newPassword);
								Message msg = handler.obtainMessage();
								msg.obj=res;
								handler.sendMessage(msg);
							}
						}).start();
					} else {
						messageBox(MessageTypes.Error, getString(R.string.text_check_net_fail).toString());
					}
				} catch (Exception ex) { }
			}
		});
	}
	
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {			
			dismissDialog(PROGRESS_DIALOG);
			UserData res= (UserData) msg.obj;
			if(res!=null) {
				toast(getString(R.string.msg_changepassword_success));
				finish();
			}
			else
				toast(getString(R.string.msg_changepassword_fail));
		}
	};
	
	/*class MyInnerHandler extends Handler{
		
		MyInnerHandler(ChangePasswordActivity c) { }
		@Override
        public void handleMessage(Message message) {
        	dismissDialog(PROGRESS_DIALOG);
			String res= message.obj.toString();
			if(res.equals("success")) {
				toast(getString(R.string.msg_changepassword_success));
				finish();
			}
			else
				toast(getString(R.string.msg_changepassword_fail));
        }
    }
    MyInnerHandler myHandler = new MyInnerHandler(this);*/
}