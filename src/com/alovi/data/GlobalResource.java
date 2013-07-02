package com.alovi.data;

import com.alovi.common.Order;

import android.graphics.Bitmap;

/**
 * This class uses for store on Global resource on application scope - Current
 * Order - Phone Number - Auth Code - Current store code - Current sub code
 */
public class GlobalResource {
	private static GlobalResource globalResource = null;

	private UserData user;
	private int userLogin = 0;
	private int screenWidth = 320;
	private int screenHeight = 480;
	private String orderCode;

	private String authCode;
	private Order order = null;

	private String storeCode;
	private String subCode;
	private String storeName;
	private Bitmap storeLogo;

	public static GlobalResource getInstance() {
		if (globalResource == null) {
			globalResource = new GlobalResource();
		}
		return globalResource;
	}

	public int getScreenWidthDefault() {
		return 320;
	}

	public int getScreenHeigthDefault() {
		return 480;
	}

	public void setScreenWidth(int screenWidth) {
		this.screenWidth = screenWidth;
	}

	public int getScreenWidth() {
		return this.screenWidth;
	}

	public void setScreenHeight(int screenHeight) {
		this.screenHeight = screenHeight;
	}

	public int getScreenHeigth() {
		return this.screenHeight;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public void setUserName(String userName) {
		this.user.response.user.userName = userName;
	}

	public String getUserName() {
		return this.user.response.user.userName;
	}

	public String getPassword() {
		return this.user.response.user.password;
	}

	public void setPassword(String password) {
		this.user.response.user.password = password;
	}

	public void setUserLogin(int userLogin) {
		this.userLogin = userLogin;
	}

	public int getUserLogin() {
		return this.userLogin;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public Order getOrder() {
		if (order == null) {
			order = new Order();
		}
		order.StoreCode = storeCode;
		order.OrderDigiCode = storeCode + subCode;
		order.TableCode = subCode;

		return order;
	}

	public void setOrder(Order order) {
		this.order = order;	
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		if(storeCode!=null&&!storeCode.isEmpty())
			this.storeCode = storeCode;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		if(storeName!=null &&!storeName.isEmpty())
		this.storeName = storeName;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		if(subCode!=null&&!subCode.isEmpty())
		this.subCode = subCode;
	}

	public Bitmap getStoreLogo() {
		return storeLogo;
	}

	public void setStoreLogo(Bitmap storeLogo) {
		// Clear the old bitmap for GC can collect it
		if (this.storeLogo != null) {
			this.storeLogo = null;
		}
		this.storeLogo = storeLogo;
	}
	
	public static void Release()
	{
		if(globalResource!=null)
			globalResource=null;
	}
}