package cs174w14.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class eDepot {
private static Queue<ShippingNotice> shippingNotices;
	
	public static void init() {
		shippingNotices = new LinkedBlockingQueue<ShippingNotice>();
	}
	
	public static void processShippingNotice(int ship_id) {
		// check db for new shipping notices
		// process shipments according to received notices
		try {
			ShippingNotice shippingNotice = new ShippingNotice(ship_id);
			shippingNotice.fill();
			shippingNotice.process();
			shippingNotices.add(shippingNotice);
			receiveShipment(ship_id);
		} catch (SQLException sqle){
			sqle.printStackTrace();
		} finally{
			ConnectionManager.clean();
		}
		
	}
	
	public static void receiveShipment(int ship_id) {
		while (!shippingNotices.isEmpty()) {
			ShippingNotice shippingNotice = shippingNotices.remove();
			shippingNotice.receive();
		}
	}
	
	public static void fillOrder(int order_num) {
		try {
			// check for orders and fill them as they come
			// update number in stock, send replenishment orders as necessary
			CustomerOrder customerOrder = new CustomerOrder(order_num);
			customerOrder.fill();
			for (Map.Entry<Product, Integer> entry : customerOrder.getContents().entrySet()) {
				Product product = entry.getKey();
				int qty = entry.getValue();
				product.fill();
				product.setQuantityInStock(product.getQuantityInStock()-qty);
			}
			
			// get all manufacturers that we need to send replenishment orders to
			ResultSet rs = ConnectionManager.runQuery(
					"SELECT mfr, COUNT(*) AS replenish_count FROM Depot_Products " +
					"WHERE qty > min_num GROUP BY mfr HAVING COUNT(*) >= 2");
			List<String> manufacturers = new ArrayList<String>();
			while (rs.next()) {
				manufacturers.add(rs.getString("mfr"));
			}
			rs.close();
			ConnectionManager.clean();
			for (String mfr : manufacturers) {
				sendReplenishmentOrder(mfr);
			}
			
			
		} catch (SQLException sqle){
			sqle.printStackTrace();
		} finally{
			ConnectionManager.clean();
		}
	}
	
	private static void sendReplenishmentOrder(String mfr) {
		try {
			//get all products to send a replenishment order for this mfr
			ResultSet rs = ConnectionManager.runQuery(
					"SELECT model_num, max_num-qty AS num_to_order FROM Depot_Products " +
					"WHERE mfr='"+mfr+"' AND qty < max_num");
			
			Map<String, Integer> shippingNoticeItems = new HashMap<String, Integer>();
			while(rs.next()) {
				shippingNoticeItems.put(rs.getString("model_num"), rs.getInt("num_to_order"));
			}
			rs.close();
			ConnectionManager.clean();
			ShippingNotice sn = new ShippingNotice(Utils.generateShippingId());
			for (Map.Entry<String, Integer> entry : shippingNoticeItems.entrySet()) {
				sn.setMfr(mfr);
				sn.addEntry(entry.getKey(), entry.getValue());
			}
			sn.insert();
			processShippingNotice(sn.getShip_id());
		} catch (SQLException sqle){
			sqle.printStackTrace();
		} finally{
			ConnectionManager.clean();
		}
	}
	
}
