package com.alovi.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RechargeMethodData {

	@SerializedName("response")
	public Response response;
	
	public static class Response {
		@SerializedName("status")
		public boolean status;
		
		@SerializedName("data")
		public List<RechargeMethod> rechargeMethod;
		
		public static class RechargeMethod {

			@SerializedName("id")
			public String id;
			
			@SerializedName("group_id")
			public String groupId;
			
			@SerializedName("code")
			public String code;
			
			@SerializedName("serviceID")
			public String serviceId;
			
			@SerializedName("name")
			public String name;
			
			@SerializedName("status")
			public String status;
			
			@SerializedName("created")
			public String created;
			
			@SerializedName("url")
			public String url;
			
			@SerializedName("color")
			public String color;
		}
		
		@SerializedName("message")
		public String message;
	}
}