package cs174w14.run;

import java.sql.SQLException;

import cs174w14.model.ConnectionManager;

public class eDepot {
	public static void main (String[] args){
		try{
			ConnectionManager.init();
		} catch (SQLException sqle){
			sqle.printStackTrace();
			System.exit(1);
		}

	}
}
