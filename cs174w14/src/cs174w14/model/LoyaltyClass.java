package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoyaltyClass implements ModelDataObject {

	private char id;
	private int shipping_handling_pct;
	private int discount_pct;
	private String name;

	public LoyaltyClass(char _id){
		this.id=_id;
	}
	
	public LoyaltyClass(char _id, int ship_hand_pct, int discount_pct, String name){
		this.id=_id;
		this.shipping_handling_pct=ship_hand_pct;
		this.discount_pct=discount_pct;
		this.name=name;
	}
	
	public LoyaltyClass(ResultSet rs) throws SQLException{
		fillFromResultSet(rs);
	}

	public char getId() {
		return id;
	}

	public int getShipping_handling_pct() {
		return shipping_handling_pct;
	}

	public int getDiscount_pct() {
		return discount_pct;
	}

	public String getName() {
		return name;
	}

	public void fill() throws SQLException{
		ResultSet me = ConnectionManager.runQuery(
				"SELECT * FROM Loyalty WHERE id='"+this.id+"'");
		me.first();
		fillFromResultSet(me);
	}
	
	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.shipping_handling_pct=rs.getInt("ship_hand");
		this.discount_pct=rs.getInt("discount");
		this.name=rs.getString("name");
	}

	@Override
	public boolean push() {
		// TODO do SQL here
		return false;
	}
}
