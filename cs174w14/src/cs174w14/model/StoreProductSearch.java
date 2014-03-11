package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoreProductSearch {
	final String query;
	List<StoreProduct> result;

	StoreProductSearch(String query){
		this.query=query;
	}

	/**
	 * This will execute the planned query and return results.
	 * If this search has already been executed, it will execute it again,
	 * fresh from the database.
	 * @return
	 */
	public List<StoreProduct> execute() throws SQLException{
		this.result = new ArrayList<StoreProduct>();
		ResultSet rs = ConnectionManager.runQuery(query);
		while(rs.next()){
			StoreProduct sp = new StoreProduct(rs);
			this.result.add(sp);
		}
		return result;
	}

	/**
	 * This will return the previous results if the search has already 
	 * been executed, otherwise it will execute it and then return;
	 * @return
	 */
	public List<StoreProduct> getResults() throws SQLException{
		if(this.result==null){
			return this.execute();
		}
		else{
			return this.result;
		}
	}

}
