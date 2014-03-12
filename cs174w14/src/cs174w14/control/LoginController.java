package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs174w14.run.CustomerRunner;
import cs174w14.run.ManagerRunner;
import cs174w14.view.LoginView;

public class LoginController {
	private final LoginView loginView;
	private final CustomerRunner customerRunner;
	private final ManagerRunner managerRunner;
	
	public LoginController(LoginView lView, CustomerRunner customerRunner, ManagerRunner managerRunner) {
		loginView = lView;
		
		this.customerRunner = customerRunner;
		this.managerRunner = managerRunner;
		
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
	}
}
