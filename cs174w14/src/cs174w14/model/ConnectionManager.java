package cs174w14.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class ConnectionManager {
	private static final String serverAddress="jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	private static final String userName="e_west";
	private static final String password="4871406";
	
	public static Connection dbconn;
	
	public static void init() throws SQLException{
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		ConnectionManager.dbconn=DriverManager.getConnection(serverAddress,userName,password);
	}
	
	public static ResultSet runQuery(String query) throws SQLException{
		Statement st = ConnectionManager.dbconn.createStatement();
		return st.executeQuery(query);
	}
}
