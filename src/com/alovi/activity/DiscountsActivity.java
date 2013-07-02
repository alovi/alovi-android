package com.alovi.activity;

import java.util.ArrayList;
import java.util.List;

import com.alovi.R;
import com.alovi.common.MainMenu;
import com.alovi.controller.UserController;
import com.alovi.data.DiscountData;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

public class DiscountsActivity extends BaseActivity {

	private ListView lvCategory;
	private MainMenu menu;
	
	private ArrayAdapter<String> listAdapter ;

	DiscountData dd;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discounts);
		menu = new MainMenu(this, true);
		menu.setTextMainTitle(getString(R.string.txt_discounts_title).toString());
		menu.setButtonMycartOn();
		
		LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 370);
		linearLayout1.setLayoutParams(layoutParam);
		
		lvCategory = (ListView) findViewById(R.id.lvOder);
		dd = UserController.getDiscounts();
		if(dd==null){
			toast("not success!");
			return;
		}
		List<DiscountData.Response.Discount> datas = dd.response.discount;
		ArrayList<String> list = new ArrayList<String>();
		for (DiscountData.Response.Discount data : datas) {
			list.add(data.serviceId + " | " + data.discount + " | " + data.sales + " | " + data.serviceName);
		}
	    listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
	    lvCategory.setAdapter( listAdapter );
	    
	    
		/*try {
			showDialog(PROGRESS_DIALOG);
			new Thread(new Runnable() {
					@Override
					public void run() {
						dd = UserController.getDiscounts();
						Message msg=handle.obtainMessage();
						msg.obj=dd;
						handle.sendMessage(msg);
					}
				}).start();
		} catch (Exception ex) {
			dismissDialog(PROGRESS_DIALOG);
			messageBox(MessageTypes.Error, ex.getMessage().toString());
		}*/
	}
	
	/*final Handler handle = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dd=(DiscountData)msg.obj;
			if(dd==null){
				dismissDialog(PROGRESS_DIALOG);
				toast("not success!");
				return;
			}
			List<DiscountData.Response.Discount> datas = dd.response.discount;
			ArrayList<String> list = new ArrayList<String>();
			for (DiscountData.Response.Discount data : datas) {
				list.add(data.serviceId + " | " + data.discount + " | " + data.sales + " | " + data.serviceName);
			}
		    listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		    lvCategory.setAdapter( listAdapter );
			dismissDialog(PROGRESS_DIALOG);
		}
	};*/
}