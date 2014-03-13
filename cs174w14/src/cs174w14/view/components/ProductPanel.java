package cs174w14.view.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.DocumentFilter.FilterBypass;

import cs174w14.model.Utils;

/* 
 *   |----Category----------------------------------------------|
 *   |  _________________________________________          ___  | 
 *   | |  Stock # |  Manufacturer   |  Model #   | $$$.$$ | 1 | |   <-- infoGridPanel (GridBagLayout)
 *   | |-----------------------------------------|         ---  |
 *   | |          |                 |            | Add to Cart  |
 *   | |-----------------------------------------|              |
 *   |                                                          |
 *   | > Details:                                               |   <-- JLabel  
 *   |----------------------------------------------------------|
 * 
 * 
 * 
 * 
 *   |----Category----------------------------------------------|
 *   |  _________________________________________          ___  |
 *   | |  Stock # |  Manufacturer   |  Model #   | $$$.$$ | 1 | |   <-- infoGridPanel (GridBagLayout)
 *   | |-----------------------------------------|         ---  |
 *   | |          |                 |            | Add to Cart  |
 *   | |-----------------------------------------|              |
 *   |                                                          |
 *   | v Details:                                               |   <-- JLabel
 *   | Description:                                             |   <-- detailsPanel (BoxLayout)
 *   |  _________________________________________               |
 *   | |                                         |              | 
 *   | |                                         |              |
 *   | |-----------------------------------------|              |
 *   |                                                          |
 *   |  Accessory Of:                                           |
 *   |  _________________________________________               |
 *   | |                                         |              |
 *   | |                                         |              |
 *   | |-----------------------------------------|              |
 *   |                                                          |
 *   |  Warranty:                                               |
 *   |----------------------------------------------------------|
 * 
 *  
 */

public class ProductPanel extends JPanel {
	private static final int WIDTH = 700;
	private static final int HEIGHT = 300;
	
	private JPanel infoGridPanel;
	private JPanel detailsPanel;

	private JLabel stockNumberLabel;
	private JLabel manufacturerLabel;
	private JLabel modelNumberLabel;
	private final JLabel inStockLabel;
	private JLabel priceLabel;
	private JTextField quantityField;
	private JButton quantityButton;

	private JLabel descriptionLabel;
	private JTextArea descriptionArea;
	private JLabel warrantyLabel;
	private JLabel accessoryOfLabel;
	private JTextArea accessoryOfArea;
	
	private boolean expanded;
	
	public ProductPanel(
			String stockNumber,
			String category,
			String manufacturer,
			String modelNumber,
			String description, 
			String warranty,
			String accessoryOf,
			int inStock,
			int priceCents,
			String buttonLabel) {
		
		expanded = false;
		
		//this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.setBorder(BorderFactory.createTitledBorder(category));
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		infoGridPanel = new JPanel();
		infoGridPanel.setLayout(new GridBagLayout());
		infoGridPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		detailsPanel = new JPanel();
		detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.PAGE_AXIS));
		detailsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		stockNumberLabel = new JLabel(stockNumber, SwingConstants.LEFT);
		manufacturerLabel = new JLabel(manufacturer, SwingConstants.LEFT);
		modelNumberLabel = new JLabel(modelNumber, SwingConstants.LEFT);
		inStockLabel = new JLabel(String.valueOf(inStock), SwingConstants.LEFT);
		priceLabel = new JLabel(Utils.centsToDollarString(priceCents),
				SwingConstants.RIGHT);
		
		quantityField = new JTextField("0", 3);
		
		// make it so only numbers can be inputted into the qtyField.
		((AbstractDocument)quantityField.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void remove(FilterBypass fb, int offset,
					int length) throws BadLocationException {
				String totalStr = fb.getDocument().getText(fb.getDocument().getStartPosition().getOffset(), fb.getDocument().getLength());
				
				// if the user tries to make the field empty, make its contents = "0"
				if (totalStr.length() == length) {
			    	fb.replace(fb.getDocument().getStartPosition().getOffset(), fb.getDocument().getLength(), "0", null);
			    }
			}
			
			@Override
			public void insertString(FilterBypass fb, int off
			                    , String str, AttributeSet attr) 
			                            throws BadLocationException 
			{
			    // remove non-digits
			    fb.insertString(off, str.replaceAll("\\D++", ""), attr);
			    
			    String totalStr = fb.getDocument().getText(fb.getDocument().getStartPosition().getOffset(), fb.getDocument().getLength());
			    String inStock = inStockLabel.getText();
			    
			    // if the user tries to make the field have a value higher than inStock, set its contents = inStock.
			    if (Integer.valueOf(totalStr) > Integer.valueOf(inStock)) {
			    	fb.replace(fb.getDocument().getStartPosition().getOffset(), fb.getDocument().getLength(), inStock, attr);
			    }
			    
			    // finally, remove leading 0's
			    totalStr = fb.getDocument().getText(fb.getDocument().getStartPosition().getOffset(), fb.getDocument().getLength());
			    fb.replace(fb.getDocument().getStartPosition().getOffset(), fb.getDocument().getLength(), Integer.valueOf(totalStr).toString(), attr);
			} 
			@Override
			public void replace(FilterBypass fb, int off
			        , int len, String str, AttributeSet attr) 
			                        throws BadLocationException 
			{
			    // remove non-digits
			    fb.replace(off, len, str.replaceAll("\\D++", ""), attr);
			    
			    String totalStr = fb.getDocument().getText(fb.getDocument().getStartPosition().getOffset(), fb.getDocument().getLength());
			    String inStock = inStockLabel.getText();

			    // if the user tries to make the field have a value higher than inStock, set its contents = inStock.
			    if (Integer.valueOf(totalStr) > Integer.valueOf(inStock)) {
			    	fb.replace(fb.getDocument().getStartPosition().getOffset(), fb.getDocument().getLength(), inStock, attr);
			    }
			    
			    // finally, remove leading 0's
			    totalStr = fb.getDocument().getText(fb.getDocument().getStartPosition().getOffset(), fb.getDocument().getLength());
			    fb.replace(fb.getDocument().getStartPosition().getOffset(), fb.getDocument().getLength(), Integer.valueOf(totalStr).toString(), attr);
			}
		});
		quantityButton = new JButton(buttonLabel);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(10, 10, 0, 15);
		//infoGridPanel row 1
		c.gridx = 0; c.gridy = 0;
		c.weightx = 0.0;
		infoGridPanel.add(new JLabel("Stock #:     ", SwingConstants.LEFT), c);
		c.gridx = 1; c.gridy = 0;
		infoGridPanel.add(new JLabel("Manufacturer:", SwingConstants.LEFT), c);
		c.gridx = 2; c.gridy = 0;
		infoGridPanel.add(new JLabel("Model #:     ", SwingConstants.LEFT), c);
		c.gridx = 3; c.gridy = 0;
		infoGridPanel.add(new JLabel("In stock: "), c);
		c.gridx = 4; c.gridy = 0;
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.EAST;
		infoGridPanel.add(priceLabel, c);
		c.gridx = 5; c.gridy = 0;
		c.weightx = 0.0;
		infoGridPanel.add(quantityField, c);
		//infoGridPanel row 2
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 10, 10, 15);
		c.gridx = 0; c.gridy = 1;
		c.weightx = 0.0;
		infoGridPanel.add(stockNumberLabel, c);
		c.gridx = 1; c.gridy = 1;
		infoGridPanel.add(manufacturerLabel, c);
		c.gridx = 2; c.gridy = 1;
		infoGridPanel.add(modelNumberLabel, c);
		c.gridx = 3; c.gridy = 1;
		infoGridPanel.add(inStockLabel, c);
		c.gridx = 5; c.gridy = 1;
		infoGridPanel.add(quantityButton, c);
		
		this.add(infoGridPanel);
		
		//details

		JLabel detailsLabel = new JLabel("Details:");
		detailsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		detailsLabel.addMouseListener(new MouseListener() {
			 public void mouseClicked(MouseEvent e) {
			       if (!expanded) {
			    	   System.out.println("clicked!");
			    	   expand();
			       }
			       else {
			    	   unexpand();
			       }
			       expanded = !expanded;
			 }
			 public void mousePressed(MouseEvent e) {}
			 public void mouseReleased(MouseEvent e) {}
			 public void mouseEntered(MouseEvent e) {}
			 public void mouseExited(MouseEvent e) {}
		});
		
		this.add(detailsLabel);
		
		descriptionArea = new JTextArea(description);
		descriptionArea.setMargin(new Insets(10,10,10,10));
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		descriptionArea.setEditable(false);
		
		descriptionLabel = new JLabel("<HTML><BR>Description:</HTML>");
		accessoryOfLabel = new JLabel("<HTML><BR>Accessory of (model numbers):</HTML>");
		accessoryOfArea = new JTextArea(accessoryOf);
		accessoryOfArea.setMargin(new Insets(10,10,10,10));
		accessoryOfArea.setLineWrap(true);
		accessoryOfArea.setWrapStyleWord(true);
		accessoryOfArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		accessoryOfArea.setEditable(false);
		
		warrantyLabel = new JLabel("<HTML><BR> Warranty: " + warranty + "</HTML>");		
		
		detailsPanel.add(descriptionLabel);
		detailsPanel.add(descriptionArea);
		detailsPanel.add(new JLabel(""));
		detailsPanel.add(new JLabel(""));
		detailsPanel.add(accessoryOfLabel);
		detailsPanel.add(accessoryOfArea);
		detailsPanel.add(new JLabel(""));
		detailsPanel.add(new JLabel(""));
		detailsPanel.add(warrantyLabel);
		detailsPanel.setVisible(false);
		this.add(detailsPanel);
		
	}
	
	public ProductPanel(
			String stockNumber,
			String category, 
			String manufacturer,
			String modelNumber,
			String description, 
			String warranty, 
			String accessoryOf,
			int inStock,
			int priceCents) {
		this( stockNumber,
			 category, 
			 manufacturer,
			 modelNumber,
			 description, 
			 warranty, 
			 accessoryOf,
			 inStock,
			 priceCents,
			 "");
		this.quantityButton.setVisible(false);
		this.quantityField.setEditable(false);
	}
	
	private void expand() {
		detailsPanel.setVisible(true);
		this.repaint();
	}
	
	private void unexpand() {
		detailsPanel.setVisible(false);
		this.repaint();
	}
	
	public void addQuantityButtonListener(ActionListener buttonListener) {
		quantityButton.addActionListener(buttonListener);
	}
	
	public int getQuantity() {
		return Integer.parseInt(quantityField.getText());
	}
	
	public void setQuantity(int quantity) {
		quantityField.setText((new Integer(quantity)).toString());
	}
	
	public String getStockNumber() {
		return stockNumberLabel.getText();
	}
	
	public void updateInStock() {
		int inStock = Integer.valueOf(inStockLabel.getText());
		int quantity = Integer.valueOf(quantityField.getText());
		inStockLabel.setText(String.valueOf(inStock-quantity));
		quantityField.setText("0");
		this.repaint();
	}
	
}
