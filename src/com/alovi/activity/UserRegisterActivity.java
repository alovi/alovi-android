package com.alovi.activity;

import java.util.ArrayList;

import com.alovi.R;
import com.alovi.common.ImageMenuAdapter;
import com.alovi.common.MessageTypes;
import com.alovi.common.UserRegistErrorState;
import com.alovi.common.Util;
import com.alovi.controller.UserController;
import com.alovi.data.GlobalResource;
import com.alovi.data.GlobalVariables;
import com.alovi.data.UserData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class UserRegisterActivity extends BaseActivity {

	private TextView txtTitle, txtUserAgreeContent;
	private ImageView imgLogo;
	private Button btnBack, btnCancel, btnRegister;
	private CheckBox cbAgree1;
	private EditText txtUserFirstName, txtUserLastName, txtUserName, txtPassword, txtRePassword, txtEmail, txtNumberPhone, txtBankAccountNumber, txtBankAccountName, txtBankId;
	private String receivedAuthCode;
	private Integer[] pics;
	private Gallery gallery;
	private ArrayList<ImageView> imageViews;
	protected String cardCode;
	private TableRow tableRow;
	public UserRegisterActivity() {	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
		setContentView(R.layout.user_register);
		//MainMenu menu = new MainMenu(this, true);

		ScrollView scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
		scrollView1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, GlobalResource.getInstance().getScreenHeigth()-134));
		
		txtTitle = (TextView) findViewById(R.id.maintitle);
		txtTitle.setText(getString(R.string.user_register_title));
		txtUserAgreeContent = (TextView) findViewById(R.id.useragreecontent);
		txtUserAgreeContent.setMovementMethod(LinkMovementMethod.getInstance());
		imgLogo = (ImageView) findViewById(R.id.imgCommonlogo);
		imgLogo.setImageResource(R.drawable.common_logo);

		txtUserFirstName = (EditText) findViewById(R.id.txtUserFirstName);
		txtUserLastName = (EditText) findViewById(R.id.txtUserLastName);
		txtPassword = (EditText) findViewById(R.id.txtPassword);
		txtRePassword = (EditText) findViewById(R.id.txtRePassword);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtNumberPhone = (EditText) findViewById(R.id.txtPhone);
		txtBankAccountNumber = (EditText) findViewById(R.id.txtBankAccountNumber);
		txtBankAccountName = (EditText) findViewById(R.id.txtBankAccountName);
		txtBankId = (EditText) findViewById(R.id.txtBankId);
		cbAgree1 = (CheckBox) findViewById(R.id.cb_user_register_agree1);
		
		initBankID();
		txtBankId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				tableRow.setVisibility(View.VISIBLE);
				txtBankAccountNumber.requestFocus();
			}
		});
		
		txtUserName = (EditText) findViewById(R.id.txtUserName);
		txtUserName.addTextChangedListener(new TextWatcher(){
	        @Override
			public void afterTextChanged(Editable s) {
	            //TODO afterTextChanged check for username has existing.
	        	
	        }
	        
	        @Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after){
	        	
	        }
	        
	        @Override
			public void onTextChanged(CharSequence s, int start, int before, int count){
	        	
	        }
	    }); 
		
		btnRegister = (Button) findViewById(R.id.btn_Register);
		btnRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!cbAgree1.isChecked()) {
					messageDialog(getString(R.string.user_register_title), getString(R.string.msg_register_agreementnotcheck));
					return;
				}
				final String userFirstName = txtUserFirstName.getText().toString();
				final String userLastName = txtUserLastName.getText().toString();
				final String userName = txtUserName.getText().toString();
				final String password = txtPassword.getText().toString();
				final String rePassword = txtRePassword.getText().toString();
				final String email = txtEmail.getText().toString();
				final String numberPhone = txtNumberPhone.getText().toString();
				final String bankAccountNumber = txtBankAccountNumber.getText().toString();
				final String bankAccountName = txtBankAccountName.getText().toString();
				final String bankId = txtBankId.getText().toString();

				if (userFirstName.length() == 0 || userLastName.length() == 0 ||
						userName.length() == 0 || password.length() == 0 ||
						rePassword.length() == 0 || email.length() == 0 ||
						numberPhone.length() == 0) {
					toast(getString(R.string.msg_userreg_inputallfield));
					return ;
				}
				if (!password.equals(rePassword)) {
					toast(getString(R.string.msg_userreg_inputrepass));
					return ;
				}
				try {
					if (Util.checkInternet(UserRegisterActivity.this)) {
						showDialog(PROGRESS_DIALOG);
						new Thread(new Runnable() {
							@Override
							public void run() {
								UserData res = UserController.createUser(userFirstName, userLastName, userName, password, email, numberPhone, bankAccountNumber, bankAccountName, bankId, 1);
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
		});
		btnCancel = (Button) findViewById(R.id.btn_user_registercancel);
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		}catch(Exception ex){
			Log.e("initBankID", "catch: " + ex.getMessage());
		}
	}
	
	private void initBankID() {
		pics = new Integer[] { R.drawable.menu_vietcombank_bt, R.drawable.menu_donga_bt, R.drawable.menu_agribank1_bt, R.drawable.menu_sacombank_bt,
				R.drawable.menu_bidv_bt, R.drawable.menu_acb_bt, R.drawable.menu_techcombank_bt, R.drawable.menu_eximbank_bt, R.drawable.menu_agribank_bt};
		tableRow = (TableRow) this.findViewById(R.id.tableRow1);
		gallery = (Gallery) this.findViewById(R.id.Gallery01);
		gallery.setBackgroundResource(R.drawable.submenu_bg3);
		gallery.setSpacing(0);
		imageViews = new ArrayList<ImageView>();
		Gallery.LayoutParams layoutParam = new Gallery.LayoutParams(150, 120);
		for (int i = 0; i < pics.length; i++) {
			Integer pic = pics[i];
			ImageView img = new ImageView(UserRegisterActivity.this);

			img.setId(pic);
			img.setImageResource(pic);
			img.setLayoutParams(layoutParam);
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			imageViews.add(img);
		}
		gallery.setAdapter(new ImageMenuAdapter(imageViews));
		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				imageViews.get(arg2).setAnimation(AnimationUtils.loadAnimation(UserRegisterActivity.this, R.anim.item_grow));
				for (int i = 0; i < imageViews.size(); i++) {
					if (i != arg2) {
						imageViews.get(i).setAnimation(AnimationUtils.loadAnimation(UserRegisterActivity.this, R.anim.item_shrink));
					}
				}
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		gallery.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int index, long id) {				
				tableRow.setVisibility(View.GONE);
				switch (index) {
				case 0: //Dien thoai di dong
					txtBankId.setText(R.string.bankname1);
					cardCode = "1";
					break;
				case 1: //Game Online
					txtBankId.setText(R.string.bankname2);
					cardCode = "2";
					break;
				case 2: //Thanh toan
					txtBankId.setText(R.string.bankname3);
					cardCode = "3";
					break;
				case 3: //Dich vu khac
					txtBankId.setText(R.string.bankname4);
					cardCode = "4";
					break;
				case 4: //Dich vu khac
					txtBankId.setText(R.string.bankname5);
					cardCode = "5";
					break;
				case 5: //Dich vu khac
					txtBankId.setText(R.string.bankname6);
					cardCode = "";
					break;
				case 6: //Dich vu khac
					txtBankId.setText(R.string.bankname7);
					cardCode = "";
					break;
				case 7: //Dich vu khac
					txtBankId.setText(R.string.bankname8);
					cardCode = "";
					break;
				case 8: //Dich vu khac
					txtBankId.setText(R.string.bankname9);
					cardCode = "";
					break;
				default:
					break;
				}
			}
		});
		// set selection
		int position = pics.length;
		if (position % 2 == 0) {
			position = pics.length / 2 - 1;
		}
		else {
			position = pics.length / 2; 			
		}
		gallery.setSelection(position, true);
		
	}
	
	final Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissDialog(PROGRESS_DIALOG);
			UserData user=(UserData)msg.obj;
			String message="";
			if(user!=null)
				message=user.response.message.toString();
			
			if (message.equalsIgnoreCase("DuplicateUserName")) {
				toast(getString(R.string.msg_userreg_dupusername));
			}
			else if(message.equalsIgnoreCase(UserRegistErrorState.RegistCompleted.name())) {
				toast("Success!");
				// We need an Editor object to make preference changes. All objects are from android.context.Context
				SharedPreferences settings = getSharedPreferences(GlobalVariables.PREFS_NAME, 0);
				SharedPreferences.Editor editor = settings.edit();
				editor.putString(GlobalVariables.USER_PHONE_NUMBER, UserName);
				editor.putString(GlobalVariables.USER_AUTHENTICATION_CODE, receivedAuthCode);

				/*GlobalResource globalResource = GlobalResource.getInstance();
				globalResource.setUserName(UserName);*/

				// Commit the edits!
				editor.commit();
				boolean paymentAction = getIntent().getBooleanExtra(GlobalVariables.ACTION_REGISTER_DOPAYMENT, false);
				Intent intent = null;
				if(paymentAction) {
					intent = new Intent(UserRegisterActivity.this,MainActivity.class);
				} else {
					intent = new Intent(UserRegisterActivity.this, MainActivity.class);
				}
				startActivity(intent);
				UserRegisterActivity.this.finish();
			} else {
				toast(getString(R.string.msg_userreg_authcode_notmatch));
			}
		}
	};
}