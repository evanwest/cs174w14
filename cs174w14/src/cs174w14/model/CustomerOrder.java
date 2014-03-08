package cs174w14.model;

import java.util.List;

public class CustomerOrder {
	int order_num;
	char loyalty;
	int shipping_handling;
	int subtotal;
	int total;
	String cust_id;
	String order_date;
	
	List<Product> contents;
	
	public CustomerOrder(int order_num){
		this.order_num=order_num;
	}
	
	public CustomerOrder(int order_num, String cust_id, int subtotal, int shipping_handling, int total, char loyalty, String order_date){
		this.order_num=order_num;
		this.cust_id=cust_id;
		this.subtotal=subtotal;
		this.shipping_handling=shipping_handling;
		this.total=total;
		this.loyalty=loyalty;
		this.order_date=order_date;
	}

	public int getOrderNum() {
		return order_num;
	}

	public char getLoyalty() {
		return loyalty;
	}

	public int getShippingHandling() {
		return shipping_handling;
	}

	public int getSubtotal() {
		return subtotal;
	}

	public int getTotal() {
		return total;
	}

	public String getCustId() {
		return cust_id;
	}

	public String getOrderDate() {
		return order_date;
	}

	public List<Product> getContents() {
		return contents;
	}
	
	
}
