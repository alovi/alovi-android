package com.alovi.common;

import com.alovi.R;
import com.alovi.activity.MoneyTransferActivity;
import com.alovi.activity.MyWalletActivity;
import com.alovi.activity.OtherTransferActivity;
import com.alovi.activity.ThongKeDoanhThuActivity;
import com.alovi.activity.TransactionHistoryActivity;
import com.alovi.activity.TransferActivity;
import com.alovi.data.GlobalVariables;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;

public class SubMenuWallet extends SubMenu{

	public SubMenuWallet(Activity activity, Gallery gallery) {
		super(activity, gallery);
	}

	@Override
	public void loadMenuImage() {
		switch (GlobalVariables.TYPE_MENU){
		case 0: //Sumenu main
			pics = new Integer[] { R.drawable.menu_truyvansodu_bt, R.drawable.menu_chuyenkhoan_bt, R.drawable.menu_naptien_bt, R.drawable.menu_baocaodoanhthu_bt, R.drawable.menu_lichsugiaodich_bt};
			break;
		case 1: //Sumenu Nap tien
			pics = new Integer[] { R.drawable.menu_internetbanking_bt, R.drawable.menu_atm_bt, R.drawable.menu_uynhiemchi_bt, R.drawable.menu_noptienmat_bt};
			break;
		case 2: //Sumenu Bao cao doanh thu
			pics = new Integer[] { R.drawable.menu_thongkedoanhthu_bt, R.drawable.menu_bieudobanh_bt, R.drawable.menu_bieudocot_bt, R.drawable.menu_dudoan_bt};
			break;
		default:
			break;
		}
	}

	@Override
	public void handleClickItem(AdapterView<?> arg0, View arg1, int index, long id) {
		switch (GlobalVariables.TYPE_MENU){
		case 0: //MainSubMenu
			Intent intent;
			switch (index) {
			case 0: //Truy van so du
				GlobalVariables.TYPE_MENU = 0;
				intent = new Intent(this.activity, MyWalletActivity.class);
				this.activity.startActivity(intent);
				break;
			case 1: //Chuyen khoan
				GlobalVariables.TYPE_MENU = 0;
				intent = new Intent(this.activity, TransferActivity.class);
				this.activity.startActivity(intent);
				break;
			case 2: //Nap tien
				GlobalVariables.TYPE_MENU = 1;
				new SubMenuWallet(this.activity, gallery);
				break;
			case 3: //Bao cao doanh thu
				GlobalVariables.TYPE_MENU = 2;
				new SubMenuWallet(this.activity, gallery);
				break;
			case 4: //Lich su giao dich
				GlobalVariables.TYPE_MENU = 0;
				intent = new Intent(this.activity, TransactionHistoryActivity.class);
				break;
			default:
				break;
			}
			break;
		case 1: //SubMenu Nap tien
			GlobalVariables.TYPE_MENU = 0;
			switch (index) {
			case 0: //Internet Banking
				intent = new Intent(this.activity, OtherTransferActivity.class);
				this.activity.startActivity(intent);
				break;
			case 1: //ATM
				intent = new Intent(this.activity, OtherTransferActivity.class);
				this.activity.startActivity(intent);
				break;
			case 2: //Uy nhiem chi
				intent = new Intent(this.activity, OtherTransferActivity.class);
				this.activity.startActivity(intent);
				break;
			case 3: //Nap tien mat
				intent = new Intent(this.activity, MoneyTransferActivity.class);
				this.activity.startActivity(intent);
				break;
			default:
				break;
			}
			break;
		case 2: //SubMenu Bao cao doanh thu
			GlobalVariables.TYPE_MENU = 0;
			switch (index) {
			case 0: //Thong ke doanh thu
				intent = new Intent(this.activity, ThongKeDoanhThuActivity.class);
				this.activity.startActivity(intent);
				break;
			case 1: //Bieu do banh
				intent = new Intent(this.activity, ThongKeDoanhThuActivity.class);
				this.activity.startActivity(intent);
				break;
			case 2: //Bieu do cot
				intent = new Intent(this.activity, ThongKeDoanhThuActivity.class);
				this.activity.startActivity(intent);
				break;
			case 3: //Du doan
				intent = new Intent(this.activity, ThongKeDoanhThuActivity.class);
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