package cs174w14.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SearchPageView extends JFrame {
	private static final int FRAME_WIDTH = 500;
	private static final int FRAME_HEIGHT = 600;
	
	private static final int LABEL_WIDTH = 200;
	private static final int LABEL_HEIGHT = 14;
	
	private JPanel panel;
	
	private JLabel stockNumberLabel;
	private JLabel accessoryOfLabel;
	private JLabel manufacturerLabel;
	private JLabel modelNumberLabel;
	private JLabel categoryLabel;
	private JLabel descriptionAttrLabel;
	private JLabel descriptionValueLabel;
	
	private JTextField stockNumberField;
	private JTextField accessoryOfField;
	private JTextField manufacturerField;
	private JTextField modelNumberField;
	private JTextField categoryField;
	private JTextField descriptionAttrField;
	private JTextField descriptionValueField;
	private JButton searchButton;
	
	public SearchPageView() {
		this.setTitle("Login to eMart!");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		
		panel = new JPanel();
		
		stockNumberLabel = new JLabel("Stock number: ");
		stockNumberLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		accessoryOfLabel = new JLabel("Accessory of (stock number):");
		accessoryOfLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		manufacturerLabel = new JLabel("Manufacturer: ");
		manufacturerLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		modelNumberLabel = new JLabel("Model number: ");
		modelNumberLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		categoryLabel = new JLabel("Category: ");
		categoryLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		descriptionAttrLabel = new JLabel("Description attribute: ");
		descriptionAttrLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		descriptionValueLabel = new JLabel("Description value: ");
		descriptionValueLabel.setPreferredSize(new Dimension(LABEL_WIDTH, LABEL_HEIGHT));
		
		stockNumberField = new JTextField(20);
		accessoryOfField = new JTextField(20);
		manufacturerField = new JTextField(20);
		modelNumberField = new JTextField(20);
		categoryField = new JTextField(20);
		descriptionAttrField = new JTextField(20);
		descriptionValueField = new JTextField(20);

		searchButton = new JButton("Search");
		
		panel.add(stockNumberLabel);
		panel.add(stockNumberField);
		panel.add(accessoryOfLabel);
		panel.add(accessoryOfField);
		panel.add(manufacturerLabel);
		panel.add(manufacturerField);
		panel.add(modelNumberLabel);
		panel.add(modelNumberField);
		panel.add(categoryLabel);
		panel.add(categoryField);
		panel.add(descriptionAttrLabel);
		panel.add(descriptionAttrField);
		panel.add(descriptionValueLabel);
		panel.add(descriptionValueField);
		panel.add(searchButton);
		
		this.getContentPane().add(panel);
		
		this.setResizable(false);
	    this.setLocationRelativeTo(null); // center the window
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public String getUsername() {
		//return usernameField.getText();
		return null;
	}
	
	public String getPassword() {
		//return passwordField.getText();
		return null;
	}
	
	public void addLoginButtonListener(ActionListener buttonListener) {
		//loginButton.addActionListener(buttonListener);
	}
}
