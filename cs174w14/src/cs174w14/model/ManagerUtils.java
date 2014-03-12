package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class ManagerUtils {
	
	/**
	 * These will return data for the period of: the current Calendar month
	 * @return
	 */

	/**
	 * Returns the total volume and worth moved during this month for each product
	 * @param month Format: MM/YYYY
	 * @return arrays have 3 elements: stock_num, qty, total_price
	 */
	public static List<String[]> getProductReport(String month){
		try{
			ResultSet rs = ConnectionManager.runQuery(
				"SELECT oi.stock_num, SUM(oi.qty) AS qty, SUM(oi.qty*oi.price) as total"
				+ "FROM Order_Items oi, Orders o WHERE oi.order_num=o.order_num "
				+ "AND to_char(order_date,'mm/YYYY')='"+month+"' "
				+ "GROUP BY oi.stock_num");
			List<String[]> prods = new ArrayList<String[]>();
			while(rs.next()){
				prods.add(new String[] {rs.getString("stock_num"), rs.getString("qty"), rs.getString("total")});
			}
			return prods;
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @return arrays have 3 elements: category, qty, total_price
	 */
	public static List<String[]> getCategoryReport(String month){
		try{
			ResultSet rs = ConnectionManager.runQuery(
				"SELECT p.category, SUM(oi.qty) AS qty, SUM(oi.qty*oi.price) as total"
				+ " FROM Order_Items oi, Orders o, Products p WHERE oi.order_num=o.order_num "
				+ " AND p.stock_num=oi.stock_num AND to_char(order_date,'mm/YYYY')='"+month+"' "
				+ " GROUP BY p.category");
			List<String[]> prods = new ArrayList<String[]>();
			while(rs.next()){
				prods.add(new String[] {rs.getString("category"), rs.getString("qty"), rs.getString("total")});
			}
			rs.close();
			ConnectionManager.clean();
			return prods;
		}catch(SQLException sqle){
			sqle.printStackTrace();
		}finally{
			ConnectionManager.clean();
		}
		return null;
	}
	
	/**
	 * 
	 * @return array has 2 elements: cust_id, total_spent
	 */
	public static String[] getUserReport(String month){
		try{
			ResultSet rs = ConnectionManager.runQuery(
				"SELECT o.cust_id, SUM(oi.qty*oi.price) as total"
				+ " FROM Order_Items oi, Orders o, WHERE oi.order_num=o.order_num "
				+ " AND to_char(order_date,'mm/YYYY')='"+month+"' "
				+ " GROUP BY o.cust_id");
			String[] result = {rs.getString("cust_id"), rs.getString("total")};
			rs.close();
			ConnectionManager.clean();
			return result;
		}catch(SQLException sqle){
			sqle.printStackTrace();
		} finally{
			ConnectionManager.clean();
		}
		return null;
	}
	
	
	public static void removeOldOrderHistory() {
		try{
		ConnectionManager.runQuery("DELETE FROM Orders WHERE order_num NOT IN ( "
				+ "SELECT order_num FROM ( "
				+ "SELECT order_num, rank() "
				+ "OVER (PARTITION by cust_id ORDER BY order_date DESC) rank "
				+ "FROM Orders"
				+ ") WHERE rank<=3)");
		} catch (SQLException sqle){
			sqle.printStackTrace();
		} finally{
			ConnectionManager.clean();
		}
	}
}
