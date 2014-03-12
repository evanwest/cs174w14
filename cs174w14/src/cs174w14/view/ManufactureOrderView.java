package cs174w14.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs174w14.view.components.FootersScrollPane;

public class ManufactureOrderView extends JFrame {
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 600;
	
	private JTextField manufacturerField;
	private JButton addProductButton;
	private JButton sendOrderButton;
	private ArrayList<JTextField> modelNumberFields;
	private ArrayList<JTextField> quantityFields;
	final private FootersScrollPane scrollPane;
	
	public ManufactureOrderView() {
		this.setTitle("Manufacturer Order");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLayout(new BorderLayout());
		
		JPanel manufacturePanel = new JPanel();
		manufacturePanel.setLayout(new GridBagLayout());
		
		JLabel manufacturerLabel = new JLabel("Manufacturer: ");
		manufacturerField = new JTextField("", 10);
		manufacturerField.setMinimumSize(manufacturerField.getPreferredSize());
		
		modelNumberFields = new ArrayList<JTextField>();
		quantityFields = new ArrayList<JTextField>();
		
		scrollPane = new FootersScrollPane();
		addProductButton = new JButton("Add product");
		addProductButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollPane.add(createPanel());
				scrollPane.revalidate();
				scrollPane.repaint();
			}
		});

		JPanel addProductPanel = new JPanel();
		addProductPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,0,10,0);
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		addProductPanel.add(addProductButton, c);
		scrollPane.add(createPanel());
		scrollPane.addFooter(addProductPanel);

		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		manufacturePanel.add(manufacturerLabel, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = 0;
		manufacturePanel.add(manufacturerField, c);
		
		JPanel sendOrderPanel = new JPanel();
		sendOrderPanel.setLayout(new GridBagLayout());
		sendOrderButton = new JButton("Send order");
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		sendOrderPanel.add(sendOrderButton, c);
		
		this.add(manufacturePanel, BorderLayout.NORTH);
		this.add(scrollPane, BorderLayout.CENTER);
		this.add(sendOrderPanel, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //TODO
	}
	
	public void addSendOrderButtonListener(ActionListener buttonListener) {
		sendOrderButton.addActionListener(buttonListener);
	}
	
	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		
		panel.add(new JLabel("Model number:"));
		panel.add(new JLabel("Quantity:"));
		JTextField modelNumberField = new JTextField(15);
		JTextField quantityField = new JTextField(10);
		modelNumberFields.add(modelNumberField);
		quantityFields.add(quantityField);
		panel.add(modelNumberField);
		panel.add(quantityField);
		panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));
		return panel;
	}
	
	public ArrayList<String> getModelNumbers() {
		ArrayList<String> modelNumbers = new ArrayList<String>();
		for (int i = 0; i < modelNumberFields.size(); i++) {
			if (!quantityFields.get(i).getText().isEmpty() && !modelNumberFields.get(i).getText().isEmpty()) {
				modelNumbers.add(modelNumberFields.get(i).getText());
			}
		}
		
		return modelNumbers;
	}
	
	public ArrayList<String> getQuantities() {
		ArrayList<String> quantities = new ArrayList<String>();
		for (int i = 0; i < quantityFields.size(); i++) {
			if (!quantityFields.get(i).getText().isEmpty() && !modelNumberFields.get(i).getText().isEmpty()) {
				quantities.add(quantityFields.get(i).getText());
			}
		}
		
		return quantities;
	}
	
	public void refresh() {
		scrollPane.removeAll();
		scrollPane.add(createPanel());
		scrollPane.revalidate();
		scrollPane.repaint();
	}
	
	public static void main(String[] args) {
		ManufactureOrderView view = new ManufactureOrderView();
		view.setVisible(true);
	}

}
