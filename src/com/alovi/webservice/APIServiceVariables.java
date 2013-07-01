package com.alovi.webservice;

public class APIServiceVariables {
	private static APIServiceVariables aPIServiceVariables = null;
	private String HOST = "https://alovi.com.vn";
	private String HOSTIP = "https://192.168.1.17";//"https://116.118.119.200";
	private String SERVICE_CONTEXT = "/index.php/api";
	private String SERVICE_URL = HOST + SERVICE_CONTEXT;
	
	public static APIServiceVariables getInstance() {
		if (aPIServiceVariables == null) {
			aPIServiceVariables = new APIServiceVariables();
		}
		return aPIServiceVariables;
	}
	
	public void setHOST(String host){
		this.HOST = host;
		this.SERVICE_URL = this.HOST + this.SERVICE_CONTEXT;
	}
	
	public void setHOSTIP(String hostip){
		this.HOSTIP = hostip;
		this.SERVICE_URL = this.HOST + this.SERVICE_CONTEXT;
	}
	
	public void setSERVICE_CONTEXT(String servicecontext){
		this.SERVICE_CONTEXT = servicecontext;
		this.SERVICE_URL = this.HOST + this.SERVICE_CONTEXT;
	}
	
	public String HOST(){
		return HOST;
	}
	
	public String HOSTIP(){
		return HOSTIP;
	}
	
	public String SERVICE_CONTEXT(){
		return SERVICE_CONTEXT;
	}
	
	public String SERVICE_URL(){
		return SERVICE_URL;
	}

	/*
	 * Payment
	 */
	public String URL_PAYMENT() {
		return SERVICE_URL + "/pay/payment.json";
	}
	public String URL_PAYMENT_TRASAU(){
		return SERVICE_URL + "/pay/payment/is_trasau/1?format=json";
	}
	public String URL_CHECK_MOBILE(){
		return SERVICE_URL + "/pay/check_mobile.json";
	}
	public String URL_GET_DISCOUNT_PAYMENT() {
		return SERVICE_URL + "/pay/get_discount_payment.json";
	}
	public String URL_GET_LIST_MONEY() {
		return SERVICE_URL + "/pay/get_list_money.json";
	}
	
	/*
	 * User
	 */
	public String URL_GETUSERS() {
		return SERVICE_URL + "/user/info.json";
	}
	public String URL_UPDATEUSER() {
		return SERVICE_URL + "/user/update.json";
		}
	public String URL_CREATEUSER() {
		return SERVICE_URL + "/user/register.json";
	}
	public String URL_CHECK_BALANCEUSER() {
		return SERVICE_URL + "/user/check_balance.json";
	}
	public String URL_CHANGE_PASSWORDUSER() {
		return SERVICE_URL + "/user/change_password.json";
	}
	public String URL_GET_DISCOUNTSUSER() {
		return SERVICE_URL + "/user/get_discounts.json";
	}
	public String URL_GET_BANKSUSER() {
		return SERVICE_URL + "/user/get_banks.json";
	}
	public String URL_GET_ACCOUNT_ALOVI_ACCEPTUSER() {
		return SERVICE_URL + "/user/get_account_alovi_accepted.json";
	}
	public String URL_GET_RECHARGE_SERVICEUSER() {
		return SERVICE_URL + "/user/get_recharge_service.json";
	}
	public String URL_RECHARGEUSER() {
		return SERVICE_URL + "/user/recharge.json";
	}
	public String URL_TRANSFERUSER() {
		return SERVICE_URL + "/user/transfer.json";
	}

	/*
	 * 
	 */
	public String URL_GET_PHONEBOOK() {
		return SERVICE_URL + "/phonebook/get_phonebook.json";
	}
	
	/*
	 * 
	 */
	public String URL_GET_SERVICE_FOR_SEARCH() {
		return SERVICE_URL + "/transaction/get_service_for_search.json";
	}
	public String URL_GET_PAYMENTS() {
		return SERVICE_URL + "/transaction/get_payments.json";
	}
	public String URL_GET_RECHARGE() {
		return SERVICE_URL + "/transaction/get_recharges.json";
	}
	
	/*
	 * 
	 */
	public String URL_GET_REPORT_BUSINESS() {
		return SERVICE_URL + "/report/get_report_business.json";
	}

	/*
	 * 
	 */
	public String URL_GET_PROMOTIONS() {
		return SERVICE_URL + "/notification/get_promotions.json";
	}
	
	/*
	 * 
	 */
	public String URL_GET_USER() {
		return SERVICE_URL + "/users/getuser/%s?format=json";
	}
}