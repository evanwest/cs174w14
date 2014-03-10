package cs174w14.view;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class LoginView extends JFrame {
	private static final int FRAME_WIDTH = 300;
	private static final int FRAME_HEIGHT = 185;
	
	private JPanel panel;
	private JLabel usernameLabel;
	private JLabel passwordLabel;
	
	private JTextField usernameField;
	private JTextField passwordField;
	private JButton loginButton;
	
	public LoginView() {
		this.setTitle("Login to eMart!");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		panel = new JPanel();
		
		usernameLabel = new JLabel("username: ");
		passwordLabel = new JLabel("password: ");
		usernameField = new JTextField(20);
		passwordField = new JPasswordField(20);
		loginButton = new JButton("Login");
		
		panel.add(usernameLabel);
		panel.add(usernameField);
		panel.add(passwordLabel);
		panel.add(passwordField);
		panel.add(loginButton);
		
		this.getContentPane().add(panel);
		
		this.setResizable(false);
	    this.setLocationRelativeTo(null); // center the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public String getUsername() {
		return usernameField.getText();
	}
	
	public String getPassword() {
		return passwordField.getText();
	}
	
	public void addLoginButtonListener(ActionListener buttonListener) {
		loginButton.addActionListener(buttonListener);
	}
}
