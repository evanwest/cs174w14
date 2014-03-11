package cs174w14.model;

import java.sql.SQLException;

public interface ModelDataObject {
	public void fill() throws SQLException;
	
	public boolean push();	
}
