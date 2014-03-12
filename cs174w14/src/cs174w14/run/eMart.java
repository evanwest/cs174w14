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
		ManagerRunner managerRunner = new ManagerRunner();
		LoginController loginController = new LoginController(loginView, customerRunner, managerRunner);
		
		loginView.setVisible(true);
	}
}
