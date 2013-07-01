package com.alovi.common;

import com.alovi.R;
import com.alovi.activity.AboutActivity;
import com.alovi.activity.ChangePasswordActivity;
import com.alovi.activity.ConfigActivity;
import com.alovi.activity.DiscountsActivity;
import com.alovi.activity.MyInfoActivity;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;

public class SubMenuAccount extends SubMenu {

	public SubMenuAccount(Activity activity, Gallery gallery) {
		super(activity, gallery);
	}

	@Override
	public void loadMenuImage() {
		pics = new Integer[] { R.drawable.menu_thongtintaikhoan_bt, R.drawable.menu_doimatkhau_bt,
				R.drawable.menu_cauhinhphanmem_bt, R.drawable.menu_gioithieu_bt, R.drawable.menu_mucchietkhau_bt};
	}

	@Override
	public void handleClickItem(AdapterView<?> arg0, View arg1, int index, long id) {
		Intent intent;
		switch (index) {
		case 0: //Thong tin tai khoan
			intent = new Intent(this.activity, MyInfoActivity.class);
			this.activity.startActivity(intent);
			break;
		case 1: //Doi mat khau
			intent = new Intent(this.activity, ChangePasswordActivity.class);
			this.activity.startActivity(intent);
			break;
		case 2: //Cau hinh phan mem
			intent = new Intent(this.activity, ConfigActivity.class);
			this.activity.startActivity(intent);
			break;
		case 3: //Gioi thieu
			intent = new Intent(this.activity, AboutActivity.class);
			this.activity.startActivity(intent);
			break;
		case 4: //Muc chiet khau
			intent = new Intent(this.activity, DiscountsActivity.class);
			this.activity.startActivity(intent);
			break;
		default:
			break;
		}
	}
}