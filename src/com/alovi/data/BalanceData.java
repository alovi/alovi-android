package com.alovi.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BalanceData {
	
	@SerializedName("response")
	public Response response;
	
	public static class Response {
		@SerializedName("status")
		public boolean status;
		
		@SerializedName("data")
		public List<Balance> balance;
		
		public static class Balance {
			
			@SerializedName("money")
			public String money;
			
			@SerializedName("money_deposit")
			public String moneyDeposit;
			
			@SerializedName("money_using")
			public String moneyUsing;
			
			@SerializedName("money_discount")
			public String moneyDiscount;
		}
		
		@SerializedName("message")
		public String message;
	}
}