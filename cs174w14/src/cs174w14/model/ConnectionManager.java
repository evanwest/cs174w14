package cs174w14.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ConnectionManager {
	private static final String serverAddress="jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
	private static final String userName="e_west";
	private static final String password="4871406";

	public static Connection dbconn;

	private static List<Statement> openStatements = new ArrayList<Statement>();

	public static void init() throws SQLException{
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		ConnectionManager.dbconn=DriverManager.getConnection(serverAddress,userName,password);
	}

	public static ResultSet runQuery(String query) throws SQLException{
		//now do a hacky find-replace for null values
		query = query.replaceAll("'null'", "null");
		System.out.println(query);
		Statement st = ConnectionManager.dbconn.createStatement();
		ResultSet rs = st.executeQuery(query);
		openStatements.add(st);
		return rs;
	}

	public static void clean(){
		for(Statement s : openStatements){
			try{
				s.close();
			} catch(SQLException sqle){
				sqle.printStackTrace();
				continue;
			}
		}
	}

	public static void close() throws SQLException{
		ConnectionManager.dbconn.close();
	}
}
