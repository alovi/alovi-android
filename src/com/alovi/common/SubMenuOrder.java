package com.alovi.common;

import com.alovi.R;
import com.alovi.activity.MyAsiaTopupActivity;
import com.alovi.activity.MyCardActivity;
import com.alovi.activity.MyGateFPTCardActivity;
import com.alovi.activity.MySoftnyxCardActivity;
import com.alovi.activity.MyTopupActivity;
import com.alovi.activity.MyVcoinCardActivity;
import com.alovi.activity.MyVcoinTopupActivity;
import com.alovi.activity.MyZingXuCardActivity;
import com.alovi.data.GlobalVariables;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;

public class SubMenuOrder extends SubMenu {

	public SubMenuOrder(Activity activity, Gallery gallery) {
		super(activity, gallery);
	}

	@Override
	public void loadMenuImage() {
		switch (GlobalVariables.TYPE_MENU){
		case 0: //Sumenu main
			pics = new Integer[] { R.drawable.game_icon, R.drawable.mobile_icon, R.drawable.pay_icon, R.drawable.other_services_icon};
			break;
		case 1: //Sumenu Game online
			pics = new Integer[] { R.drawable.menu_vcointopup_bt, R.drawable.menu_asiatopup_bt, R.drawable.menu_thevcoin_bt, R.drawable.menu_thegatefpt_bt, R.drawable.menu_thezingxu_bt, R.drawable.menu_thesoftnyx_bt };
			break;
		case 2: //Sumenu Dien thoai
			pics = new Integer[] { R.drawable.menu_topup_bt, R.drawable.menu_thecao_bt};
			break;
		case 3: //Submenu Thanh toan
			pics = new Integer[] { R.drawable.menu_dien_bt, R.drawable.menu_nuoc_bt, R.drawable.menu_dtcdcoday_bt, R.drawable.menu_dtcdkhongday_bt, R.drawable.menu_internet_bt, R.drawable.menu_truyenhinhcap_bt };
			break;
		default: //Dich vu khac
			break;
		}
	}

	@Override
	public void handleClickItem(AdapterView<?> arg0, View arg1, int index, long id) {
		switch (GlobalVariables.TYPE_MENU){
		case 0: //MainSubMenu
			Intent intent;
			switch (index) {
			case 0: //Game Online
				GlobalVariables.TYPE_MENU = 1;
				new SubMenuOrder(this.activity, gallery);
				break;
			case 1: //Dien thoai di dong
				GlobalVariables.TYPE_MENU = 2;
				new SubMenuOrder(this.activity, gallery);
				break;
			case 2: //Thanh toan
				GlobalVariables.TYPE_MENU = 3;
				new SubMenuOrder(this.activity, gallery);
				break;
			case 3: //Dich vu khac
				break;
			default:
				break;
			}
			break;
		case 1: //SubMenu Game Online
			GlobalVariables.TYPE_MENU = 0;
			switch (index) {
			case 0: //VcoinTopup
				intent = new Intent(this.activity, MyVcoinTopupActivity.class);
				this.activity.startActivity(intent);
				break;
			case 1: //AsiaTopup
				intent = new Intent(this.activity, MyAsiaTopupActivity.class);
				this.activity.startActivity(intent);
				break;
			case 2: //The Vcoin
				intent = new Intent(this.activity, MyVcoinCardActivity.class);
				this.activity.startActivity(intent);
				break;
			case 3: //The Gate FPT
				intent = new Intent(this.activity, MyGateFPTCardActivity.class);
				this.activity.startActivity(intent);
				break;
			case 4: //The ZingXu
				intent = new Intent(this.activity, MyZingXuCardActivity.class);
				this.activity.startActivity(intent);
				break;
			case 5: //The Softnyx
				intent = new Intent(this.activity, MySoftnyxCardActivity.class);
				this.activity.startActivity(intent);
				break;
			default:
				break;
			}
			break;
		case 2: //SubMenu Dien thoai di dong
			GlobalVariables.TYPE_MENU = 0;
			switch (index) {
			case 0: //Topup
				intent = new Intent(this.activity, MyTopupActivity.class);
				this.activity.startActivity(intent);
				break;
			case 1: //The Cao
				intent = new Intent(this.activity, MyCardActivity.class);
				this.activity.startActivity(intent);
				break;
			default:
				break;
			}
			break;
		case 3: //SubMenu Thanh toan
			GlobalVariables.TYPE_MENU = 0;
			switch (index) {
			case 0: //Dien
				intent = new Intent(this.activity, MyCardActivity.class);
				this.activity.startActivity(intent);
				break;
			case 1: //Nuoc
				intent = new Intent(this.activity, MyCardActivity.class);
				this.activity.startActivity(intent);
				break;
			case 2: //Dien thoai co dinh co day
				intent = new Intent(this.activity, MyCardActivity.class);
				this.activity.startActivity(intent);
				break;
			case 3: //Dien thoai co dinh ko day
				intent = new Intent(this.activity, MyCardActivity.class);
				this.activity.startActivity(intent);
				break;
			case 4: //Internet(ADSL/Cap Quang)
				intent = new Intent(this.activity, MyCardActivity.class);
				this.activity.startActivity(intent);
				break;
			case 5: //Truyen hinh cap
				intent = new Intent(this.activity, MyCardActivity.class);
				this.activity.startActivity(intent);
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
	}
}