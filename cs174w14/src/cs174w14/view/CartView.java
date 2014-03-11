package cs174w14.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import cs174w14.view.components.CartProductPanel;
import cs174w14.view.components.ProductScrollPane;

public class CartView extends JFrame {
	private static final int FRAME_WIDTH = 600;
	private static final int FRAME_HEIGHT = 600;
	
	private JButton checkoutButton;
	private ProductScrollPane productScrollPane;
	
	public CartView() {
		this.setTitle("Shopping Cart");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLayout(new BorderLayout());
		
		JPanel cartPanel = new JPanel();
		cartPanel.setLayout(new GridBagLayout());
		
		checkoutButton = new JButton("Checkout");
		checkoutButton.setVisible(false);
		
		productScrollPane = new ProductScrollPane("There are no items in your cart.");

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,0,10,0);
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;   //request any extra vertical space
		c.fill = GridBagConstraints.BOTH;
		cartPanel.add(productScrollPane, c);
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.0;
		c.weighty = 0.0;
		c.fill = GridBagConstraints.NONE;
		c.anchor = GridBagConstraints.PAGE_END;
		cartPanel.add(checkoutButton, c);
		
		this.add(cartPanel);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //TODO
	}
	
	public void clearContents() {
		checkoutButton.setVisible(false);
		productScrollPane.setChildren(null);
	}
	
	public void setCartContents(ArrayList<CartProductPanel> order) {
		if (order != null && order.size() > 0) {
			checkoutButton.setVisible(true);
		}
		else {
			checkoutButton.setVisible(false);
		}
		productScrollPane.setChildren(order);
	}
	
	public void addCheckoutButtonListener(ActionListener buttonListener) {
		checkoutButton.addActionListener(buttonListener);
	}
	
	public void setEmptyMessage(String message) {
		productScrollPane.setEmptyMessage(message);
		productScrollPane.repaint();
	}
}
