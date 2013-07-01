package com.alovi.common;


/**
 * The OrderDetail class represents on Order Line on Order Master
 *
 */
public class OrderDetail {
	public int OrderId;
	public int ProdSeq;
	public int ProductId;
	public String ProductCode;
	public String ProductName;
	public int ProductQty;
	public String UnitName;
	public double ProductPrice;
	public double ProductCost;
	public double DiscountRate;
	public double DiscountAmt;
	public double ProductAmount;
	
	
	// The qty is ordered in Service
	private int orderedQty;

	/**
	 * @return the orderedQty
	 */
	public int getOrderedQty() {
		return orderedQty;
	}

	/**
	 * @param orderedQty the orderedQty to set
	 */
	public void setOrderedQty(int orderedQty) {
		this.orderedQty = orderedQty;
	}
	
	
	
}

