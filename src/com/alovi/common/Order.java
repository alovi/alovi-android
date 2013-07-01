package com.alovi.common;

import java.util.ArrayList;
import java.util.List;

import com.alovi.data.GlobalResource;


public class Order {
	public int OrderId;
	public String OrderNo;
	public String OrderDigiCode;
	public String OrdererUserName;
	public String OrderDesc;
	public String OrderRemark;
	public String OrderState;
	public String OrderStateName;
	public double OrderCost;
	public double OrderTax;
	public double DiscountAmt;
	public double OrderAmount;
	public String StoreCode;
	public String TableCode;
	public String StoreName;
	public List<OrderDetail> OrderDetails = new ArrayList<OrderDetail>();
	public int Revision;
	
	private boolean changed;
	
	
	
	
	
	/**
	 * @return the changed
	 */
	public boolean isChanged() {
		return changed;
	}
	/**
	 * @param changed the changed to set
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
	/**
	 * Mark this Order and all it's line is sync with server
	 */
	public void markOrderIsSync(){
		for(int i = 0; i < OrderDetails.size(); i++){
			OrderDetail orderDetail = OrderDetails.get(i);
			orderDetail.setOrderedQty(orderDetail.ProductQty);
		}
	}
	/**
	 * Add Product to Order:
	 * - If ProductID and UnitName exists on Order - update this OrderDetail
	 * - Else add new OrderDetail
	 * @param productId
	 * @param productCode
	 * @param unitName
	 * @param productPrice
	 */
	public void addProduct(Product product, String unitName, int productQty, double productPrice){
		int line = checkOrderDetailExist(product.ProductId, unitName);
		// Not exists
		if(line == -1){
			// Creare new OrderDetail
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.ProductId = product.ProductId;
			orderDetail.ProductName = product.ProdName;
			orderDetail.ProductCode = product.ProdCode;
			orderDetail.ProductQty = productQty;
			orderDetail.ProductPrice = productPrice;
			orderDetail.UnitName = unitName;
			orderDetail.ProductCost = productQty * productPrice;
			orderDetail.DiscountRate = 0;
			orderDetail.DiscountAmt = 0;
			orderDetail.ProductAmount = orderDetail.ProductCost - orderDetail.DiscountAmt;
			
			addOrderDetail(orderDetail);
		}
		else{ // Exists
			// Get the old OrderDetail
			OrderDetail orderDetail = getOrderDetail(line);
			// Add more qty
			orderDetail.ProductQty += productQty;
			orderDetail.ProductCost = orderDetail.ProductQty * productPrice;
			orderDetail.ProductAmount = orderDetail.ProductCost - orderDetail.DiscountAmt;
			// Don't need because the current line Do update
			
			//this.DiscountAmt += orderDetail.DiscountAmt;
			this.OrderAmount += productQty * productPrice;
			this.OrderCost += productQty * productPrice;
			
			//updateOrderDetail(line, orderDetail);
		}
		this.changed = true;
	}
	
	/**
	 * Check to see the productId and unitName does exists on Order
	 * @param productId The product ID
	 * @param unitName The Unit Name 
	 * @return The line (-1 if not found)
	 */
	private int checkOrderDetailExist(int productId, String unitName){
		int line = -1;
		for(int i = 0; i < OrderDetails.size(); i++){
			OrderDetail orderDetail = OrderDetails.get(i);
			if(productId == orderDetail.ProductId && unitName.equalsIgnoreCase(orderDetail.UnitName)){
				line = i;
				break;
			}
		}
		return line;
	}
		
	
	/**
	 * Add OrderDetail
	 * @param orderDetail
	 */
	public void addOrderDetail(OrderDetail orderDetail){
		
		this.OrderDetails.add(orderDetail);
		this.DiscountAmt += orderDetail.DiscountAmt;
		this.OrderAmount += orderDetail.ProductAmount;
		this.OrderCost += orderDetail.ProductCost;
		this.changed = true;
	}
	
	/**
	 * Add OrderDetail at line
	 * @param orderDetail
	 */
	public void addOrderDetail(int line,OrderDetail orderDetail){
		this.OrderDetails.add(line, orderDetail);
		this.DiscountAmt += orderDetail.DiscountAmt;
		this.OrderAmount += orderDetail.ProductAmount;
		this.OrderCost += orderDetail.ProductCost;
		this.changed = true;
	}
	/**
	 * Remove {@link OrderDetail} at line (0 based index)
	 * @param line The line index
	 */
	public void removeOrderDetail(int line){
		if(line <= this.OrderDetails.size()){
			OrderDetail orderDetail = this.OrderDetails.get(line);
			this.DiscountAmt -= orderDetail.DiscountAmt;
			this.OrderAmount -= orderDetail.ProductAmount;
			this.OrderCost -= orderDetail.ProductCost;
			this.OrderDetails.remove(line);
			this.changed = true;
		}
	}
	
	public OrderDetail getOrderDetail(int line){
		OrderDetail orderDetail = null;
		if(line < this.OrderDetails.size()){
			orderDetail = OrderDetails.get(line);
		}
		return orderDetail;
	}
	public List<OrderDetail>getOrderDetails()
	{
		return this.OrderDetails;
	}
	
	public void updateOrderDetailQuaility(int line, int newQuality){
		if(newQuality == 0){
			removeOrderDetail(line);
		}else{
			// Get the old one
			OrderDetail orderDetail = getOrderDetail(line);
			if(orderDetail != null){
				if(orderDetail.ProductQty != newQuality){
					int delta = newQuality - orderDetail.ProductQty;
					orderDetail.ProductCost += (delta * orderDetail.ProductPrice);
					orderDetail.DiscountAmt = 0;
					orderDetail.ProductAmount = orderDetail.ProductCost - orderDetail.DiscountAmt;
					
					orderDetail.ProductQty = newQuality;
					GlobalResource.getInstance().getOrder().OrderAmount += (delta * orderDetail.ProductPrice); 
					//updateOrderDetail(line, orderDetail);
					
					
				}
			}
		}
		
		this.changed = true;
	}

}
