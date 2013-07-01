package com.alovi.activity;

import com.alovi.R;
import com.alovi.common.MainMenu;
import com.alovi.common.MessageTypes;
import com.alovi.common.OrderState;
import com.alovi.common.Util;
import com.alovi.controller.UserController;
import com.alovi.data.TransferData;

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
import android.widget.TableRow;
import android.widget.Toast;

public class TransferActivity extends BaseActivity {

	private MainMenu menu;
	private Button btnOrder;
	private EditText txtAmount, txtAccount;
	private EditText txtNoteTransfer;
	private EditText txtAmount1;
	private EditText txtPinTransfer;
	private TableRow tableRowPinTransfer;
	protected String note;
	protected String amount;
	protected String account;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			setContentView(R.layout.transfer);
			menu = new MainMenu(this, true);
			menu.setTextMainTitle(getString(R.string.txt_transfer_title).toString());
			menu.setButtonMycartOn();
	
			btnOrder = (Button) findViewById(R.id.btn_mycart_order);
			txtAmount = (EditText) findViewById(R.id.txtAmount);
			txtAmount1 = (EditText) findViewById(R.id.txtAmount1);
			txtAccount = (EditText) findViewById(R.id.txtAccount);
			txtNoteTransfer = (EditText) findViewById(R.id.txtNoteTransfer);
			tableRowPinTransfer = (TableRow) findViewById(R.id.tableRowPinTransfer);
			txtPinTransfer = (EditText) findViewById(R.id.txtPinTransfer);
			btnOrder.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					amount = txtAmount.getText().toString();
					account = txtAccount.getText().toString();
					note = txtNoteTransfer.getText().toString();
					String pin = txtPinTransfer.getText().toString();
					if(amount==null||account==null||amount.length()==0||account.length()==0){
						toast("Chưa nhập đầy đủ thông tin!");
						return;
					}
					if(tableRowPinTransfer.getVisibility()==View.GONE){
						txtAmount1.setText("Bang chu");
						txtAmount1.setVisibility(View.VISIBLE);
						tableRowPinTransfer.setVisibility(View.VISIBLE);
						return;
					}
					if(pin==null||pin!="1234"){
						toast("Sai pin! pin: 1234");
						return;
					}
					try {
						showDialog(PROGRESS_DIALOG);
						if (Util.checkInternet(TransferActivity.this)) {
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
				AlertDialog.Builder builder = new AlertDialog.Builder(TransferActivity.this);
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
				AlertDialog.Builder builder = new AlertDialog.Builder(TransferActivity.this);
				builder.setMessage(getString(R.string.txt_mycart_confirm_payment_msg))
						.setTitle(R.string.txt_mycart_confirm_payment_title)
						.setCancelable(false)
						.setPositiveButton(getString(R.string.text_ok),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int id) {
										Intent intent = new Intent(TransferActivity.this, EventMenuActivity.class);
										TransferActivity.this.startActivity(intent);
										TransferActivity.this.finish();
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
		
		
		
		
		TransferData state = UserController.transfer(account, amount, note);
		if(state != null){
			//GlobalResource globalResource = GlobalResource.getInstance();
			//globalResource.setOrderCode(""+state.response.transfer..paymentID);
			return OrderState.Success;
		}
		return OrderState.Error;
	}
}