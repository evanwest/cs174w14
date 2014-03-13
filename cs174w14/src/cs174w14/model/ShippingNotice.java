package cs174w14.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class ShippingNotice implements ModelDataObject{

	int ship_id;
	String mfr;
	Date dateReceived;

	Map<Product, Integer> contents;

	public ShippingNotice(int ship_id){
		this.ship_id=ship_id;
		this.mfr=null;
		this.dateReceived=null;
		this.contents=null;
	}

	public int getShip_id() {
		return ship_id;
	}

	public void setShip_id(int ship_id) {
		this.ship_id = ship_id;
	}

	public String getMfr() {
		return mfr;
	}

	public void setMfr(String mfr) {
		this.mfr = mfr;
	}

	public Date getDateReceived() {
		return dateReceived;
	}

	public void setDateReceived(Date dateReceived) {
		this.dateReceived = dateReceived;
	}

	public Map<Product, Integer> getContents() {
		if(this.contents==null){
			this.contents = new HashMap<Product, Integer>();
			try{
				this.loadContents();
			} catch (SQLException sqle){
				sqle.printStackTrace();
				ConnectionManager.clean();
			}
		}
		return contents;
	}


	/**
	 * This particular 
	 */
	public void addEntry(String model_num, int qty) throws SQLException{
		if(this.contents==null){
			this.contents = new HashMap<Product, Integer>();
		}
		//check if this is already in the map
		for(Map.Entry<Product, Integer> entry : contents.entrySet()){
			if(model_num.equals(entry.getKey().getModelNum())){
				entry.setValue(entry.getValue()+qty);
				return;
			}
		}
		//otherwise check if there is already a stock number for this
		Product p = new Product(mfr, model_num);
		p.fill();
		//now add to our list
		this.contents.put(p,qty);

	}

	@Override
	public void fill() throws SQLException {
		try{
			ResultSet me = ConnectionManager.runQuery(
					"SELECT * FROM Shipping_Notices WHERE ship_id="+this.ship_id);
			if(me.next()){
				fillFromResultSet(me);
			}
			me.close();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		} finally{
			ConnectionManager.clean();
		}
	}

	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.ship_id=rs.getInt("ship_id");
		this.dateReceived=rs.getDate("date_received");
		this.mfr=rs.getString("company");
	}

	private void loadContents() throws SQLException{
		ResultSet cont = ConnectionManager.runQuery(
				"SELECT stock_num, qty FROM Shipping_Notice_Items WHERE ship_id="+this.ship_id );
		while(cont.next()){
			this.contents.put(new Product(cont.getString("stock_num")), cont.getInt("qty"));
		}
		cont.close();
		ConnectionManager.clean();
	}

	@Override
	public boolean push() {
		//no need to implement
		return false;
	}

	@Override
	public boolean insert() {
		try{
			ConnectionManager.runQuery("INSERT INTO Shipping_Notices"
					+ "(ship_id, company, date_received) VALUES "
					+ "("+this.ship_id+", '"+this.mfr+"', SYSDATE)").close();
			ConnectionManager.clean();
			//now for all contents, if needed make new Product entries
			for(Map.Entry<Product, Integer> entry : contents.entrySet()){
				Product p = entry.getKey();
				if(p.getStockNum()==null){
					//nope, need to generate
					String stockNum=Utils.generateStockNum();
					p.setStockNum(stockNum);
					p.setDefaults();
					p.insert();
				}
				//now insert into shipping_notice_items
				ConnectionManager.runQuery("INSERT INTO Shipping_Notice_Items"
						+ " (stock_num, ship_id, qty) VALUES "
						+ " ('"+p.getStockNum()+"', "+this.ship_id+", "+entry.getValue()+")");
				ConnectionManager.clean();
			}
			//now modify replenishment in products
			ConnectionManager.runQuery(
					"UPDATE Products SET (replenishment) = ("
							+ "SELECT (p.replenishment+sni.qty) AS replenishment "
							+ "FROM Products p, Shipping_Notice_Items sni "
							+ "WHERE p.stock_num=sni.stock_num AND sni.ship_id="+this.ship_id).close();
			ConnectionManager.clean();
			return true;
		} catch (SQLException sqle){
			sqle.printStackTrace();
		} finally{
			ConnectionManager.clean();
		}
		return false;
	}

	public boolean delete(){
		try{
			ConnectionManager.runQuery(
					"DELETE FROM Shipping_Notices WHERE ship_id="+this.ship_id).close();
		} catch(SQLException sqle){
			sqle.printStackTrace();
		} finally{
			ConnectionManager.clean();
		}
		return false;
	}

	/**
	 * Call this method when the shipment has arrived at the depot.
	 * It will modify replenishment and qty for products, then delete
	 * this shipping notice.
	 * @return
	 */
	public boolean process(){
		try{
			//modify replenishment (sub) and qty (add)
			ConnectionManager.runQuery(
					"UPDATE Products SET (replenishment, qty) = ("
							+ "SELECT (p.replenishment-sni.qty) AS replenishment,"
							+ "(p.qty+sni.qty) AS qty FROM Products p, Shipping_Notice_Items sni"
							+ "WHERE p.stock_num=sni.stock_num AND sni.ship_id="+this.ship_id).close();
			//delete this notice
			delete();
		} catch(SQLException sqle){
			sqle.printStackTrace();
		} finally{
			ConnectionManager.clean();
		}
		return false;
	}

}
