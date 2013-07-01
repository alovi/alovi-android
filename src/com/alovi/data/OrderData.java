package com.alovi.data;

import com.google.gson.annotations.SerializedName;

/*
 * {"response":{"status":true,"data":{"status":1,"result":0,"paymentID":3857}}}
 */
public class OrderData {
	
	@SerializedName("response")
	public Response response;
	
	public static class Response {
		@SerializedName("status")
		public boolean status;
		
		@SerializedName("data")
		public Order order;
		
		public static class Order {
			
			@SerializedName("status")
			public int status;
		
			@SerializedName("result")
			public int result;
			
			@SerializedName("paymentID")
			public int paymentID;
		}
		
		@SerializedName("message")
		public String message;
	}
}