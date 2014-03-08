package cs174w14.model;

import java.sql.*;

public class JdbcTest {

	public static String serverAddress="jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	public static void main(String[] args) {
		ResultSet rs;
		Connection conn;
		try{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
			conn = DriverManager.getConnection(serverAddress, "e_west", "4871406");

			Statement st = conn.createStatement();
			rs = st.executeQuery("SELECT owner, table_name FROM all_tables WHERE owner='E_WEST'");
			
			while(rs.next()){
				System.out.println(rs.getString("owner")+","+rs.getString("table_name"));
			}
			
			rs.close();
			conn.close();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		}
	}
	
	public static void setUpTables(){
		
	}

}
