package cs174w14.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerOrder {
	int order_num;
	char loyalty_id;
	int shipping_handling;
	int subtotal;
	int total;
	String cust_id;
	Date order_date;
	
	List<StoreProduct> contents;
	LoyaltyClass loyaltyClass;
	
	public CustomerOrder(int order_num){
		this.order_num=order_num;
	}
	
	public CustomerOrder(int order_num, String cust_id, int subtotal, int shipping_handling, int total, char loyalty, Date order_date){
		this.order_num=order_num;
		this.cust_id=cust_id;
		this.subtotal=subtotal;
		this.shipping_handling=shipping_handling;
		this.total=total;
		this.loyalty_id=loyalty;
		this.order_date=order_date;
		this.loyaltyClass=new LoyaltyClass(loyalty_id);
	}
	
	public CustomerOrder(ResultSet rs) throws SQLException{
		fillFromResultSet(rs);
	}



	public int getOrderNum() {
		return order_num;
	}

	public LoyaltyClass getLoyalty() {
		return loyaltyClass;
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

	public Date getOrderDate() {
		return order_date;
	}

	public List<StoreProduct> getContents() {
		if(this.contents==null){
			try{
				loadContents();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		return contents;
	}
	
	public void fillStub() throws SQLException{
		ResultSet me = ConnectionManager.runQuery(
				"SELECT * FROM Orders WHERE order_num='"+this.order_num+"'");
		me.first();
		fillFromResultSet(me);
	}
	
	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.order_num=rs.getInt("order_num");
		this.cust_id=rs.getString("cust_id");
		this.subtotal=rs.getInt("subtotal");
		this.shipping_handling=rs.getInt("ship_hand");
		this.total=rs.getInt("total");
		this.loyalty_id=rs.getString("loyalty").charAt(0);
		this.order_date=rs.getDate("order_date");
	}
	
	private void loadContents() throws SQLException{
		this.contents = new ArrayList<StoreProduct>();
		ResultSet cont = ConnectionManager.runQuery(
				"SELECT * FROM Order_Items WHERE order_num='"+this.order_num+"'");
		while(cont.next()){
			StoreProduct p = new StoreProduct(cont.getString("stock_num"));
			p.setOldPrice(cont.getInt("price"));
			this.contents.add(p);
		}
	}
	
}
