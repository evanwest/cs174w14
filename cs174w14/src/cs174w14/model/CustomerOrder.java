package cs174w14.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CustomerOrder implements ModelDataObject {
	int order_num;
	char loyalty_id;
	int ship_hand_paid;
	int pct_ship_hand;
	int subtotal;
	int amt_ship_hand;
	int total;
	int pct_discount;
	String cust_id;
	Date order_date;

	Map<Product, Integer> contents;

	public CustomerOrder(int order_num){
		this.order_num=order_num;
	}

	public CustomerOrder(int order_num, String cust_id, char loyalty, int pct_discount, int pct_ship_hand, Date order_date){
		this.order_num=order_num;
		this.cust_id=cust_id;
		this.pct_discount=pct_discount;
		this.pct_ship_hand=pct_ship_hand;
		this.loyalty_id=loyalty;
		this.order_date=order_date;
		this.total=-1;
		this.subtotal=-1;
		this.ship_hand_paid=-1;
	}

	public CustomerOrder(ResultSet rs) throws SQLException{
		fillFromResultSet(rs);
	}

	/**
	 * This cart MUST be filled to give meaningful information
	 * @param c
	 */
	public CustomerOrder(Cart c) throws SQLException{
		this.order_num = getNewOrderNum();
		this.cust_id = c.getCustomerId();
		this.contents = c.getContents();
		Customer cust = new Customer(c.getCustomerId());
		cust.fill();
		LoyaltyClass lc = new LoyaltyClass(cust.getLoyaltyExpiration()>0 ? cust.getLoyaltyTemp() : cust.getLoyalty());
		this.loyalty_id=lc.getId();
		this.pct_discount=lc.getDiscount_pct();
		this.pct_ship_hand=lc.getShipping_handling_pct();
		this.order_date = new Date(System.currentTimeMillis());

		calculateTotals();
		//totals?
	}



	private int getNewOrderNum() {
		int newOrderNum = (int)(Math.random()*1000000000);
		
		try {
			ResultSet rs = ConnectionManager.runQuery("SELECT order_num FROM Store_Orders WHERE order_num="+newOrderNum);
			if (rs.next()) {
				ConnectionManager.clean();
				return getNewOrderNum();
			}

		} catch (SQLException sqle){
			sqle.printStackTrace();
		}
		
		ConnectionManager.clean();
		return newOrderNum;
	}

	public int getOrderNum() {
		return order_num;
	}

	public int getShippingHandling() {
		if(amt_ship_hand<0){
			try{
				calculateTotals();
			} catch (SQLException sqle){
				//panic
			}
		}
		return amt_ship_hand;
	}

	public int getSubtotal() {
		if(subtotal<0){
			try{
				calculateTotals();
			} catch (SQLException sqle){
				//panic
			}
		}
		return subtotal;
	}

	public int getTotal() {
		if(total<0){
			try{
				calculateTotals();
			} catch (SQLException sqle){
				//panic
			}
		}
		return total;
	}

	public String getCustId() {
		return cust_id;
	}

	public Date getOrderDate() {
		return order_date;
	}

	public Map<Product, Integer> getContents() {
		if(this.contents==null){
			try{
				loadContents();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		return contents;
	}

	@Override
	public void fill() throws SQLException{
		ResultSet me = ConnectionManager.runQuery(
				"SELECT * FROM Orders WHERE order_num='"+this.order_num+"'");
		me.next();
		fillFromResultSet(me);
		me.close();
		ConnectionManager.clean();
	}

	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.order_num=rs.getInt("order_num");
		this.cust_id=rs.getString("cust_id");
		this.pct_discount=rs.getInt("discount");
		this.pct_ship_hand=rs.getInt("ship_hand_pct");
		this.loyalty_id=rs.getString("loyalty").charAt(0);
		this.order_date=rs.getDate("order_date");
	}

	private void loadContents() throws SQLException{
		this.contents = new HashMap<Product, Integer>();
		ResultSet cont = ConnectionManager.runQuery(
				"SELECT * FROM Order_Items WHERE order_num='"+this.order_num+"'");
		while(cont.next()){
			Product p = new Product(cont.getString("stock_num"));
			p.setOldPrice(cont.getInt("price"));
			this.contents.put(p, cont.getInt("qty"));
		}
		cont.close();
		ConnectionManager.clean();
	}

	/*
	 * We assume contents are already loaded and filled
	 */
	private void calculateTotals() throws SQLException{
		//need to define subtotal, ship_hand, total
		this.subtotal=this.total=this.ship_hand_paid=0;
		for(Map.Entry<Product, Integer> entry : contents.entrySet()){
			if(entry.getKey().getPriceCents()<0){
				System.err.println("Tried to get price from unfilled Product");
			}
			this.subtotal+=(entry.getKey().getPriceCents()*entry.getValue());
		}
		this.ship_hand_paid = (subtotal*pct_ship_hand)/100;
		int discount = (subtotal*pct_discount)/100;
		total=subtotal+ship_hand_paid-discount;
	}

	/**
	 * Use this to prepare to re-run an order.
	 * It will update all product prices as well as current customer loyalty status
	 * then recalculate totals;
	 * @throws SQLException
	 */
	public void recalculate() throws SQLException{
		if(this.contents==null){
			this.loadContents();
		}
		this.order_num=getNewOrderNum();
		for(Product p: this.contents.keySet()){
			p.fill();
		}
		Customer c = new Customer(this.cust_id);
		c.fill();
		this.loyalty_id=c.getLoyaltyExpiration() > 0 ? c.getLoyaltyTemp() : c.getLoyalty();
		LoyaltyClass lc = new LoyaltyClass(this.loyalty_id);
		lc.fill();
		this.pct_discount=lc.getDiscount_pct();
		this.pct_ship_hand=lc.getShipping_handling_pct();
		calculateTotals();
	}

	@Override
	public boolean push() {
		// this operation need not be supported
		return false;
	}

	@Override
	public boolean insert() {
		try{
			ConnectionManager.runQuery("INSERT INTO Orders"
					+ "(order_num, loyalty, ship_hand_pct, discount,"
					+ "cust_id, order_date) VALUES ("
					+ this.order_num+", '"+this.loyalty_id+"', "+this.pct_ship_hand
					+ ", "+this.pct_discount+", '"+this.cust_id+"', SYSDATE)").close();
			ConnectionManager.clean();
			for(Map.Entry<Product, Integer> entry : contents.entrySet() ){
				// TODO: maybe call fill for all entries?
				ConnectionManager.runQuery("INSERT INTO Order_Items "
						+ "(order_num, stock_num, qty, price) VALUES ("
						+ this.order_num+", '"+entry.getKey().getStockNum()+"', "
						+ entry.getValue()+", "+entry.getKey().getPriceCents()+")").close();
				ConnectionManager.clean();
			}
			return true;
		} catch (SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This should only ever be called for a particular order number
	 * @return
	 */
	public boolean sendToStore(){
		try{
			ConnectionManager.runQuery("INSERT INTO Store_Orders"
					+ " (order_num, Date_Received) VALUES "
					+ " ("+this.getOrderNum()+", SYSDATE)").close();
			ConnectionManager.clean();
			for(Map.Entry<Product, Integer> entry : this.contents.entrySet()){
				ConnectionManager.runQuery("INSERT INTO Store_Order_Items "
						+ " (order_num, stock_num, qty) VALUES "
						+ " ("+this.getOrderNum()+", '"+entry.getKey().getStockNum()+"', "
						+ entry.getValue()+") ");
			}
			return true;
		} catch (SQLException sqle){
			sqle.printStackTrace();
		} finally{
			ConnectionManager.clean();
		}
		return false;

	}

}
