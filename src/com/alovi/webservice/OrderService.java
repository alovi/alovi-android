package com.alovi.webservice;

import com.alovi.common.Order;

/**
 * This interface defines all method need to work with Order
 */
public interface OrderService {
	
	/**
	 * Create Order (Cart) on Service
	 * @param mobiPhone	The mobile phone
	 * @param order	The Order
	 * @return	The created Order ID
	 */
	public int createOrder(String mobiPhone, Order order);
	/**
	 * Update Order on Service
	 * @param mobilePhone
	 * @param order
	 * @return The Order Processing Status
	 */
	public String updateOrder(String mobilePhone, Order order);
	
	/**
	 * Get the current submit Order but not completed
	 * @param mobilePhone
	 * @param digitalCode
	 * @return The current Order or null
	 */
	public Order getCurrentOrder(String mobilePhone, String digitalCode);
	/**
	 * Fake pay Order
	 * @param mobilePhone
	 * @param orderId
	 * @return true/false
	 */
	public String fakePayOrder(String mobilePhone, int orderId);
	/**
	 * Get Order by Order ID
	 * @param orderId Order ID
	 * @return
	 */
	public Order getOrder(int orderId);
	/**
	 * Get Order Revision
	 * @param orderId Order ID
	 * @return The Order revision
	 */
	public int getOrderRevision(int orderId);
	
	/**
	 * Get Order State
	 * @param orderId
	 * @return
	 */
	public String getOrderState(int orderId);
}
