package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;

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
	private char loyalty;
	private char loyalty_temp;
	private int loyalty_expiration;

	public Customer(String cust_id){
		this.cust_id=cust_id;
	}

	public Customer(String cust_id, String pwd_hash, String salt, 
			String f_name, String m_name, String l_name, String email, 
			String address_addr, String city_addr, String state_addr,
			String zip_addr, char loyalty, char loyalty_temp, int loyalty_expiration){
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
	}

	public Customer(ResultSet rs) throws SQLException{
		fillFromResultSet(rs);
	}

	public String getCust_id() {
		return cust_id;
	}

	public String getPwd_hash() {
		return pwd_hash;
	}

	public String getSalt() {
		return salt;
	}

	public String getFirst_name() {
		return first_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public String getEmail() {
		return email;
	}

	public String getAddress_addr() {
		return address_addr;
	}

	public String getCity_addr() {
		return city_addr;
	}

	public String getState_addr() {
		return state_addr;
	}

	public String getZip_addr() {
		return zip_addr;
	}

	public char getLoyalty() {
		return loyalty;
	}

	public char getLoyalty_temp() {
		return loyalty_temp;
	}

	public int getLoyalty_expiration() {
		return loyalty_expiration;
	}

	public void fill() throws SQLException{
		ResultSet me = ConnectionManager.runQuery(
				"SELECT * FROM Customers WHERE cust_id='"+this.cust_id+"'");
		me.first();
		fillFromResultSet(me);
	}

	private void fillFromResultSet(ResultSet rs) throws SQLException{
		this.cust_id=rs.getString("cust_id");
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
					+"loyalty_expiration="+this.loyalty_expiration
					+"WHERE cust_id='"+this.cust_id+"';");
			return true;
		} catch (SQLException sqle){
			return false;
		}

	}
}
