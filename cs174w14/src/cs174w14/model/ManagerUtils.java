package cs174w14.model;

import java.util.List;

public abstract class ManagerUtils {
	
	/**
	 * These will return data for the period of: the current Calendar month
	 * @return
	 */

	/**
	 * 
	 * @return arrays have 3 elements: stock_num, qty, total_price
	 */
	public static List<String[]> getProductReport(){
		return null;
	}
	
	/**
	 * 
	 * @return arrays have 3 elements: category, qty, total_price
	 */
	public static List<String[]> getCategoryReport(){
		return null;
	}
	
	/**
	 * 
	 * @return array has 2 elements: cust_id, total_spent
	 */
	public static String[] getUserReport(){
		return null;
	}
	
	//send replenishment order? actually just send shipping notice
	
	public static void removeOldOrderHistory(){
		
	}
}
