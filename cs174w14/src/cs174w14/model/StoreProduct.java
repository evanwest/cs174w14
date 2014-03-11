package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StoreProduct {
	private String stock_num;
	private String model_num;
	private int warranty;
	private int price_cents;
	private int old_price=-1;
	private String category;
	private String manufacturer;

	private Map<String, String> descriptions=null;
	private List<StoreProduct> accessories=null;

	public StoreProduct(String stock_num){
		this.stock_num=stock_num;
		this.manufacturer=null;
		this.model_num=null;
		this.category=null;
		this.price_cents=-1;
		this.warranty=-1;
	}

	public StoreProduct(String stock_num, String mfr, String model_num, String category, 
			int price_cents, int warranty){
		this.stock_num=stock_num;
		this.manufacturer=mfr;
		this.model_num=model_num;
		this.category=category;
		this.price_cents=price_cents;
		this.warranty=warranty;
	}

	public StoreProduct(ResultSet rs) throws SQLException{
		fillFromResultSet(rs);
	}
	
	public void setOldPrice(int price_cents){
		this.old_price=price_cents;
	}

	public String getStockNum() {
		return stock_num;
	}

	public String getModelNum() {
		return model_num;
	}

	public int getWarranty() {
		return warranty;
	}

	public int getPriceCents() {
		return price_cents;
	}

	public String getCategory() {
		return category;
	}

	public String getManufacturer() {
		return manufacturer;
	}
	
	public int getOldPrice(){
		return this.old_price;
	}

	public Map<String, String> getDescriptions(){
		if(descriptions==null){
			try{
				loadDescriptions();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		return this.descriptions;
	}

	public String getDescription(String key){
		if(descriptions==null){
			try{
				loadDescriptions();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		return this.descriptions.get(key);
	}

	/**
	 * This returns a list of Accessory products
	 * These products are initially loaded as STUBS.
	 * @return
	 */
	public List<StoreProduct> getAccessories(){
		if(this.accessories==null){
			try{
				loadAccessories();
			} catch (SQLException sqle){

			}
		}
		return this.accessories;

	}

	public void fillStub() throws SQLException{
		ResultSet me = ConnectionManager.runQuery(
				"SELECT * FROM Products WHERE stock_num='"+this.stock_num+"'");
		me.first();
		fillFromResultSet(me);
	}

	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.stock_num=rs.getString("stock_num");
		this.manufacturer=rs.getString("manufacturer");
		this.model_num=rs.getString("model_num");
		this.category=rs.getString("category");
		this.price_cents=rs.getInt("price");
		this.warranty=rs.getInt("warranty");
	}

	private void loadDescriptions() throws SQLException{
		this.descriptions = new HashMap<String, String>();
		ResultSet desc = ConnectionManager.runQuery(
				"SELECT attr, val FROM descriptions WHERE stock_num='"+this.stock_num+"'");
		while(desc.next()){
			this.descriptions.put(desc.getString("attr"), desc.getString("val"));
		}
	}

	private void loadAccessories() throws SQLException{
		this.accessories = new ArrayList<StoreProduct>();
		ResultSet acc = ConnectionManager.runQuery(
				"SELECT acc_stock_num FROM accessories WHERE acc_of_stock_num='"+this.stock_num+"'");
		while(acc.next()){
			this.accessories.add(new StoreProduct(acc.getString("acc_stock_num")));
		}
	}
}
