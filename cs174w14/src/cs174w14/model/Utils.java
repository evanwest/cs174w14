package cs174w14.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class Utils {
	public static String generateStockNum(){
		Random r = new Random();
		StringBuilder snum = new StringBuilder();
		snum.append((char)(r.nextInt(26)+'A'));
		snum.append((char)(r.nextInt(26)+'A'));
		for(int i=0; i<5; ++i){
			snum.append(r.nextInt(10));
		}
		String stock_number = snum.toString();
		
		//check to make sure this stock number doesn't already exist.
		ProductSearchFactory psf = new ProductSearchFactory();
		psf.setStockNum(stock_number);
		List<Product> products = null;
		try {
			products = psf.create().execute();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		}
		
		if (!products.isEmpty()) {
			// if we got results from our search, try creating the stock num again
			return generateStockNum();
		}
		return stock_number;
	}
	
	public static int generateShippingId(){
		Random r = new Random();
		return Math.abs(r.nextInt(1000000));
	}
	
	public static String centsToDollarString(String priceCents) {
		if (priceCents == null) {
			return null;
		}
		int cents = Math.abs(Integer.parseInt(priceCents));
		
		String ret = String.format("$%d.%02d", (int)(cents/100 + 0.5), cents%100);
		if (cents < 0) {
			return "- " + ret;
		}
		return ret;
	}
	
	public static String centsToDollarString(int priceCents) {
		return centsToDollarString(String.valueOf(priceCents));
	}
}
