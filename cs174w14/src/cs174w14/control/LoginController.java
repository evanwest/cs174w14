package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import cs174w14.model.Customer;
import cs174w14.run.CustomerRunner;
import cs174w14.run.ManagerRunner;
import cs174w14.view.LoginView;

public class LoginController {
	private final LoginView loginView;
	private final CustomerRunner customerRunner;

	public LoginController(LoginView lView, CustomerRunner customerRunner) {
		loginView = lView;

		this.customerRunner = customerRunner;

		//set up the action listeners
		loginView.addLoginButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login(loginView.getUsername(), loginView.getPassword());
			}
		});

	}

	public void login(String username, String password) {
		//TODO: perform login, determine if is customer manager.
		// if customer, do:
		loginView.dispose();
		managerRunner.run();
		String hash=password;
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			hash = bytesToHex(md.digest());
			Customer c = new Customer(username);
			c.fill();
			if(c.getPwdHash().equals(hash)){
				//login success
				if(c.getManager()){
					loginView.dispose();
					//managerRunner.run(username);
				} 
				else{
					loginView.dispose();
					customerRunner.run(username);
				}
			}
			else{
				//login failure, return to login page
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
			//trust me, this won't happen
			e.printStackTrace();
			return;
		} catch (SQLException sqle){
			sqle.printStackTrace();
			//TODO: return to login page
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
