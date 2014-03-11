package cs174w14.view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProductScrollPane extends JScrollPane {
	private JPanel productsPanel;
	
	public ProductScrollPane() {
		productsPanel = new JPanel();
		productsPanel.setLayout(new GridBagLayout());
		
		this.setViewportView(productsPanel);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);		
}

	//This method clears the main scrollpane and adds these JComponents to it.
	public void setChildren(ProductPanel[] results) {
		productsPanel.removeAll();
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		int i = 0;
		if (results != null) {
			for (; i < results.length; i++) {
				c.gridy = i;
				productsPanel.add(results[i], c);
			}
		}
		
		c.gridx = 0;
		c.gridy = i;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_END;
		productsPanel.add(Box.createGlue(), c);
		this.revalidate();
	}
}
