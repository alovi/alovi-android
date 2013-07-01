package com.alovi.controller;

import com.alovi.data.GlobalResource;
import com.alovi.data.RechargeData;
import com.alovi.data.ServiceSearchData;
import com.alovi.webservice.APIServiceVariables;
import com.alovi.webservice.RequestMethod;
import com.alovi.webservice.RestClient;
import com.google.gson.Gson;

public class TransactionController {

	/*
	 * @param type Loai tim kiem.
	 * 		payment (default), recharge, transfer.
	 * 		payment : Dich vu thanh toan.
	 * 		recharge : Phuong thuc gui (nap) tien.
	 * 		transfer : Dich vu chuyen tien.
	 * @return object{ status=1, data } thanh cong.
	 * object{ status=0, error } khong thanh cong.
	 */
	public static ServiceSearchData getServiceForSearch(String type) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GET_SERVICE_FOR_SEARCH());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("type", type);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				ServiceSearchData ServiceSearchData = gson.fromJson(service.getResponse(), ServiceSearchData.class);
				return ServiceSearchData;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static RechargeData getRecharges(String take, String page) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GET_RECHARGE());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("take", take);
		service.addParam("page", page);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				RechargeData rechargeData = gson.fromJson(service.getResponse(), RechargeData.class);
				return rechargeData;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
}