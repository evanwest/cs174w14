package cs174w14.view.components;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import cs174w14.model.Product;
import cs174w14.model.Utils;

public class CheckoutDialog extends JDialog {
	private static final int DIALOG_WIDTH = 300;
	private static final int DIALOG_HEIGHT = 400;
	
	private JButton confirmButton;
	private JLabel titlesLabel;
	private JLabel totalsLabel;
	private int totalCents;

	public CheckoutDialog(Map<Product, Integer> products, int discountPercentage, int shippingAndHandlingPercentage) {
		this.setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
		this.setModal(true);

		FootersScrollPane scrollPane = new FootersScrollPane();
		JPanel dialogPanel = new JPanel();
		dialogPanel.setLayout(new BorderLayout());
		dialogPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		JLabel headerLabel = new JLabel("Receipt:");
		dialogPanel.add(headerLabel, BorderLayout.NORTH);
		
		
		int subtotalCents = 0;
		
		StringBuilder titles = new StringBuilder();
		StringBuilder values = new StringBuilder();
		titles.append("<HTML><p>");
		values.append("<HTML><p align=\"right\">");
		for (Map.Entry<Product, Integer> entry : products.entrySet()) {
			Product product = entry.getKey();
			int qty = entry.getValue();
			
			subtotalCents += qty*product.getPriceCents();
			titles.append("(" + qty + ") " + product.getManufacturer() + " " + product.getModelNum() +"<BR>");
			values.append(Utils.centsToDollarString(qty*product.getPriceCents()) + "<BR>");
		}
		
		int discountCents = (int)(((float)subtotalCents) * (discountPercentage/100.0) + 0.5);
		String discount =Utils.centsToDollarString(-discountCents);
		//subtotalCents -= discountCents;
		int shippingAndHandlingCents = (int)(((float)subtotalCents) * (shippingAndHandlingPercentage/100.0) + 0.5);
		if(subtotalCents>100*100){
			shippingAndHandlingCents=0;
		}
		String shippingAndHandling = Utils.centsToDollarString(shippingAndHandlingCents);
		totalCents = subtotalCents + shippingAndHandlingCents - discountCents;
		String total = Utils.centsToDollarString(totalCents);
		
		titlesLabel = new JLabel(String.format(titles + "Subtotal<BR><BR>discount (%d%%)<BR>shipping & handling (%d%%)<BR><BR>TOTAL</p></HTML>",
				discountPercentage, shippingAndHandlingPercentage));
		
		totalsLabel = new JLabel(values + Utils.centsToDollarString(subtotalCents) + 
								   "<BR><BR>" + discount +
								   "<BR>" + shippingAndHandling + 
								   "<BR><BR>" + total + "</p></HTML>");
		
		dialogPanel.add(titlesLabel, BorderLayout.WEST);
		dialogPanel.add(totalsLabel, BorderLayout.EAST);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(1, 2));
		
		confirmButton = new JButton("Ok");
		buttonPanel.add(confirmButton);
		
		scrollPane.add(dialogPanel, BorderLayout.NORTH);
		scrollPane.add(buttonPanel, BorderLayout.SOUTH);
		this.add(scrollPane);
	    this.setLocationRelativeTo(null); // center the window
	}
	
	public int getTotalCents() {
		return totalCents;
	}
	
	public void addConfirmButtonListener(ActionListener buttonListener) {
		confirmButton.addActionListener(buttonListener);
	}
}
