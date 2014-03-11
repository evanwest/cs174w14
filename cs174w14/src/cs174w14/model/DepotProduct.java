package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DepotProduct {
	private String stock_num;
	private String model_num;
	private String manufacturer;
	private int quantity_in_stock;
	private String location;
	private int minimum_stock;
	private int maximum_stock;
	private int replenishment_incoming;

	public DepotProduct(String stock_num){
		this.stock_num=stock_num;
	}

	public DepotProduct(String stock_num, String model_num, String mfr,
			int quantity, String location, int min_stock, 
			int max_stock, int replenishment){
		this.stock_num=stock_num;
		this.model_num=model_num;
		this.manufacturer=mfr;
		this.quantity_in_stock=quantity;
		this.location=location;
		this.minimum_stock=min_stock;
		this.maximum_stock=max_stock;
		this.replenishment_incoming=replenishment;
	}

	public DepotProduct(ResultSet rs) throws SQLException{
		fillFromResultSet(rs);
	}

	public String getStockNum() {
		return stock_num;
	}

	public String getModelNum() {
		return model_num;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public int getQuantity() {
		return quantity_in_stock;
	}

	public String getLocation() {
		return location;
	}

	public int getMinStock() {
		return minimum_stock;
	}

	public int getMaxStock() {
		return maximum_stock;
	}

	public int getReplenishment() {
		return replenishment_incoming;
	}
	
	public void fillStub() throws SQLException{
		ResultSet me = ConnectionManager.runQuery(
				"SELECT * FROM Depot_Products WHERE stock_num='"+this.stock_num+"'");
		me.first();
		fillFromResultSet(me);
	}

	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.stock_num=rs.getString("stock_num");
		this.model_num=rs.getString("model_num");
		this.manufacturer=rs.getString("mfr");
		this.quantity_in_stock=rs.getInt("qty");
		this.location=rs.getString("location");
		this.minimum_stock=rs.getInt("min_num");
		this.maximum_stock=rs.getInt("max_num");
		this.replenishment_incoming=rs.getInt("replenishment");
	}

}
