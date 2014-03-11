package cs174w14.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class CustomerHomeView extends JFrame {
	private static final int FRAME_WIDTH = 400;
	private static final int FRAME_HEIGHT = 300;
	
	private static final int WELCOME_LABEL_FONT_SIZE = 22;
	private static final int SEARCH_BUTTON_WIDTH = 160;
	private static final int SEARCH_BUTTON_HEIGHT = 80;
	
	private JPanel panel;
	
	private JLabel welcomeLabel;
	private JButton searchButton;
	private JButton viewCartButton;
	private JButton previousOrdersButton;
	
	
	public CustomerHomeView() {
		this.setTitle("eMart");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		
		panel = new JPanel();
		
		welcomeLabel = new JLabel("Welcome, valued customer!", SwingConstants.CENTER);
		welcomeLabel.setPreferredSize(new Dimension(FRAME_WIDTH, SEARCH_BUTTON_HEIGHT));
		welcomeLabel.setFont(new Font(welcomeLabel.getFont().getName(), Font.PLAIN, WELCOME_LABEL_FONT_SIZE));
		searchButton = new JButton("Search For Items");
		searchButton.setPreferredSize(new Dimension(SEARCH_BUTTON_WIDTH, SEARCH_BUTTON_HEIGHT));
		previousOrdersButton = new JButton("Previous Orders");
		viewCartButton = new JButton("View Cart");
		
		panel.add(welcomeLabel);
		panel.add(viewCartButton);
		panel.add(previousOrdersButton);
		panel.add(searchButton);
		
		this.getContentPane().add(panel);
		
		this.setResizable(false);
	    this.setLocationRelativeTo(null); // center the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addSearchButtonListener(ActionListener buttonListener) {
		searchButton.addActionListener(buttonListener);
	}
	
	public void addViewCartButtonListener(ActionListener buttonListener) {
		viewCartButton.addActionListener(buttonListener);
	}
	
	public void addPreviousOrdersButtonListener(ActionListener buttonListener) {
		previousOrdersButton.addActionListener(buttonListener);
	}
}
