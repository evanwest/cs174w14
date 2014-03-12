package cs174w14.run;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class fills the database with the stock information provided
 *
 */
public class PopulateDatabaseRunner {
	
	public static void main(String[] args){
		//add users
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
		
		} catch (NoSuchAlgorithmException nsae){
			
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
