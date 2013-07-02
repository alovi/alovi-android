package com.alovi.controller;

import com.alovi.common.AVLog;
import com.alovi.common.Order;
import com.alovi.common.OrderProcessState;
import com.alovi.common.ServiceFactory;
import com.alovi.data.DiscountData;
import com.alovi.data.GlobalResource;
import com.alovi.data.GlobalVariables;
import com.alovi.data.MoneyData;
import com.alovi.data.OrderData;
import com.alovi.data.PaymentData;
import com.alovi.webservice.APIServiceVariables;
import com.alovi.webservice.RequestMethod;
import com.alovi.webservice.RestClient;
import com.google.gson.Gson;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class OrderController {

	/**
	 * @param string pvAmount So tien nap.
	 * @param string serviceID Ma dich vu.
	 * 		Khi thanh toan topup dien thoai co the khong can cung cap.
	 * 		De biet duoc serviceID co the vao function get_discounts cua API USER.
	 * @param string pvAccount So dien thoai/tai khoan game.
	 * 		Khi thanh toan the cao dien thoai hoac the game thi khong can cung cap.
	 * @return object{ status=1, data } thanh cong.
	 * object{ status=0, error } khong thanh cong.
	 */
	public static OrderData createOrder(int type, String amount, String serviceId, String account, boolean isTraTruoc) {
		String url = "";
		if(isTraTruoc) url = APIServiceVariables.getInstance().URL_PAYMENT();
		else url = APIServiceVariables.getInstance().URL_PAYMENT_TRASAU();
		RestClient service=new RestClient(url);
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("pvAmount", amount);
		switch(type){
		case 1://topup dien thoai
			service.addParam("pvAccount", account);
			break;
		case 2://card dien thoai, vcoin, gate fpt, zingxu, softnyx
			service.addParam("serviceID", serviceId);
			break;
		case 3://asia, vcoin topup
			service.addParam("serviceID", serviceId);
			service.addParam("pvAccount", account);
			break;
		default:
			break;
		}
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.PUT);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
		        OrderData resultOrder = gson.fromJson(service.getResponse(), OrderData.class);
				return resultOrder;
			}
			return null;
		} catch (Exception ex) {
			AVLog.WriteLog(ex.getMessage());
			return null;
		}
	}

	public static String updateOrder(String mobilePhone, Order order) {
		String orderStatus = ServiceFactory.getOrderService().updateOrder(
				mobilePhone, order);
		order.OrderStateName = orderStatus;

		order.setChanged(false);
		order.markOrderIsSync();
		order.Revision++;
		return orderStatus;
	}

	/**
	 * @param integer id Ma giao dich.
	 * @return object{ status=1, data } thanh cong.
	 * object{ status=0, error } khong thanh cong.
	 * 
	 *
	 */
	public static PaymentData getCurrentOrder(String transactionId) {
		if(transactionId==null||transactionId.length()==0) return null;
		GlobalResource globalResource = GlobalResource.getInstance();
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_PAYMENT());
		service.addParam("q", "android");
		service.addParam("id", transactionId);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				PaymentData paymentData = gson.fromJson(service.getResponse(), PaymentData.class);
				return paymentData;
			}
			return null;
		} catch (Exception ex) {
			AVLog.WriteLog(ex.getMessage());
			return null;
		}
	}

	public static PaymentData getPayments(String take, String page) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GET_PAYMENTS());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("take", take);
		service.addParam("page", page);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				PaymentData paymentData = gson.fromJson(service.getResponse(), PaymentData.class);
				return paymentData;
			}
			return null;
		} catch (Exception ex) {
			AVLog.WriteLog(ex.getMessage());
			return null;
		}
	}

	/**
	 * serviceID --> function get_discounts of API USER.
	 * or pvAccount
	 */
	public static DiscountData getDiscountPayment (String serviceId, String account) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GET_DISCOUNT_PAYMENT());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		if(serviceId!=null)
			service.addParam("serviceId", serviceId);
		if(account!=null)
			service.addParam("pvAccount", account);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				DiscountData discountData = gson.fromJson(service.getResponse(), DiscountData.class);
				return discountData;
			}
			return null;
		} catch (Exception ex) {
			AVLog.WriteLog(ex.getMessage());
			return null;
		}
	}

	/**
	 * @param mobile string
	 */
	public static String checkMobile (String numberPhone) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_CHECK_MOBILE());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("mobile", numberPhone);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				return service.getResponse();
			}
			return null;
		} catch (Exception ex) {
			AVLog.WriteLog(ex.getMessage());
			return null;
		}
	}

	/**
	 * type : mobile_card, game, zingxu_card, viettel, trasau, mobile_topup
	 */
	public static MoneyData getListMoney (String type) {
		RestClient service=new RestClient(APIServiceVariables.getInstance().URL_GET_LIST_MONEY());
		GlobalResource globalResource = GlobalResource.getInstance();
		service.addParam("q", "android");
		service.addParam("type", type);
		service.addBasicAuthentication(globalResource.getUserName(), globalResource.getPassword());
		try {
			service.execute(RequestMethod.GET);
			if(service.getResponseCode() == 200) {
				Gson gson = new Gson();
				MoneyData moneyData = gson.fromJson(service.getResponse(), MoneyData.class);
				return moneyData;
			}
			return null;
		} catch (Exception ex) {
			AVLog.WriteLog(ex.getMessage());
			return null;
		}
	}

	/**
	 * Sync Order with Server
	 * 
	 * @param currentOrder
	 *            The local Order
	 * @return The synced Order
	 */
	public static Order syncOrderWithServer(Order currentOrder) {
		Order result = currentOrder;
		if (currentOrder.OrderId > 0) {
			int revision = ServiceFactory.getOrderService().getOrderRevision(
					currentOrder.OrderId);

			// If the local Order is older than Server
			if (currentOrder.Revision < revision) {
				
				// Get the Server license
				Log.i("OrderController", " - sync with server");
				Order serverOrder = ServiceFactory.getOrderService().getOrder(
						currentOrder.OrderId);
				
				if (serverOrder != null && serverOrder.OrderId > 0) {
					// TODO Temp overwrite the local Order version with the
					// server version
					// need do merge here
					Log.i("OrderController", 
							" - sync - replace with server version");
					
					if (serverOrder.OrderState.equals("Paid")) {
						// TODO Temp overwrite the local Order version with the
						// server version
						// need do merge here
						Log.i("OrderController", " - Paid already");
						
						result = ServiceFactory.getOrderService().getCurrentOrder(currentOrder.OrdererUserName, currentOrder.OrderDigiCode);
					}
					else {
						result = serverOrder;
					}
						
					result.markOrderIsSync();
					result.setChanged(false);
					GlobalResource.getInstance().setOrder(result);					
					
				}
			}
		}

		return result;
	}

	public static String fakePayOrder(String mobilePhone, Order order) {
		String result = ServiceFactory.getOrderService().fakePayOrder(
				mobilePhone, order.OrderId);
		if (OrderProcessState.ProcessDone.name().equalsIgnoreCase(result)) {
			// order.OrderState = OrderState.Paid.name();
			// order.OrderStateName = OrderState.Paid.name();
			order = null;
			// Finish Order
			GlobalResource.getInstance().setOrder(null);
		}
		return result;
	}

	public static void saveOrderToMemory(Context context) {
		Order order = GlobalResource.getInstance().getOrder();
		if (order != null) {
			// Save the phone and digital code to global resourse
			SharedPreferences settings = context.getSharedPreferences(
					GlobalVariables.PREFS_ORDER_NAME, 0);
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(GlobalVariables.USER_PHONE_NUMBER,
					order.OrdererUserName);
			editor.putString(GlobalVariables.STORECODE, order.StoreCode);
			editor.putString(GlobalVariables.SUBCODE, order.TableCode);
			editor.commit();

			// Call to Server to save Order

			// Check to see it is a new Order
			// TODO Need think more
			/*
			 * if (order.OrderId == 0) {
			 * OrderController.createOrder(order.OrdererMobile, order); } else {
			 * // Check to see the Order is changed if (order.isChanged()) { //
			 * Update Order to Server
			 * OrderController.updateOrder(order.OrdererMobile, order); } }
			 */
		}
	}

}
