package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoyaltyClass implements ModelDataObject {

	private char id;
	private int shipping_handling_pct;
	private int shipping_handling_cutoff;
	private int discount_pct;
	private int min_purchase;
	private String name;

	public LoyaltyClass(char _id){
		this.id=_id;
	}
	
	public LoyaltyClass(String name){
		this.name=name;
		try{
			ResultSet rs = ConnectionManager.runQuery(
					"SELECT * FROM Loyalty WHERE name='"+name+"'");
			if(rs.next()){
				fillFromResultSet(rs);
			}
			else{
				System.err.println("Tried to find nonexistent loyalty class: "+name);
				//do nothing
			}
		} catch(SQLException sqle){
			sqle.printStackTrace();
		}
	}

	public LoyaltyClass(char _id, int ship_hand_pct, int discount_pct, String name, int min_purchase, int s_h_cutoff){
		this.id=_id;
		this.shipping_handling_pct=ship_hand_pct;
		this.shipping_handling_cutoff=s_h_cutoff;
		this.discount_pct=discount_pct;
		this.name=name;
		this.min_purchase=min_purchase;
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
	
	public int getMinPurchase(){
		return this.min_purchase;
	}

	public void fill() throws SQLException{
		ResultSet me = ConnectionManager.runQuery(
				"SELECT * FROM Loyalty WHERE id='"+this.id+"'");
		me.next();
		fillFromResultSet(me);
		me.close();
		ConnectionManager.clean();
	}

	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.id=rs.getString("id").charAt(0);
		this.shipping_handling_pct=rs.getInt("ship_hand");
		this.discount_pct=rs.getInt("pct_discount");
		this.name=rs.getString("name");
		this.min_purchase=rs.getInt("min_purchase");
		this.shipping_handling_cutoff=rs.getInt("s_h_cutoff");
	}

	@Override
	public boolean push() {
		try{
			ConnectionManager.runQuery("UPDATE Loyalty SET"
					+ "name='"+this.name+"', "
					+ "pct_discount="+this.discount_pct+", "
					+ "ship_hand="+this.shipping_handling_pct+", "
					+ "min_purchase="+this.min_purchase+", "
					+ "s_h_cutoff="+this.shipping_handling_cutoff+" "
					+ "WHERE id='"+this.id+"'").close();
			ConnectionManager.clean();
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
					+ "(id, name, pct_discount, ship_hand, min_purchase, s_h_cutoff)"
					+ " VALUES ('"+this.id+"', '"+this.name+"', "
					+ this.discount_pct+", "+this.shipping_handling_pct
					+ ", "+this.min_purchase+", "+this.shipping_handling_cutoff+")").close();
			ConnectionManager.clean();
			return true;
		} catch (SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
}
