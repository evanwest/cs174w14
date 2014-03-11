package cs174w14.view;

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
		priceLabel = new JLabel("$" + (int)(priceCents/100 + 0.5) + "." + String.format("%02d  ", priceCents%100),
				SwingConstants.RIGHT);
		quantityField = new JTextField("1", 3);
		((AbstractDocument)quantityField.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void insertString(FilterBypass fb, int off
			                    , String str, AttributeSet attr) 
			                            throws BadLocationException 
			{
			    // remove non-digits
			    fb.insertString(off, str.replaceAll("\\D++", ""), attr);
			} 
			@Override
			public void replace(FilterBypass fb, int off
			        , int len, String str, AttributeSet attr) 
			                        throws BadLocationException 
			{
			    // remove non-digits
			    fb.replace(off, len, str.replaceAll("\\D++", ""), attr);
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
		c.weightx = 1.0;
		c.anchor = GridBagConstraints.EAST;
		infoGridPanel.add(priceLabel, c);
		c.gridx = 4; c.gridy = 0;
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
		c.gridx = 4; c.gridy = 1;
		infoGridPanel.add(quantityButton, c);
		
		this.add(infoGridPanel);
		
		//details

		System.out.println("output:");
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
		descriptionArea.setAlignmentX(Component.LEFT_ALIGNMENT);
		descriptionArea.setEditable(false);
		
		descriptionLabel = new JLabel("<HTML><BR>Description:</HTML>");
		accessoryOfLabel = new JLabel("<HTML><BR>Accessory of (model numbers):</HTML>");
		accessoryOfArea = new JTextArea(accessoryOf);
		accessoryOfArea.setMargin(new Insets(10,10,10,10));
		accessoryOfArea.setLineWrap(true);
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
			int priceCents) {
		this( stockNumber,
			 category, 
			 manufacturer,
			 modelNumber,
			 description, 
			 warranty, 
			 accessoryOf,
			 priceCents,
			 "");
		this.quantityButton.setVisible(false);
		this.quantityField.setEditable(false);
	}
	
	private void expand() {
		detailsPanel.setVisible(true);
		this.revalidate();
	}
	
	private void unexpand() {
		detailsPanel.setVisible(false);
		this.revalidate();
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
	
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		JScrollPane pane = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		GridBagConstraints c = new GridBagConstraints();
		//c.fill = GridBagConstraints.HORIZONTAL;
		//c.anchor = GridBagConstraints.NORTHWEST;// | GridBagConstraints.NORTH;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new ProductPanel("1000", "Laptop", "Samsung", "200", "Mem: 2GB\nasdfja\nsdfjasdfj\nasdfjasdjfasjdfajsdfja", "12", "", 123000, "Add to Cart"), c);
		c.gridx = 0;
		c.gridy = 1;
		panel.add(new ProductPanel("1000", "Laptop", "Samsung", "200", "Mem: 2GB", "12", "", 123000, "Add to Cart"), c);
		//panel.add(new ProductPanel("1000", "Laptop", "Samsung", "200", "Mem: 2GB", "12", "", 123000, "Add to Cart"));
		//panel.add(new ProductPanel("1000", "Laptop", "Samsung", "200", "Mem: 2GB", "12", "", 123000, "Add to Cart"));
		//panel.add(new ProductPanel("1000", "Laptop", "Samsung", "200", "Mem: 2GB", "12", "", 123000, "Add to Cart"));
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.anchor = GridBagConstraints.PAGE_END;
		panel.add(Box.createGlue(), c);
		
		frame.add(pane, BorderLayout.WEST);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}
