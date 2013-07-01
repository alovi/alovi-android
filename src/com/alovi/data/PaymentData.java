package com.alovi.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/*
 * {"response":{"status":true,"data":{"id":"3853","type":"1","pvAccount":"01666625366",
 * "serviceID":"7885","receiptID":"46","currentDate":"2013-01-15 10:01:06",
 * "terminalID":"9369860","pvAmount":"5000","status":"2","result":"0","bill_address":"",
 * "getvat":"0","ip_address":"115.77.134.50","description":null,"pay_bank_id":null,
 * "receipt_bank_id":null,"number_of_order":null,"user_id_approve":null,
 * "discount_transfer_id":null,"modify":"2013-01-15 10:28:37","card_result":null,
 * "gateway_id":"1","transactionID":"3853","service_name":"Viettel","service_group_id":"1",
 * "result_name":"OK","username":"tainguyenchi","username_approved":null,"money":"475675",
 * "discount_money":"225","gateway_name":"Simpay"}}}
 */
public class PaymentData {
	
	@SerializedName("response")
	public Response response;
	
	public static class Response {
		@SerializedName("status")
		public boolean status;
		
		@SerializedName("data")
		public List<Payment> payment;
		
		public static class Payment {
		
			@SerializedName("id")
			public String Id;
			
			@SerializedName("type")
			public String type;
			
			@SerializedName("pvAccount")
			public String account;
		
			@SerializedName("serviceID")
			public String serviceId;
		
			@SerializedName("receiptID")
			public String receiptId;
		
			@SerializedName("currentDate")
			public String currentDate;
		
			@SerializedName("terminalID")
			public String terminalId;
			
			@SerializedName("pvAmount")
			public String amount;
			
			@SerializedName("status")
			public String status;
			
			@SerializedName("result")
			public String result;
			
			@SerializedName("bill_address")
			public String bill_address;
		
			@SerializedName("getvat")
			public String getvat;
		
			@SerializedName("ip_address")
			public String ip_address;
		
			@SerializedName("description")
			public String description;
		
			@SerializedName("pay_bank_id")
			public String payBankId;
		
			@SerializedName("receipt_bank_id")
			public String receiptBankId;
		
			@SerializedName("number_of_order")
			public String numberOfOrder;
		
			@SerializedName("user_id_approve")
			public String userIdApprove;
		
			@SerializedName("discount_transfer_id")
			public String discountTransferId;
		
			@SerializedName("modify")
			public String modify;
		
			@SerializedName("card_result")
			public String cardResult;
			
			@SerializedName("gateway_id")
			public String gateway_id;
			
			@SerializedName("transactionID")
			public String transactionId;
		
			@SerializedName("service_name")
			public String serviceName;
		
			@SerializedName("service_group_id")
			public String serviceGroupId;
		
			@SerializedName("result_name")
			public String resultName;
		
			@SerializedName("username")
			public String username;
			
			@SerializedName("username_approved")
			public String usernameApproved;
			
			@SerializedName("money")
			public String money;
			
			@SerializedName("discount_money")
			public String discountMoney;
		
			@SerializedName("gateway_name")
			public String gatewayName;
		}
		
		@SerializedName("message")
		public String message;
	}
}