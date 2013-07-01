package com.alovi.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class BankData {

	@SerializedName("response")
	public Response response;
	
	public static class Response {
		@SerializedName("status")
		public boolean status;
		
		@SerializedName("data")
		public List<Bank> bank;
		
		public static class Bank {
			
			@SerializedName("id")
			public String id;
			
			@SerializedName("name")
			public String name;
			
			@SerializedName("status")
			public String status;
		}
		
		@SerializedName("message")
		public String message;
	}
}