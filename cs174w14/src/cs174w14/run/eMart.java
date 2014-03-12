package cs174w14.run;

import cs174w14.control.LoginController;
import cs174w14.view.LoginView;

public class eMart {
	public static void main(String[] args) {
		LoginView loginView = new LoginView();
		CustomerRunner customerRunner = new CustomerRunner();
		ManagerRunner managerRunner = new ManagerRunner();
		LoginController loginController = new LoginController(loginView, customerRunner, managerRunner);
		
		loginView.setVisible(true);
	}
}
