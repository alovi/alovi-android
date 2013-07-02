package com.alovi.activity;

import java.util.ArrayList;
import com.alovi.R;
import com.alovi.common.ImageMenuAdapter;
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
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

public class CardActivity extends BaseActivity {

	private MainMenu menu;
	private Button btnOrder;
	private EditText txtAmount, txtServiceId;
	private Gallery gallery;
	private TableLayout tableLayout;
	private Integer[] pics;
	private ArrayList<ImageView> imageViews;
	private String cardCode;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			setContentView(R.layout.card_code);
			menu = new MainMenu(this, true);
			menu.setTextMainTitle(getString(R.string.txt_my_cart_title).toString());
			menu.setButtonMycartOn();
			
			//pics = new Integer[] { R.drawable.menu_mobifone_bt, R.drawable.menu_viettel_bt, R.drawable.menu_vinaphone_bt, R.drawable.menu_vietnammobile_bt, R.drawable.menu_fpt_bt, R.drawable.menu_vnpt_bt};
			pics = new Integer[] { R.drawable.mobifone_icon, R.drawable.viettel_icon, R.drawable.vinaphone_icon, R.drawable.vietnamobile_icon, R.drawable.gmobile_icon, R.drawable.sfone_icon};
			tableLayout = (TableLayout) this.findViewById(R.id.tableLayout1);
			gallery = (Gallery) this.findViewById(R.id.Gallery01);
			gallery.setBackgroundResource(R.drawable.submenu_bg3);
			gallery.setSpacing(0);
			imageViews = new ArrayList<ImageView>();
			Gallery.LayoutParams layoutParam = new Gallery.LayoutParams(153, 153);
			for (int i = 0; i < pics.length; i++) {
				Integer pic = pics[i];
				ImageView img = new ImageView(CardActivity.this);

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
					imageViews.get(arg2).setAnimation(AnimationUtils.loadAnimation(CardActivity.this, R.anim.item_grow));
					for (int i = 0; i < imageViews.size(); i++) {
						if (i != arg2) {
							imageViews.get(i).setAnimation(AnimationUtils.loadAnimation(CardActivity.this, R.anim.item_shrink));
						}
					}
				}
				@Override
				public void onNothingSelected(AdapterView<?> arg0) {}
			});
			gallery.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int index, long id) {				
					tableLayout.setVisibility(View.GONE);
					switch (index) {
					case 0: //Dien thoai di dong
						txtServiceId.setText("Mobifone");
						cardCode = "9790";
						break;
					case 1: //Game Online
						txtServiceId.setText("Viettel");
						cardCode = "9852";
						break;
					case 2: //Thanh toan
						txtServiceId.setText("Vinaphone");
						cardCode = "9851";
						break;
					case 3: //Dich vu khac
						txtServiceId.setText("Vietnammobile");
						cardCode = "9853";
						break;
					case 4: //Dich vu khac
						txtServiceId.setText("Gmobile");
						cardCode = "9855";
						break;
					case 5: //Dich vu khac
						txtServiceId.setText("SFone");
						cardCode = "9854";
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
			
	
			btnOrder = (Button) findViewById(R.id.btn_mycart_order);
			txtAmount = (EditText) findViewById(R.id.txtAmount);
			txtServiceId = (EditText) findViewById(R.id.txtServiceID);
			txtServiceId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					tableLayout.setVisibility(View.VISIBLE);
					txtAmount.requestFocus();
				}
			});
	
			btnOrder.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						showDialog(PROGRESS_DIALOG);
						if (Util.checkInternet(CardActivity.this)) {
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
				AlertDialog.Builder builder = new AlertDialog.Builder(CardActivity.this);
				builder.setMessage(getString(R.string.txt_error))
						.setTitle(R.string.txt_error)
						.setCancelable(false)
						.setPositiveButton(getString(R.string.text_ok),//text_yes),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int id) {
										
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
				//showDlgRegister();
				return;
			}else if(state==OrderState.Success){
				Util.alertOrderState(getApplicationContext(), OrderState.Created);
				//show dialog payment
				AlertDialog.Builder builder = new AlertDialog.Builder(CardActivity.this);
				builder.setMessage(getString(R.string.txt_mycart_confirm_payment_msg))
						.setTitle(R.string.txt_mycart_confirm_payment_title)
						.setCancelable(false)
						.setPositiveButton(getString(R.string.text_ok),
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int id) {
										Intent intent = new Intent(CardActivity.this, EventMenuActivity.class);
										CardActivity.this.startActivity(intent);
										CardActivity.this.finish();
									}
								});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
	};
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { }
		return super.onKeyDown(keyCode, event);
	}
	
	private OrderState doOrder() throws Exception{
		OrderData state;
		String amount = txtAmount.getText().toString();
		state = OrderController.createOrder(2, amount, cardCode, null, true);
		if(state != null){
			GlobalResource globalResource = GlobalResource.getInstance();
			globalResource.setOrderCode(""+state.response.order.paymentID);
			return OrderState.Success;
		}
		return OrderState.Error;
	}
}