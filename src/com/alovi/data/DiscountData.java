package com.alovi.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class DiscountData {

	@SerializedName("response")
	public Response response;
	
	public static class Response {
		@SerializedName("status")
		public boolean status;
		
		@SerializedName("data")
		public List<Discount> discount;
		
		public static class Discount {
			
			@SerializedName("discount")
			public String discount;
			
			@SerializedName("note")
			public String note;
			
			@SerializedName("created")
			public String created;
			
			@SerializedName("sales")
			public String sales;
			
			@SerializedName("service_name")
			public String serviceName;
			
			@SerializedName("group_id")
			public String groupId;
			
			@SerializedName("service_group_name")
			public String serviceGroupName;
			
			@SerializedName("code")
			public String code;
			
			@SerializedName("serviceID")
			public String serviceId;
		}
		
		@SerializedName("message")
		public String message;
	}
}