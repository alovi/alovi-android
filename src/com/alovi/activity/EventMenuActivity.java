package com.alovi.activity;


import java.util.List;

import com.alovi.R;
import com.alovi.common.MainMenu;
import com.alovi.common.MessageTypes;
import com.alovi.common.Util;
import com.alovi.controller.OrderController;
import com.alovi.data.GlobalResource;
import com.alovi.data.PaymentData;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

public class EventMenuActivity extends BaseActivity {
	
	private MainMenu menu;
	private OnClickListener clickListener;
	private TableRow tbevent_notice, tbevent_event_news;
	private TextView txtMaDV, txtContent, txtState;
	private PaymentData state;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.event_submenu);

		menu = new MainMenu(this, true, true);
		menu.setTextMainTitle(getString(R.string.txt_event_title).toString());
		menu.setLogoImage();
		menu.setButtonEventsStatus(true);
		
		txtMaDV = (TextView) findViewById(R.id.txtMaGiaoDich);
		txtContent = (TextView) findViewById(R.id.txtContent);
		txtState = (TextView) findViewById(R.id.txtState);

		setControls();
		checkPayment();
	}
	
	private void setControls() {
		clickListener = new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				resetStatus();
				setImageClick(v,true);
				switch (v.getId()) {
				case R.id.tbevent_notice:
					//Intent intent=new Intent(MyWalletSubmenuActivity.this,ChangePassPatternActivity.class);
					//startActivity(intent);
					break;
				case R.id.tbevent_event_news:
					//intent=new Intent(MyWalletSubmenuActivity.this,MyInfoActivity.class);
					//startActivity(intent);
					break;
				case R.id.btnRefresh:
					checkPayment();
					Log.e("checkPayment", "finished checked");
					break;
				default:
					break;
				}
			}
		};
		
		tbevent_notice = (TableRow) findViewById(R.id.tbevent_notice);
		tbevent_event_news = (TableRow) findViewById(R.id.tbevent_event_news);

		tbevent_notice.setOnClickListener(clickListener);
		tbevent_event_news.setOnClickListener(clickListener);
	}
	
	private void setImageClick(View row, boolean clicked)
	{
		View child = row.findViewById(R.id.btnmywallet_submenu_arrow);
		if (child instanceof Button)
			if(clicked)
				child.setBackgroundResource(R.drawable.mywallet_submenu_arrow1);
			else
				child.setBackgroundResource(R.drawable.mywallet_submenu_arrow);	
	}
	
	private void resetStatus()
	{
		setImageClick(tbevent_notice, false);
		setImageClick(tbevent_event_news, false);
	}
	
	private void checkPayment(){
		try {
			if (Util.checkInternet(EventMenuActivity.this)) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							GlobalResource globalResource = GlobalResource.getInstance();
							String orderCode = globalResource.getOrderCode();
							if(orderCode!=null&&orderCode.length()!=0)
								state = OrderController.getCurrentOrder(orderCode);
							else state = OrderController.getPayments("5", "1");
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
		} catch (Exception ex) {
			messageBox(MessageTypes.Error, "Loi o day ne: "+ex.getMessage());
		}
	}
	
	final Handler handlerOrder = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			PaymentData state=(PaymentData) msg.obj;
			if(state!=null){
				List<PaymentData.Response.Payment> lData = state.response.payment;
				PaymentData.Response.Payment data = lData.get(0);
				//for (PaymentData.Response.Data data : lData) {
				txtMaDV.setText(data.transactionId);
				txtContent.setText(data.account+" _ "+data.amount + " _ " + data.currentDate);
				txtState.setText(data.status);
				//}
			}
		}
	};
}