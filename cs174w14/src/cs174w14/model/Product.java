package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Product implements ModelDataObject {
	private String stock_num;
	private String model_num;
	private int warranty;
	private int price_cents;
	private int old_price=-1;
	private String category;
	private String manufacturer;
	private int quantity_in_stock;
	private String location;
	private int minimum_stock;
	private int maximum_stock;
	private int replenishment_amt;

	private Map<String, String> descriptions=null;
	private List<Product> accessories=null;

	public Product(String stock_num){
		this.stock_num=stock_num;
		this.manufacturer=null;
		this.model_num=null;
		this.category=null;
		this.price_cents=-1;
		this.warranty=-1;
		this.quantity_in_stock=-1;
		this.location=null;
		this.minimum_stock=-1;
		this.maximum_stock=-1;
		this.replenishment_amt=-1;
	}

	public Product(String mfr, String model_num){
		this.manufacturer=mfr;
		this.model_num=model_num;
		this.stock_num=null;
		this.category=null;
		this.price_cents=-1;
		this.warranty=-1;
		this.quantity_in_stock=-1;
		this.location=null;
		this.minimum_stock=-1;
		this.maximum_stock=-1;
		this.replenishment_amt=-1;
	}

	public Product(String stock_num, String mfr, String model_num, String category, 
			int price_cents, int warranty, int quantity, String location, int min_stock, 
			int max_stock, int replenishment){
		this.stock_num=stock_num;
		this.manufacturer=mfr;
		this.model_num=model_num;
		this.category=category;
		this.price_cents=price_cents;
		this.warranty=warranty;
		this.quantity_in_stock=quantity;
		this.location=location;
		this.minimum_stock=min_stock;
		this.maximum_stock=max_stock;
		this.replenishment_amt=replenishment;
	}

	public Product(ResultSet rs) throws SQLException{
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

	public int getPrice() {
		return price_cents;
	}


	public int getQuantityInStock() {
		return quantity_in_stock;
	}

	public String getLocation() {
		return location;
	}

	public int getMinimumStock() {
		return minimum_stock;
	}

	public int getMaximumStock() {
		return maximum_stock;
	}

	public int getReplenishmentAmount() {
		return replenishment_amt;
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
	
	public String getDescriptionParagraph(){
		if(descriptions==null){
			try{
				loadDescriptions();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		StringBuilder buff = new StringBuilder();
		for (Map.Entry<String, String> entry : descriptions.entrySet()){
			buff.append(entry.getKey()+": "+entry.getValue()+"\n");
		}
		return buff.toString();
	}

	/**
	 * This returns a list of Accessory products
	 * These products are initially loaded as STUBS.
	 * @return
	 */
	public List<Product> getAccessories(){
		if(this.accessories==null){
			try{
				loadAccessories();
			} catch (SQLException sqle){

			}
		}
		return this.accessories;

	}

	@Override
	public void fill() throws SQLException{
		ResultSet me;
		if(this.stock_num!=null){
			me = ConnectionManager.runQuery(
					"SELECT * FROM Products WHERE stock_num='"+this.stock_num+"'");
		} 
		else if(this.manufacturer!=null && this.model_num!=null){
			me = ConnectionManager.runQuery(
					"SELECT * FROM Products WHERE model_num='"+this.model_num+"' AND mfr='"+this.manufacturer+"';");
		}
		else{
			//serious problem
			return;
		}
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
		this.quantity_in_stock=rs.getInt("qty");
		this.location=rs.getString("location");
		this.minimum_stock=rs.getInt("min_num");
		this.maximum_stock=rs.getInt("max_num");
		this.replenishment_amt=rs.getInt("replenishment");

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
		this.accessories = new ArrayList<Product>();
		ResultSet acc = ConnectionManager.runQuery(
				"SELECT acc_stock_num FROM accessories WHERE acc_of_stock_num='"+this.stock_num+"'");
		while(acc.next()){
			this.accessories.add(new Product(acc.getString("acc_stock_num")));
		}
	}

	/**
	 * This should only be called when this has already been filled
	 * or you're really, really sure everything is correct and want
	 * to insert into the table (insert nonfunctional at present)
	 */
	@Override
	public boolean push() {
		// TODO do SQL here
		//first check if exists in table
		boolean exists=true;
		if(exists){
			try{
			ConnectionManager.runQuery("UPDATE Products SET"
					+ "warranty="+this.warranty+", "
					+ "price="+this.price_cents+","
					+ "category='"+this.category+"',"
					+ "qty="+this.quantity_in_stock+","
					+ "location='"+this.location+","
					+ "min_num="+this.minimum_stock+","
					+ "max_num="+this.maximum_stock+","
					+ "replenishment="+this.replenishment_amt
					+ " WHERE stock_num='"+this.stock_num+"';");
			return true;
			} catch(SQLException sqle){
				return false;
			}
		}
		//else insert (not now)
		return false;
	}
}
