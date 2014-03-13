package cs174w14.run;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import cs174w14.model.ConnectionManager;
import cs174w14.model.Customer;
import cs174w14.model.LoyaltyClass;
import cs174w14.model.Product;

/**
 * This class fills the database with the stock information provided
 *
 */
public class PopulateDatabaseRunner {

	private static String[][] users = {
		{"Rhagrid", "Rhagrid", "Rubeus Hagrid", "rhagrid@cs", "123 MyStreet", "Goleta apt A", "Ca", "g", "FALSE",},
		{"Mhooch", "Mhooch", "Madam Hooch", "mhooch@cs", "123 MyStreet", "Goleta apt B", "Ca", "s", "FALSE",},
		{"Amoody", "Amoody", "Alastor Moody", "amoody@cs", "123 MyStreet", "Goleta apt C", "Ca", "n", "FALSE",},
		{"Pquirrell", "Pquirrell", "Professor Quirrell", "pquirrell@cs", "123 MyStreet", "Goleta apt D", "Ca", "n", "FALSE",},
		{"Sblack", "Sblack", "Sirius Black", "sblack@cs", "123 MyStreet", "Goleta apt E", "Ca", "r", "TRUE",},
		{"Ddiggle", "Ddiggle", "Dedalus Diggle", "ddiggle@cs", "123 MyStreet", "Goleta apt F", "Ca", "r", "FALSE",} ,
	};

	private static String[][] accessories = {
		{"AA00301", "AA00201"},
		{"AA00301", "AA00202"},
		{"AA00302", "AA00201"},
		{"AA00302", "AA00202"},
		{"AA00401", "AA00101"},
		{"AA00401", "AA00201"},
		{"AA00401", "AA00202"},
		{"AA00402", "AA00101"},
		{"AA00402", "AA00201"},
		{"AA00402", "AA00202"},
		{"AA00501", "AA00201"},
		{"AA00501", "AA00202"},
		{"AA00601", "AA00201"},
		{"AA00601", "AA00202"},
		{"AA00602", "AA00201"},
		{"AA00602", "AA00202"},
	};

	private static String[][] descriptions = {
		{"AA00101", "Processer speed", "3.33Ghz"},
		{"AA00101", "Ram size", "512 Mb"},
		{"AA00101", "Hard disk size", "100Gb"},
		{"AA00101", "Display Size", "17"},
		{"AA00201", "Processer speed", "2.53Ghz"},
		{"AA00201", "Ram size", "256 Mb"},
		{"AA00201", "Hard disk size", "80Gb"},
		{"AA00201", "Os", "none"},
		{"AA00202", "Processer speed", "2.9Ghz"},
		{"AA00202", "Ram size", "512 Mb"},
		{"AA00202", "Hard disk size", "80Gb"},
		{"AA00301", "Size", "17"},
		{"AA00301", "Weight", "25 lb."},
		{"AA00302", "Size", "17"},
		{"AA00302", "Weight", "9.6 lb."},
		{"AA00401", "Required disk size", "128 MB"},
		{"AA00401", "Required RAM size", "64 MB"},
		{"AA00402", "Required disk size", "128 MB"},
		{"AA00402", "Required RAM size", "64 MB"},
		{"AA00501", "Resoulution", "1200 dpi"},
		{"AA00501", "Sheet capacity", "500"},
		{"AA00501", "Weight", ".4 lb"},
		{"AA00601", "Resoulution", "3.1 Mp"},
		{"AA00601", "Max zoom", "5 times"},
		{"AA00601", "Weight", "24.7 lb"},
		{"AA00602", "Resoulution", "3.1 Mp"},
		{"AA00602", "Max zoom", "5 times"},
		{"AA00602", "Weight", "24.7 lb"},
	};

	private static String[][] products = {
		{"AA00101", "Laptop", "HP", "6111", "12", "1630", "1", "2", "10", "A9" },
		{"AA00201", "Desktop", "Dell", "420", "12", "239", "2", "3", "15", "A7" },
		{"AA00202", "Desktop", "Emachine", "3958", "12", "369.99", "2", "4", "8", "B52" },
		{"AA00301", "Monitor", "Envision", "720", "36", "69.99", "3", "4", "6", "C27" },
		{"AA00302", "Monitor", "Samsung", "712", "36", "279.99", "3", "4", "6", "C13" },
		{"AA00401", "Software", "Symantec", "2005", "60", "19.99", "5", "7", "9", "D27" },
		{"AA00402", "Software", "Mcafee", "2005", "60", "19.99", "5", "7", "9", "D1" },
		{"AA00501", "Printer", "HP", "1320", "12", "299.99", "2", "3", "5", "E7" },
		{"AA00601", "Camera", "HP", "435", "3", "119.99", "2", "3", "9", "F9" },
		{"AA00602", "Camera", "Cannon", "738", "1", "329.99", "2", "3", "5", "F3" },
	};

	/*
		Gold : if the total purchase is > $500.
		Silver : if the total purchase is > $100 but <= $500.
		Green : if the total purchase is > $0 but <= $100.
	 */

	public static void main(String[] args){
		//add loyalties
		try{
			ConnectionManager.init();
			//insertLoyalties();
			//insertUsers();
			insertProducts();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		} finally{
			try{
				ConnectionManager.close();
			} catch (SQLException sqle){
				//nothing
			}
		}
	}

	public static void insertLoyalties(){
		LoyaltyClass gold = new LoyaltyClass('g', 10, 10, "gold", 50001, 10000);
		LoyaltyClass silver = new LoyaltyClass('s', 10, 5, "silver", 10001, 10000);
		LoyaltyClass green = new LoyaltyClass('r', 10, 0, "green", 1, 10000);
		LoyaltyClass _new = new LoyaltyClass('n', 10, 10, "new", 0, 10000);

		gold.insert();
		silver.insert();
		green.insert();
		_new.insert();
	}

	public static void insertUsers(){
		//add users
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			for(String[] user : users){
				String salt = "abcd";
				md.update((user[1]+salt).getBytes("UTF-8"));
				Customer c = new Customer(
						user[0],
						bytesToHex(md.digest()),
						salt,
						user[2],
						null,
						null,
						user[3],
						user[4]+user[5],
						null,
						user[6],
						null,
						user[7].charAt(0),
						'n',
						-1,
						Boolean.valueOf(user[8])
						);
				c.insert();
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
			e.printStackTrace();
		}
	}

	public static void insertProducts(){
		Map<String, Product> prods = new HashMap<String, Product>();
		for(String[] prod : products){
			Product p = new Product(prod[0], prod[2], prod[3], prod[1],
					(int)(Float.valueOf(prod[5])*100), Integer.valueOf(prod[4]),
					Integer.valueOf(prod[7]), prod[9], Integer.valueOf(prod[6]),
					Integer.valueOf(prod[8]), 0);
			p.insert();
			prods.put(prod[0], p);
		}
		insertDescriptions(prods);
		insertAccessories(prods);
		/*
		 * 	public Product(String stock_num, String mfr, String model_num, String category, 
			int price_cents, int warranty, int quantity, String location, int min_stock, 
			int max_stock, int replenishment){
			STOCK#,CATEGORY,MANU,MODEL#,WARRANTY,PRICE,Min,Qty,Max,Location
		 */
	}

	public static void insertDescriptions(Map<String, Product> prods){
		for(String[] desc : descriptions){
			Product p;
			p = prods.get(desc[0]);
			p.getDescriptions().put(desc[1], desc[2]);
		}
	}

	public static void insertAccessories(Map<String, Product> prods){
		for(String[] desc : descriptions){
			Product p, p2;
			p = prods.get(desc[0]);
			p2 = prods.get(desc[1]);

			p.getAccessoryOf().add(p2);
		}
	}

	/**
	 * From: http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
	 */
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for ( int j = 0; j < bytes.length; j++ ) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
}
