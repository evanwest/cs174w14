package cs174w14.model;

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
		return snum.toString();
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
