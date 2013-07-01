package com.alovi.vieweritems;

import com.alovi.R;
import com.alovi.common.Order;
import com.alovi.common.Util;
import com.alovi.data.GlobalResource;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OrderIconTextView extends IconTextView{
	private Button btnIncrease;
	private Button btnDecrese;
	
	private View.OnClickListener clickListener;
	private TextView txtTotal;
	private Button btnOrder;
	private int orderedQuy;
	private String won;
	private Context context;
	
	public OrderIconTextView(Context context, IconTextItem aItem) {
		super(context);
		this.context=context;
		mIcon.setImageBitmap(aItem.getIcon());
		
		won=context.getString(R.string.txt_unit_vietnamdong);
		
		mText01.setText(aItem.getData(1));
		
		mText03.setText(aItem.getData(3));
		
		mText04.setText(aItem.getData(4));
		
		mText05.setText(aItem.getData(5));
		
		btnOrder = (Button) ((Activity)context).findViewById(R.id.btn_mycart_order);
		
		int qty = Util.strToInt(aItem.getData(5).trim());
		orderedQuy = Util.strToInt(aItem.getData(6).trim());
		
		btnIncrease.setTag(aItem.getData(0));
		btnDecrese.setTag(aItem.getData(0));
		
		// Disbale the (-) button when qty equals the ordered qty
		if(qty <= orderedQuy){
			btnDecrese.setEnabled(false);
		}
		
		clickListener=new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(v instanceof Button)
				{
					if(true)//v.getId()==R.id.btnDecrease)
					{
						final int total=Integer.parseInt(mText05.getText().toString())-1;
						
						//mText05.setText(String.valueOf(total));
						//update quatity
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								synchronized (this) {
									updateOrderQtyAtLine(Util.strToInt(btnDecrese.getTag().toString()), total);
									Message msg = handler.obtainMessage();
									msg.arg1=total;
									//msg.arg2=R.id.btnDecrease;
									handler.sendMessage(msg);
								}	
							}
						}).start();
					}
				}
			}
		};
		btnDecrese.setOnClickListener(clickListener);
		btnIncrease.setOnClickListener(clickListener);
		this.aItem = aItem;
	}
	
	public void updateOrderedQuatity() {
		this.orderedQuy=Integer.parseInt(mText05.getText().toString());
	}
	
	private void updateOrderQtyAtLine(int line, int newQty){
		GlobalResource globalResource = GlobalResource.getInstance();
		Order order = globalResource.getOrder();
		order.updateOrderDetailQuaility(line, newQty);
	}
	
	final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int total=msg.arg1;
			mText05.setText(total+"");
			Order order=GlobalResource.getInstance().getOrder();
			txtTotal.setText(Util.formatCurrency(order.OrderAmount)+won);
			btnOrder.setEnabled(true);
			if(true)//msg.arg2==R.id.btnDecrease)
			{
				//TODO Need to check disable (-) button here again.
				if(total==orderedQuy)
					btnDecrese.setEnabled(false);
				if(total==0)
				{
					btnIncrease.setEnabled(false);
					
							//MyCardActivity mycart=(MyCardActivity)OrderIconTextView.this.context;
							//mycart.removeOrderItem(OrderIconTextView.this);
							return;
				
				}
			}
			if(true)//msg.arg2==R.id.btnIncrease)
			{
				//TODO Need to check disable (-) button here again.
				btnDecrese.setEnabled(true);
				
			}
		}
	};
	public int getLine()
	{
		return Integer.parseInt(aItem.getData(0));
	}
}