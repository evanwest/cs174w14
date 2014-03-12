package cs174w14.view.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class CheckoutDialog extends JDialog {
	private static final int DIALOG_WIDTH = 300;
	private static final int DIALOG_HEIGHT = 200;
	
	private JButton cancelButton;
	private JButton confirmButton;
	private JLabel titlesLabel;
	private JLabel totalsLabel;
	private int totalCents;

	public CheckoutDialog(int subtotalCents, int discountPercentage, int shippingAndHandlingPercentage) {
		this.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		this.setModal(true);

		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BorderLayout());
		dialogPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel headerLabel = new JLabel("Confirm checkout:");
		dialogPanel.add(headerLabel, BorderLayout.NORTH);
		
		String subtotal = String.format("$%d.%02d", (int)(subtotalCents/100 + 0.5), subtotalCents%100);
		int discountCents = (int)(((float)subtotalCents) * (discountPercentage/100.0) + 0.5);
		String discount = String.format("- $%d.%02d", (int)(discountCents/100 + 0.5), discountCents%100);
		//subtotalCents -= discountCents;
		int shippingAndHandlingCents = (int)(((float)subtotalCents) * (shippingAndHandlingPercentage/100.0) + 0.5);
		String shippingAndHandling = String.format("$%d.%02d", (int)(shippingAndHandlingCents/100 + 0.5), shippingAndHandlingCents%100);
		totalCents = subtotalCents + shippingAndHandlingCents - discountCents;
		String total = String.format("$%d.%02d", (int)(totalCents/100 + 0.5), totalCents%100);;
		
		titlesLabel = new JLabel(String.format("<HTML><p>subtotal<BR>discount (%d%%)<BR>shipping & handling (%d%%)<BR>TOTAL</p></HTML>",
				discountPercentage, shippingAndHandlingPercentage));
		
		totalsLabel = new JLabel("<HTML><p align=\"right\">" + subtotal + 
								   "<BR>" + discount +
								   "<BR>" + shippingAndHandling + 
								   "<BR>" + total + "</p></HTML>");
		
		dialogPanel.add(titlesLabel, BorderLayout.WEST);
		dialogPanel.add(totalsLabel, BorderLayout.EAST);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		
		cancelButton = new JButton("Cancel");
		confirmButton = new JButton("Confirm");
		buttonPanel.add(cancelButton);
		buttonPanel.add(confirmButton);
		
		dialogPanel.add(buttonPanel, BorderLayout.SOUTH);
		this.add(dialogPanel);
	    this.setLocationRelativeTo(null); // center the window
	}
	
	public int getTotalCents() {
		return totalCents;
	}
	
	public void addCancelButtonListener(ActionListener buttonListener) {
		cancelButton.addActionListener(buttonListener);
	}
	
	public void addConfirmButtonListener(ActionListener buttonListener) {
		confirmButton.addActionListener(buttonListener);
	}
}
