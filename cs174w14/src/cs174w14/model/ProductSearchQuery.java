package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchQuery  {
	final String query;
	List<Product> result;

	ProductSearchQuery(String query){
		this.query=query;
	}

	/**
	 * This will execute the planned query and return results.
	 * If this search has already been executed, it will execute it again,
	 * fresh from the database.
	 * @return
	 */
	public List<Product> execute() throws SQLException{
		this.result = new ArrayList<Product>();
		ResultSet rs = ConnectionManager.runQuery(query);
		while(rs.next()){
			Product item = new Product(rs);
			this.result.add(item);
		}
		rs.close();
		ConnectionManager.clean();
		return result;
	}

	/**
	 * This will return the previous results if the search has already 
	 * been executed, otherwise it will execute it and then return;
	 * @return
	 */
	public List<Product> getResults() throws SQLException{
		if(this.result==null){
			return this.execute();
		}
		else{
			return this.result;
		}
	}

}
