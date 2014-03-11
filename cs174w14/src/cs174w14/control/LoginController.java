package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs174w14.run.CustomerRunner;
import cs174w14.view.CustomerHomeView;
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
		String cust_id="TODO";
		loginView.dispose();
		customerRunner.run(cust_id);
	}
}
