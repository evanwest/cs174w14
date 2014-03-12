package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer implements ModelDataObject {

	private String cust_id;
	private String pwd_hash;
	private String salt;
	private String first_name;
	private String middle_name;
	private String last_name;
	private String email;
	private String address_addr;
	private String city_addr;
	private String state_addr;
	private String zip_addr;
	private Character loyalty;
	private Character loyalty_temp;
	private int loyalty_expiration;
	private boolean manager;

	public Customer(String cust_id){
		this.cust_id=cust_id;
	}

	public Customer(String cust_id, String pwd_hash, String salt, 
			String f_name, String m_name, String l_name, String email, 
			String address_addr, String city_addr, String state_addr,
			String zip_addr, Character loyalty, Character loyalty_temp, 
			int loyalty_expiration, boolean manager){
		this.cust_id=cust_id;
		this.pwd_hash=pwd_hash;
		this.salt=salt;
		this.first_name=f_name;
		this.middle_name=m_name;
		this.last_name=l_name;
		this.email=email;
		this.address_addr=address_addr;
		this.city_addr=city_addr;
		this.state_addr=state_addr;
		this.zip_addr=zip_addr;
		this.loyalty=loyalty;
		this.loyalty_temp=loyalty_temp;
		this.loyalty_expiration=loyalty_expiration;
		this.manager=manager;
	}

	public Customer(ResultSet rs) throws SQLException{
		fillFromResultSet(rs);
	}

	public String getCustId() {
		return cust_id;
	}

	public String getPwdHash() {
		return pwd_hash;
	}

	public String getSalt() {
		return salt;
	}

	public String getFirstName() {
		return first_name;
	}

	public String getMiddleName() {
		return middle_name;
	}

	public String getLastName() {
		return last_name;
	}

	public String getEmail() {
		return email;
	}

	public String getAddressAddr() {
		return address_addr;
	}

	public String getCityAddr() {
		return city_addr;
	}

	public String getStateAddr() {
		return state_addr;
	}

	public String getZipAddr() {
		return zip_addr;
	}

	public char getLoyalty() {
		return loyalty;
	}

	public char getLoyaltyTemp() {
		return loyalty_temp;
	}

	public int getLoyaltyExpiration() {
		return loyalty_expiration;
	}

	public boolean getManager(){
		return this.manager;
	}

	public void fill() throws SQLException{
		ResultSet me = ConnectionManager.runQuery(
				"SELECT * FROM Customers WHERE cust_id='"+this.cust_id+"'");
		if(me.next()){
			fillFromResultSet(me);
		}
		me.close();
		ConnectionManager.clean();
	}

	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.cust_id=rs.getString("cust_id").trim();
		this.pwd_hash=rs.getString("pwd_hash");
		this.salt=rs.getString("salt");
		this.first_name=rs.getString("f_name");
		this.middle_name=rs.getString("m_name");
		this.last_name=rs.getString("l_name");
		this.email=rs.getString("email");
		this.address_addr=rs.getString("addr_addr");
		this.city_addr=rs.getString("addr_city");
		this.state_addr=rs.getString("addr_state");
		this.zip_addr=rs.getString("addr_zip");
		this.loyalty=rs.getString("loyalty").charAt(0);
		this.loyalty_temp=rs.getString("loyalty_temp").charAt(0);
		this.loyalty_expiration=rs.getInt("loyalty_expiration");
		this.manager=(rs.getString("manager").equals("T"));
	}

	@Override
	public boolean push() {
		try{
			ConnectionManager.runQuery("UPDATE Customers SET " 
					+"pwd_hash='"+this.pwd_hash+"', "
					+"salt='"+this.salt+"', "
					+"f_name='"+this.first_name+"', "
					+"m_name='"+this.middle_name+"', "
					+"l_name='"+this.last_name+"', "
					+"email='"+this.email+"', "
					+"addr_addr='"+this.address_addr+"', "
					+"addr_city='"+this.city_addr+"', "
					+"addr_state='"+this.state_addr+"', "
					+"addr_zip='"+this.zip_addr+"', "
					+"loyalty='"+this.loyalty+"', "
					+"loyalty_temp='"+this.loyalty_temp+"', "
					+"loyalty_expiration="+this.loyalty_expiration+", "
					+"manager='"+(this.manager? "T" : "F")+"'"
					+"WHERE cust_id='"+this.cust_id+"'").close();
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
			ConnectionManager.runQuery("INSERT INTO Customers "
					+ "(cust_id, pwd_hash, salt, f_name, m_name, l_name,"
					+ "email, addr_addr, addr_city, addr_state,"
					+ "addr_zip, loyalty, loyalty_temp, "
					+ "loyalty_expiration, manager) VALUES ("
					+ "'"+this.cust_id+"', '"+this.pwd_hash+"', "
					+"'"+this.salt+"', '"+this.first_name+"', "
					+"'"+this.middle_name+"', '"+this.last_name+"', "
					+"'"+this.email+"', '"+this.address_addr+"', "
					+"'"+this.city_addr+"', '"+this.state_addr+"', "
					+"'"+this.zip_addr+"', '"+this.loyalty+"', "
					+"'"+this.loyalty_temp+"', "+this.loyalty_expiration+", "
					+"'"+(this.manager ? "T" : "F")+"')").close();
			ConnectionManager.clean();
			return true;
		} catch (SQLException sqle){
			sqle.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This will update loyalty status using previous 3 orders
	 */
	public void updateStatus() throws SQLException{
		//first get previous 3 orders
		ResultSet rs = ConnectionManager.runQuery(
				"SELECT order_num FROM ("
				+ "SELECT order_num, order_date FROM Orders "
				+ "WHERE cust_id='"+this.cust_id+"' ORDER BY order_date desc"
				+ ") WHERE ROWNUM <= 3");
		List<CustomerOrder> lastThree = new ArrayList<CustomerOrder>();
		int prev_total=0;
		while(rs.next()){
			CustomerOrder o = new CustomerOrder(Integer.valueOf(rs.getString("order_num")));
			lastThree.add(o);
		}
		rs.close();
		ConnectionManager.clean();
		for(CustomerOrder o : lastThree){
			o.fill();
			prev_total+=o.getTotal();
		}
		//now get highest matching loyalty status
		rs = ConnectionManager.runQuery(
				"SELECT id FROM ( SELECT id FROM Loyalty "
				+ "WHERE min_purchase<="+prev_total+" ORDER BY min_purchase DESC ) "
				+ "WHERE ROWNUM<=1");
		rs.next();
		this.loyalty = rs.getString("id").charAt(0);
		rs.close();
		ConnectionManager.clean();
	}
}
