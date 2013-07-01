package com.alovi.activity;

import java.util.ArrayList;

import com.alovi.R;
import com.alovi.common.ImageMenuAdapter;
import com.alovi.common.MainMenu;
import com.alovi.common.MessageTypes;
import com.alovi.common.Util;
import com.alovi.controller.UserController;
import com.alovi.data.GlobalResource;
import com.alovi.data.UserData;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class MyInfoActivity extends BaseActivity {
	private MainMenu menu;
	private Button btnOk, btnChangePass;
	private View.OnClickListener clickListener;
	private EditText txtUserFirstName, txtUserLastName, txtUserName, txtEmail, txtPhone, txtBankId, txtBankAccountNumber, txtBankAccountName;
	private UserData user;
	private TableRow tableRow;
	private Gallery gallery;
	private ArrayList<ImageView> imageViews;
	private Integer[] pics;
	protected String cardCode;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.edit_my_info);
		menu = new MainMenu(this, true);
		menu.setTextMainTitle(getString(R.string.txt_edit_myinfo_title).toString());
		menu.setButtonMyWalletStatus(true);
		
		ScrollView scrollView1 = (ScrollView) findViewById(R.id.scrollView1);
		scrollView1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, GlobalResource.getInstance().getScreenHeigth()-227));
		
		txtUserFirstName = (EditText) findViewById(R.id.txtUserFirstName);
		txtUserLastName = (EditText) findViewById(R.id.txtUserLastName);
		txtUserName = (EditText) findViewById(R.id.txtUserName);
		txtEmail = (EditText) findViewById(R.id.txtEmail);
		txtPhone = (EditText) findViewById(R.id.txtPhone);
		txtBankId = (EditText) findViewById(R.id.txtBankId);
		txtBankAccountNumber = (EditText) findViewById(R.id.txtBankAccountNumber);
		txtBankAccountName = (EditText) findViewById(R.id.txtBankAccountName);
		
		initBankID();
		
		btnChangePass = (Button) findViewById(R.id.btn_edit_info_pass);
		btnOk = (Button) findViewById(R.id.btn_edit_info_ok);

		setControls();
		loadData();
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
			ImageView img = new ImageView(MyInfoActivity.this);

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
				imageViews.get(arg2).setAnimation(AnimationUtils.loadAnimation(MyInfoActivity.this, R.anim.item_grow));
				for (int i = 0; i < imageViews.size(); i++) {
					if (i != arg2) {
						imageViews.get(i).setAnimation(AnimationUtils.loadAnimation(MyInfoActivity.this, R.anim.item_shrink));
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
					cardCode = "6";
					break;
				case 6: //Dich vu khac
					txtBankId.setText(R.string.bankname7);
					cardCode = "7";
					break;
				case 7: //Dich vu khac
					txtBankId.setText(R.string.bankname8);
					cardCode = "8";
					break;
				case 8: //Dich vu khac
					txtBankId.setText(R.string.bankname9);
					cardCode = "9";
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

	private void setControls() {
		clickListener = new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (v instanceof Button) {
					if (v.getId() == R.id.btn_edit_info_pass) {
						Intent intent = new Intent(MyInfoActivity.this, ChangePasswordActivity.class);
						MyInfoActivity.this.startActivity(intent);
					} else if (v.getId() == R.id.btn_edit_info_ok) {
						updateUser();
					}
				}
			}
		};
		this.btnChangePass.setOnClickListener(clickListener);
		this.btnOk.setOnClickListener(clickListener);
	}

	private void loadData() {
		GlobalResource g = GlobalResource.getInstance();
		UserData user = g.getUser();
		txtUserFirstName.setText(user.response.user.FirstName);
		txtUserLastName.setText(user.response.user.LastName);
		txtUserName.setText(user.response.user.userName);
		txtEmail.setText(user.response.user.email);
		txtPhone.setText(user.response.user.MobilePhone);
		txtBankId.setText(user.response.user.bank_name);
		txtBankAccountNumber.setText(user.response.user.bank_account_number);
		txtBankAccountName.setText(user.response.user.bank_account_name);
	}

	private void updateUser() {
		final String firstName = txtUserFirstName.getText().toString();
		final String lastName = txtUserLastName.getText().toString();
		final String email = txtEmail.getText().toString();
		final String phone = txtPhone.getText().toString();
		final String bankID = cardCode;
		final String bankAccountNumber = txtBankAccountNumber.getText().toString();
		final String bankAccountName = txtBankAccountName.getText().toString();
		try {
			if (Util.checkInternet(this)) {
				showDialog(PROGRESS_DIALOG);
				new Thread(new Runnable() {
					@Override
					public void run() {
						UserData res=UserController.updateUser(firstName, lastName, phone, email, bankID, bankAccountNumber, bankAccountName);
						if(res!=null){
							GlobalResource globalResource = GlobalResource.getInstance();
							globalResource.setUser(res);
						}
						Message msg = handlerUpdate.obtainMessage();
						msg.obj = res;
						handlerUpdate.sendMessage(msg);
					}
				}).start();
			} else {
				messageBox(MessageTypes.Error, getString(R.string.text_check_net_fail).toString());
			}
		} catch (Exception ex) { }
	}

	final Handler handlerUpdate = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissDialog(PROGRESS_DIALOG);
			if ((UserData) msg.obj != null) {
				toast(getString(R.string.msg_edit_info_complete));
				Intent intent = new Intent("com.alovi.activity.MainActivity");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				MyInfoActivity.this.startActivity(intent);
			}
		}
	};
	
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissDialog(PROGRESS_DIALOG);
			if (user == null)
				return;
		}
	};
}