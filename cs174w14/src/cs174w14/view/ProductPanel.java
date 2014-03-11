package cs174w14.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/* 
 *    __________________________________________________________
 *   | Category:                                                |
 *   |  _________________________________________          ___  |
 *   | |  Stock # |  Manufacturer   |  Model #   | $$$.$$ | 1 | |
 *   | |-----------------------------------------|         ---  |
 *   | |          |                 |            | Add to Cart  |
 *   | |-----------------------------------------|              |
 *   |                                                          |
 *   | Description:                                             |
 *   |  _________________________________________               |
 *   | |                                         |              |
 *   | |                                         |              |
 *   | |-----------------------------------------|              |
 *   | > Details                                                |
 *   |----------------------------------------------------------|
 * 
 * 
 * 
 * 
 *    __________________________________________________________
 *   | Category:                                                |
 *   |  _________________________________________          ___  |
 *   | |  Stock # |  Manufacturer   |  Model #   | $$$.$$ | 1 | |
 *   | |-----------------------------------------|         ---  |
 *   | |          |                 |            | Add to Cart  |
 *   | |-----------------------------------------|              |
 *   |                                                          |
 *   | Description:                                             |
 *   |  _________________________________________               |
 *   | |                                         |              |
 *   | |                                         |              |
 *   | |-----------------------------------------|              |
 *   | v Details                                                |
 *   |    Warranty:                                             |
 *   |    Accessory Of:                                         |
 *   |----------------------------------------------------------|
 * 
 *  
 */

public class ProductPanel extends JPanel {
	private static final int WIDTH = 500;
	private static final int HEIGHT = 300;
	
	private JPanel infoGridPanel;
	private JPanel detailsPanel;

	private JLabel stockNumberLabel;
	private JLabel manufacturerLabel;
	private JLabel modelNumberLabel;
	private JLabel priceLabel;
	private JTextField quantityField;
	private JButton quantityButton;

	private JTextArea descriptionArea;
	private JLabel warrantyLabel;
	private JLabel accessoryOfLabel;
	
	public ProductPanel(
			String stockNumber,
			String category, 
			String manufacturer,
			String modelNumber,
			String description, 
			String warranty, 
			String accessoryOf,
			int priceCents,
			String buttonLabel) {
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		infoGridPanel = new JPanel();
		infoGridPanel.setLayout(new GridLayout(2, 4));
		detailsPanel = new JPanel();
		
		stockNumberLabel = new JLabel("Stock #: " + stockNumber);
		manufacturerLabel = new JLabel(manufacturer);
		modelNumberLabel = new JLabel(modelNumber);
		descriptionArea = new JTextArea(description);
		warrantyLabel = new JLabel(warranty);
		accessoryOfLabel = new JLabel(accessoryOf);
		priceLabel = new JLabel("$" + (int)(priceCents/100 + 0.5) + "." + priceCents%100);
		quantityField = new JTextField("1", 3);
		quantityButton = new JButton(buttonLabel);
		
		this.setBorder(BorderFactory.createTitledBorder(category));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		
		this.add(stockNumberLabel);
		this.add(manufacturerLabel);
		this.add(modelNumberLabel);
		this.add(warrantyLabel);
		this.add(accessoryOfLabel);
		
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
}
