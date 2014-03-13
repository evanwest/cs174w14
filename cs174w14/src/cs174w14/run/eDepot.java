package cs174w14.run;

import java.sql.SQLException;

import cs174w14.model.ConnectionManager;

/**
 * This is a command-line interface for the eDepot
 * external world interface
 *
 */
public class eDepot {
	public static void main (String[] args){
		try{
			ConnectionManager.init();
		} catch (SQLException sqle){
			sqle.printStackTrace();
			System.exit(1);
		}
		
		EDepotRunner eDepotRunner = new EDepotRunner();
		eDepotRunner.run();

	}
	
}
