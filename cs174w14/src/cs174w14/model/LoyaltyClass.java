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
		try{
			ConnectionManager.runQuery("UPDATE Loyalty SET"
					+ "name='"+this.name+"', "
					+ "pct_discount="+this.discount_pct+", "
					+ "ship_hand="+this.shipping_handling_pct
					+ "WHERE id='"+this.id+"';");
			return true;
		} catch (SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean insert() {
		try{
			if(this.name==null || this.discount_pct<0 || this.shipping_handling_pct<0){
				return false;
			}
			ConnectionManager.runQuery("INSERT INTO Loyalty "
					+ "(id, name, pct_discount, ship_hand)"
					+ "VALUES ('"+this.id+"', '"+this.name+", "
					+ this.discount_pct+", "+this.shipping_handling_pct+");");
			return true;
		} catch (SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
}
