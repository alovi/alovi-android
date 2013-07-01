package com.alovi.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class MoneyData {
	
	@SerializedName("response")
	public Response response;
	
	public static class Response {
		@SerializedName("status")
		public boolean status;
		
		@SerializedName("data")
		public List<Money> money;
		
		public static class Money {
			
			@SerializedName("value")
			public String value;
			
			@SerializedName("text")
			public String text;
		}
		
		@SerializedName("message")
		public String message;
	}
}