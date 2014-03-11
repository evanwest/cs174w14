package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Cart implements ModelDataObject {
	private String cust_id;
	private Map<Product, Integer> contents;
	private Map<String, Integer> originalContents;

	public Cart(String cust_id){
		this.cust_id=cust_id;
		this.contents=null;
	}

	public Cart(ResultSet rs) throws SQLException{
		fillFromResultSet(rs);
	}

	public Map<Product, Integer> getContents(){
		return this.contents;
	}

	@Override
	public void fill() throws SQLException {
		ResultSet me = ConnectionManager.runQuery(
				"SELECT stock_num, qty FROM cart_items WHERE cust_id='"+this.cust_id+"';");
		fillFromResultSet(me);
	}

	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.contents = new HashMap<Product, Integer>();
		this.originalContents = new HashMap<String, Integer>();
		while(rs.next()){
			contents.put(new Product(rs.getString("stock_num")), rs.getInt("qty"));
			originalContents.put(rs.getString("stock_num"), rs.getInt("qty"));
		}
	}

	/**
	 * Note: this will only update cart contents!
	 * It will not recurse to products in cart
	 */
	@Override
	public boolean push(){
		try{
			for(Map.Entry<Product, Integer> entry : contents.entrySet()){
				if(originalContents.containsKey(entry.getKey().getStockNum())){
					//update table quantity or delete if zero
					if(entry.getValue()<=0){
						//delete from table
						ConnectionManager.runQuery("DELETE FROM Cart_Items WHERE "
								+ "stock_num='"+entry.getKey().getStockNum()+"' "
								+ "AND cust_id='"+this.cust_id+"';");
					}
					else{
						ConnectionManager.runQuery("UPDATE Cart_Items SET"
								+ "qty="+entry.getValue()
								+ "WHERE stock_num='"+entry.getKey().getStockNum()+"' "
								+ "AND cust_id='"+this.cust_id+"';");
					}
				}
				else{
					ConnectionManager.runQuery("INSERT INTO Cart_Items (cust_id, stock_num, qty) "
							+ "VALUES ('"+this.cust_id+"', '"+entry.getKey().getStockNum()+"', "+entry.getValue()+");");
				}
			}
			return true;
		} catch (SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}




}
