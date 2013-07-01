package com.alovi.common;

import com.alovi.webservice.OrderService;
import com.alovi.webservice.OrderServiceImpl;

/**
 * This class use for get all service implement class
 *
 */
public class ServiceFactory {

	private static OrderService orderService;
	
	public static OrderService getOrderService(){
		if(orderService == null){
			orderService = new OrderServiceImpl();
		}
		return orderService;
	}
}
