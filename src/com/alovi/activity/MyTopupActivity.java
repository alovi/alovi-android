package com.alovi.activity;

import com.alovi.R;
import com.alovi.common.MainMenu;
import com.alovi.common.MessageTypes;
import com.alovi.common.OrderState;
import com.alovi.common.Util;
import com.alovi.controller.OrderController;
import com.alovi.data.GlobalResource;
import com.alovi.data.OrderData;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MyTopupActivity extends BaseActivity {

	private MainMenu menu;
	private Button btnOrder, btnMyContact;
	private TextView txtNoteMoney;
	private EditText txtAmount, txtAccount;
	private RadioGroup rgTypePay;
	private boolean isTraTruoc = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			setContentView(R.layout.topup);
			menu = new MainMenu(this, true);
			menu.setTextMainTitle(getString(R.string.txt_topup_title).toString());
			menu.setButtonMycartOn();
	
			btnOrder = (Button) findViewById(R.id.btn_mycart_order);
			btnMyContact = (Button) findViewById(R.id.btnMyContact);
			txtAmount = (EditText) findViewById(R.id.txtAmount);
			txtAmount.setHint(getText(R.string.txt_payment_amount_en));
			txtAccount = (EditText) findViewById(R.id.txtAccount);
			txtAccount.setHint(getText(R.string.txt_account_mobile_en));
			txtNoteMoney = (TextView) findViewById(R.id.txtNoteMoney);
			rgTypePay = (RadioGroup) findViewById(R.id.rgTypePay);

			rgTypePay.setOnCheckedChangeListener(new OnCheckedChangeListener(){
			    @Override
				public void onCheckedChanged(RadioGroup rgType, int checkedId){
					if(checkedId == R.id.rbTraTruoc){
						txtNoteMoney.setText(getText(R.string.txt_note_money1));
						isTraTruoc = true;
					}else{
						txtNoteMoney.setText(getText(R.string.txt_note_money2));
						isTraTruoc = false;
					}
			    }
			});
			btnOrder.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						showDialog(PROGRESS_DIALOG);
						if (Util.checkInternet(MyTopupActivity.this)) {
							new Thread(new Runnable() {
								@Override
								public void run() {
									OrderState state=OrderState.Error;
									try {
										state = doOrder();
									} catch (Exception e) {
										e.printStackTrace();
									}
									finally{
										Message msg = handlerOrder.obtainMessage();
										msg.obj = state;
										handlerOrder.sendMessage(msg);
									}
								}
							}).start();
						} else {
							messageBox(MessageTypes.Error, getString(R.string.text_check_net_fail).toString());
						}
					} catch (Exception ex) { }
				}
			});
			btnMyContact.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try{
						Intent i = new Intent(MyTopupActivity.this, ContactActivity.class);
						startActivity(i);
					}catch(Exception e){ }
				}
			});
		}catch(Exception ex){
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
		btnOrder.setEnabled(true);
	}

	public void setMenu(String storeCode) {
		// menu.setTextMainTitle(getStoreName());
		Bitmap bitmap = getStoreLogo();
		menu.setLogoImage(bitmap, storeCode);
	}

	final Handler handlerOrder = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dismissDialog(PROGRESS_DIALOG);
			OrderState state=(OrderState) msg.obj;
			if(state==OrderState.Error) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MyTopupActivity.this);
				builder.setMessage(getString(R.string.txt_error))
						.setTitle(R.string.txt_error)
						.setCancelable(false)
						.setPositiveButton(getString(R.string.text_ok),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int id) {
										
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
				return;
			}else if(state==OrderState.Success){
				Util.alertOrderState(getApplicationContext(), OrderState.Created);
				//show dialog payment
				AlertDialog.Builder builder = new AlertDialog.Builder(MyTopupActivity.this);
				builder.setMessage(getString(R.string.txt_mycart_confirm_payment_msg))
						.setTitle(R.string.txt_mycart_confirm_payment_title)
						.setCancelable(false)
						.setPositiveButton(getString(R.string.text_ok),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int id) {
										Intent intent = new Intent(MyTopupActivity.this, MainActivity.class);
										MyTopupActivity.this.startActivity(intent);
										MyTopupActivity.this.finish();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {	}
		return super.onKeyDown(keyCode, event);
	}
	
	private OrderState doOrder() throws Exception{
		String amount = txtAmount.getText().toString();
		String account = txtAccount.getText().toString();
		if(amount==null||account==null||amount.length()==0||account.length()==0){
			toast("Chưa nhập đầy đủ thông tin!");
			return OrderState.Error;
		}
		if(rgTypePay.getCheckedRadioButtonId()==R.id.rbTraTruoc&&Integer.parseInt(amount)>5000
				&&Integer.parseInt(amount)%5000!=0){
			toast(getText(R.string.txt_note_money1).toString());
			return OrderState.Error;
		}
		if(rgTypePay.getCheckedRadioButtonId()==R.id.rbTraSau&&Integer.parseInt(amount)>5000
				&&Integer.parseInt(account)%1000!=0){
			toast(getText(R.string.txt_note_money2).toString());
			return OrderState.Error;
		}
		
		OrderData state = OrderController.createOrder(1, amount, null, account, isTraTruoc);
		if(state != null){
			GlobalResource globalResource = GlobalResource.getInstance();
			globalResource.setOrderCode(""+state.response.order.paymentID);
			return OrderState.Success;
		}
		return OrderState.Error;
	}
}