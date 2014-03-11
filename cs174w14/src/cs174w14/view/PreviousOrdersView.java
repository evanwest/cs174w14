package cs174w14.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cs174w14.view.components.PreviousOrderProductPanel;
import cs174w14.view.components.ProductScrollPane;

public class PreviousOrdersView extends JFrame {
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 600;
	
	private JTextField orderNumberField;
	private JButton findOrderButton;
	private JButton rerunOrderButton;
	private ProductScrollPane productScrollPane;
	
	public PreviousOrdersView() {
		this.setTitle("Previous Orders");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLayout(new BorderLayout());
		
		JPanel previousOrdersPanel = new JPanel();
		previousOrdersPanel.setLayout(new GridBagLayout());
		
		JLabel orderNumberLabel = new JLabel("Previous order number: ");
		orderNumberField = new JTextField("", 10);
		orderNumberField.setMinimumSize(orderNumberField.getPreferredSize());
		findOrderButton = new JButton("Find previous order");
		rerunOrderButton = new JButton("Rerun this order");
		rerunOrderButton.setVisible(false);
		
		productScrollPane = new ProductScrollPane("Previous order not found.");

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,0,10,0);
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		previousOrdersPanel.add(orderNumberLabel, c);
		c.weightx = 1.0;
		c.gridx = 1;
		c.gridy = 0;
		previousOrdersPanel.add(orderNumberField, c);
		c.weightx = 1.0;
		c.gridx = 2;
		c.gridy = 0;
		previousOrdersPanel.add(findOrderButton, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		c.weightx = 1.0;
		c.weighty = 1.0;   //request any extra vertical space
		c.fill = GridBagConstraints.BOTH;
		previousOrdersPanel.add(productScrollPane, c);
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 3;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.PAGE_END;
		previousOrdersPanel.add(rerunOrderButton, c);
		
		this.add(previousOrdersPanel);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //TODO
	}
	
	public void setOrder(ArrayList<PreviousOrderProductPanel> order) {
		if (order!= null && order.size() > 0) {
			rerunOrderButton.setVisible(true);
			this.repaint();
		}
		else {
			rerunOrderButton.setVisible(false);
			this.repaint();
		}
		productScrollPane.setChildren(order);
	}
	
	public void clearContents() {
		rerunOrderButton.setVisible(false);
		productScrollPane.setChildren(null);
	}
	
	public void setEmptyMessage(String message) {
		productScrollPane.setEmptyMessage(message);
		productScrollPane.repaint();
	}
	
	public String getOrderNumber() {
		return orderNumberField.getText();
	}
	
	public void addFindOrderButtonListener(ActionListener buttonListener) {
		findOrderButton.addActionListener(buttonListener);
	}
	
	public void addRerunOrderButtonListener(ActionListener buttonListener) {
		rerunOrderButton.addActionListener(buttonListener);
	}
}
