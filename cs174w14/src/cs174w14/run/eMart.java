package cs174w14.run;

import java.sql.SQLException;

import cs174w14.control.LoginController;
import cs174w14.model.ConnectionManager;
import cs174w14.view.LoginView;

public class eMart {
	public static void main(String[] args) {
		try{
			ConnectionManager.init();
		} catch (SQLException sqle){
			sqle.printStackTrace();
			System.exit(1);
		}
		LoginView loginView = new LoginView();
		CustomerRunner customerRunner = new CustomerRunner();
		LoginController lController = new LoginController(loginView, customerRunner);
		
		loginView.setVisible(true);
	}
}
