package cs174w14.view.components;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class ProductScrollPane extends JScrollPane {
	private Map<String, ProductPanel> productPanelMap;
	private JPanel productsPanel;
	private JLabel emptyMessageLabel;
	
	public ProductScrollPane(String emptyMessage) {
		productsPanel = new JPanel();
		productsPanel.setLayout(new GridBagLayout());
		
		productPanelMap = new HashMap<String, ProductPanel>();

		emptyMessageLabel = new JLabel(emptyMessage);
		this.setEmptyMessage(emptyMessage);
		
		this.setViewportView(productsPanel);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	}
	
	public ProductScrollPane() {
		this("No items.");
	}

	//This method clears the main scrollpane and adds these JComponents to it.
	public void setChildren(List<? extends ProductPanel> products) {
		productsPanel.removeAll();
		productPanelMap = new HashMap<String, ProductPanel>();
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridwidth = 1;
		c.gridheight = 1;
		int i = 0;
		if (products != null) {
			for (; i < products.size(); i++) {
				c.gridy = i;
				productsPanel.add(products.get(i), c);
				productPanelMap.put(products.get(i).getStockNumber(), products.get(i));
			}
		}

		c.gridx = 0;
		c.gridy = i;
		c.weightx = 1.0;
		c.weighty = 1.0;
		if (i++ == 0) {
			c.fill = GridBagConstraints.NONE;
			productsPanel.add(emptyMessageLabel, c);
		}
		c.gridx = 0;
		c.gridy = i;
		c.anchor = GridBagConstraints.PAGE_END;
		productsPanel.add(Box.createGlue(), c);
		this.repaint();
	}
	
	public void setEmptyMessage(String message) {
		emptyMessageLabel.setText(message);
		//TODO: need to redraw here??
	}
	
	public void activateFilterBypasses(boolean b) {
		for (Map.Entry<String, ProductPanel> entries : productPanelMap.entrySet()) {
			entries.getValue().activateFilterBypass(b);
		}
	}
}
