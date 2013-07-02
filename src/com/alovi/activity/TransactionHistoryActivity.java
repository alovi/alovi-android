package com.alovi.activity;

import java.util.ArrayList;
import java.util.List;

import com.alovi.R;
import com.alovi.common.AVLog;
import com.alovi.common.MainMenu;
import com.alovi.controller.OrderController;
import com.alovi.data.PaymentData;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class TransactionHistoryActivity extends BaseActivity {

	private MainMenu menu;
	private ListView lvCategory;
	private ArrayAdapter<String> listAdapter ;
	private PaymentData state;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try{
			setContentView(R.layout.transaction_history);
			menu = new MainMenu(this, true);
			menu.setTextMainTitle(getString(R.string.txt_transaction_history_title).toString());
			
			LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
			LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, 370);
			linearLayout1.setLayoutParams(layoutParam);
			
			lvCategory = (ListView) findViewById(R.id.lvOder);
			try{
				state = OrderController.getPayments("20", "1");
			}catch(Exception ex){}
			
			if(state==null){
				toast("not success!");
				return;
			}
			List<PaymentData.Response.Payment> datas = state.response.payment;
			ArrayList<String> list = new ArrayList<String>();
			for (PaymentData.Response.Payment data : datas) {
				list.add(data.serviceId + " | " + data.payBankId + " | " + data.amount + " | " + data.serviceName);
			}
		    listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
		    lvCategory.setAdapter( listAdapter );
		}catch(Exception ex){
			AVLog.WriteLog(ex.getMessage());
			Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
}