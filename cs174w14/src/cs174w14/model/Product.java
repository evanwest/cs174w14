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
	private List<Product> accessory_of=null;

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
				sqle.printStackTrace();
			}
		}
		return this.accessories;
	}

	public List<Product> getAccessoryOf(){
		if(this.accessory_of==null){
			try{
				loadAccessoryOf();
			} catch(SQLException sqle){
				sqle.printStackTrace();
			}
		}
		return this.accessory_of;
	}

	public String getAccessoryOfParagraph(){
		List<Product> acc_of = getAccessoryOf();
		StringBuilder sb = new StringBuilder();
		for(Product p: acc_of){
			sb.append(p.getStockNum()+", ");
		}
		return sb.toString();
	}

	public void setStockNum(String stock_num) {
		this.stock_num = stock_num;
	}

	public void setModelNum(String model_num) {
		this.model_num = model_num;
	}

	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}

	public void setPriceCents(int price_cents) {
		this.price_cents = price_cents;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setQuantityInStock(int quantity_in_stock) {
		this.quantity_in_stock = quantity_in_stock;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setMinimumStock(int minimum_stock) {
		this.minimum_stock = minimum_stock;
	}

	public void setMaximumStock(int maximum_stock) {
		this.maximum_stock = maximum_stock;
	}

	public void setReplenishmentAmt(int replenishment_amt) {
		this.replenishment_amt = replenishment_amt;
	}
	
	public void setDefaults(){
		this.category="default";
		this.price_cents=1;
		this.warranty=0;
		this.quantity_in_stock=0;
		this.location="A0";
		this.minimum_stock=0;
		this.maximum_stock=100;
		this.replenishment_amt=0;
	}

	@Override
	public void fill() throws SQLException{
		ResultSet me;
		if(this.stock_num!=null){
			me = ConnectionManager.runQuery(
					"SELECT * FROM Products NATURAL JOIN Depot_Products WHERE stock_num='"+this.stock_num+"'");
		} 
		else if(this.manufacturer!=null && this.model_num!=null){
			me = ConnectionManager.runQuery(
					"SELECT * FROM Products NATURAL JOIN Depot_Products WHERE model_num='"+this.model_num+"' AND mfr='"+this.manufacturer+"'");
		}
		else{
			//serious problem
			return;
		}
		if(me.next()){
			fillFromResultSet(me);
		} //else everything remains null
		me.close();
		ConnectionManager.clean();
	}

	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.stock_num=rs.getString("stock_num");
		this.manufacturer=rs.getString("mfr");
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
		desc.close();
		ConnectionManager.clean();
	}

	private void loadAccessories() throws SQLException{
		this.accessories = new ArrayList<Product>();
		ResultSet acc = ConnectionManager.runQuery(
				"SELECT acc_stock_num FROM accessories WHERE acc_of_stock_num='"+this.stock_num+"'");
		while(acc.next()){
			this.accessories.add(new Product(acc.getString("acc_stock_num")));
		}
		acc.close();
		ConnectionManager.clean();
	}

	private void loadAccessoryOf() throws SQLException{
		this.accessory_of = new ArrayList<Product>();
		ResultSet accof = ConnectionManager.runQuery(
				"SELECT acc_of_stock_num FROM accessories WHERE acc_stock_num='"+this.stock_num+"'");
		while(accof.next()){
			this.accessory_of.add(new Product(accof.getString("acc_of_stock_num")));
		}
		accof.close();
		ConnectionManager.clean();
	}

	/**
	 * This should only be called when this has already been filled
	 * or you're really, really sure everything is correct and want
	 * to insert into the table (insert nonfunctional at present)
	 * 
	 * No changing mfr/model, stock_num!
	 */
	@Override
	public boolean push() {
		try{
			ConnectionManager.runQuery("UPDATE Products SET "
					+ "warranty="+this.warranty+", "
					+ "price="+this.price_cents+", "
					+ "category='"+this.category+"', "
					+ " WHERE stock_num='"+this.stock_num+"'").close();
			ConnectionManager.clean();
			ConnectionManager.runQuery("UPDATE Depot_Products SET "
					+ "qty="+this.quantity_in_stock+", "
					+ "location='"+this.location+"', "
					+ "min_num="+this.minimum_stock+", "
					+ "max_num="+this.maximum_stock+", "
					+ "replenishment="+this.replenishment_amt
					+ " WHERE stock_num='"+this.stock_num+"'").close();
			ConnectionManager.clean();
			//TODO: update accessories and descriptions
			return true;
		} catch(SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean insert() {
		try{
			//insert into mart products
			
			ConnectionManager.runQuery("INSERT INTO Products ("
					+"stock_num, model_num, mfr, category,"
					+ "warranty, price)"
					+ " VALUES ('"+this.stock_num+"', '"+this.model_num+"', "
					+ "'"+this.manufacturer+"', '"+this.category+"', "
					+ this.warranty+", "+this.price_cents+")").close();
			ConnectionManager.clean();
			
			
			ConnectionManager.runQuery("INSERT INTO Depot_Products ( "
					+ "stock_num, model_num, mfr, qty, location, "
					+ "min_num, max_num, replenishment) VALUES "
					+ "( '"+this.stock_num+"', '"+this.model_num+"', "
					+ "'"+this.manufacturer+"', "+this.quantity_in_stock+", "
					+ "'"+this.location+"', "+this.minimum_stock+", "
					+ this.maximum_stock+", "+this.replenishment_amt+" )");
			ConnectionManager.clean();
			
			//now need to insert descriptions
			if(this.descriptions!=null){
				for(Map.Entry<String, String> entry : this.descriptions.entrySet()){
					ConnectionManager.runQuery("INSERT INTO Descriptions "
							+ "(stock_num, attr, val) VALUES ("
							+ "'"+this.stock_num+"', '"+entry.getKey()+"', '"+entry.getValue()+"' )").close();
					ConnectionManager.clean();
				}
			}
			//now insert accessories
			if(this.accessories!=null){
				for(Product entry : this.accessories){
					ConnectionManager.runQuery("INSERT INTO Accessories "
							+ "(acc_of_stock_num, acc_stock_num) VALUES "
							+ "'"+this.stock_num+"', '"+entry.getStockNum()+"')");
				}
			}
			else if(this.accessory_of!=null){
				for(Product entry : this.accessory_of){
					ConnectionManager.runQuery("INSERT INTO Accessories "
							+ "(acc_of_stock_num, acc_stock_num) VALUES "
							+ "'"+entry.getStockNum()+"', '"+this.stock_num+"')");

				}
			}
			return true;
		} catch(SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
}
