package com.alovi.data;

import com.google.gson.annotations.SerializedName;

public class UserData {

	@SerializedName("response")
	public Response response;
	
	public static class Response {
		@SerializedName("status")
		public boolean status;
		
		@SerializedName("data")
		public User user;
		
		public static class User {
			
			@SerializedName("user_id")
			public String userId;
	
			@SerializedName("username")
			public String userName;
	
			@SerializedName("password")
			public String password;

			@SerializedName("email")
			public String email;

			@SerializedName("first_name")
			public String FirstName;

			@SerializedName("last_name")
			public String LastName;

			@SerializedName("phone")
			public String MobilePhone;

			@SerializedName("group_name")
			public String group_name;

			@SerializedName("bank_name")
			public String bank_name;

			@SerializedName("bank_account_number")
			public String bank_account_number;

			@SerializedName("bank_account_name")
			public String bank_account_name;

			@SerializedName("created")
			public String created;

			@SerializedName("last_login")
			public String last_login;

			@SerializedName("money")
			public String money;
		}
		
		@SerializedName("message")
		public String message;
	}
}