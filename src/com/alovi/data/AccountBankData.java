package com.alovi.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class AccountBankData {

	@SerializedName("response")
	public Response response;
	
	public static class Response {
		@SerializedName("status")
		public boolean status;
		
		@SerializedName("data")
		public List<AccountBank> accountBank;
		
		public static class AccountBank {
			
			@SerializedName("bank_account")
			public String bankAccount;
			
			@SerializedName("name")
			public String name;
		}
		
		@SerializedName("message")
		public String message;
	}
}