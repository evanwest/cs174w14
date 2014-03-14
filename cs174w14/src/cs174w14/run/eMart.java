package cs174w14.run;

import java.sql.SQLException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cs174w14.control.LoginController;
import cs174w14.model.ConnectionManager;
import cs174w14.model.eDepot;
import cs174w14.view.LoginView;

public class eMart {
	public static void main(String[] args) {
		try{
			ConnectionManager.init();
		} catch (SQLException sqle){
			sqle.printStackTrace();
			System.exit(1);
		}
		eDepot.init();
		
		try {
			UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginView loginView = new LoginView();
		CustomerRunner customerRunner = new CustomerRunner();
		ManagerRunner managerRunner = new ManagerRunner();
		LoginController loginController = new LoginController(loginView, customerRunner, managerRunner);
		
		loginView.setVisible(true);
	}
}
