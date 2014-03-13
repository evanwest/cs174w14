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
}
