package com.alovi.data;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class ServiceSearchData {

	@SerializedName("response")
	public Response response;
	
	public static class Response {
		@SerializedName("status")
		public boolean status;
		
		@SerializedName("data")
		public List<ServiceSearch> serviceSearch;
		
		public static class ServiceSearch {

			@SerializedName("serviceID")
			public String serviceId;
			
			@SerializedName("name")
			public String name;
		}
		
		@SerializedName("message")
		public String message;
	}
}