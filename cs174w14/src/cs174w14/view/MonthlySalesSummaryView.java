package cs174w14.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import cs174w14.model.Utils;
import cs174w14.view.components.FootersScrollPane;

public class MonthlySalesSummaryView extends JFrame {
	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HEIGHT = 600;
	
	private JLabel highestPayingCustomerLabel;
	private FootersScrollPane productsPane;
	private FootersScrollPane categoriesPane;
	
	public MonthlySalesSummaryView() {
		this.setTitle("Monthly Summary of Sales");
		this.setSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
		this.setLayout(new GridBagLayout());
		
		productsPane = new FootersScrollPane();
		categoriesPane = new FootersScrollPane();
		
		
		JLabel customerLabel = new JLabel("Highest paying customer");
		Font font = customerLabel.getFont();
		Font boldFont = new Font(font.getFontName(), Font.BOLD, font.getSize());
		customerLabel.setFont(boldFont);
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,0,10,0);
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		this.add(customerLabel, c);
		highestPayingCustomerLabel = new JLabel();
		c.gridx = 0;
		c.gridy = 1;
		this.add(highestPayingCustomerLabel, c);
		
		JLabel productsLabel = new JLabel("Stock Number: (number of sales, totalRevenue)");
		productsLabel.setFont(boldFont);
		c.gridx = 0;
		c.gridy = 2;
		this.add(productsLabel, c);
		JLabel categoriesLabel = new JLabel("Category: (number of sales, totalRevenue)");
		categoriesLabel.setFont(boldFont);
		c.gridx = 1;
		c.gridy = 2;
		this.add(categoriesLabel, c);
		
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		this.add(productsPane, c);
		c.gridx = 1;
		c.gridy = 3;
		this.add(categoriesPane, c);
	}

	public void setHighestPayingCustomer(String customerID) {
		highestPayingCustomerLabel.setText(customerID);
	}
	
	public void setProductSales(List<String[]> productSalesInfo) {
		StringBuilder productSalesStringBuilder = new StringBuilder();
		if (productSalesInfo != null && productSalesInfo.size() > 0) {
			for (int i = 0; i < productSalesInfo.size(); i++) {
				String[] row = productSalesInfo.get(i);
				assert row.length == 3;
				
				productSalesStringBuilder.append(row[0]);
				productSalesStringBuilder.append(": (");
				productSalesStringBuilder.append(row[1]);
				productSalesStringBuilder.append(", ");
				productSalesStringBuilder.append(Utils.centsToDollarString(row[2]));
				productSalesStringBuilder.append(")\n");
			}
		}
		JTextArea productSalesInfoArea = new JTextArea();
		productSalesInfoArea.setLineWrap(false);
		productSalesInfoArea.setEditable(false);
		productSalesInfoArea.setText(productSalesStringBuilder.toString());
		productsPane.add(productSalesInfoArea);
		productsPane.revalidate();
		productsPane.repaint();
	}

	public void setCategorySales(List<String[]> categorySalesInfo) {
		StringBuilder categorySalesStringBuilder = new StringBuilder();
		if (categorySalesInfo != null && categorySalesInfo.size() > 0) {
			for (int i = 0; i < categorySalesInfo.size(); i++) {
				String[] row = categorySalesInfo.get(i);
				assert row.length == 3;
				
				int decimalIndex = row[2].length() - 2;
				
				categorySalesStringBuilder.append(row[0]);
				categorySalesStringBuilder.append(": (");
				categorySalesStringBuilder.append(row[1]);
				categorySalesStringBuilder.append(", ");
				categorySalesStringBuilder.append(Utils.centsToDollarString(row[2]));
				categorySalesStringBuilder.append(")\n");
			}
		}
		JTextArea categorySalesInfoArea = new JTextArea();
		categorySalesInfoArea.setLineWrap(false);
		categorySalesInfoArea.setEditable(false);
		categorySalesInfoArea.setText(categorySalesStringBuilder.toString());
		categoriesPane.add(categorySalesInfoArea);
		categoriesPane.revalidate();
		categoriesPane.repaint();
	}
}
