package cs174w14.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ManagerHomeView extends JFrame {
	private static final int FRAME_WIDTH = 550;
	private static final int FRAME_HEIGHT = 500;
	
	private static final int WELCOME_LABEL_FONT_SIZE = 22;
	private static final int WELCOME_LABEL_HEIGHT = 280;
	
	private JTextField summaryMonthField;
	private JTextField customerIDField;
	private JTextField customerStatusField;
	private JTextField statusExpirationField;
	private JTextField stockNumberField;
	private JTextField priceField;
	private JButton printReportButton;
	private JButton updateStatusButton;
	private JButton updatePriceButton;
	private JButton sendOrderButton;
	private JButton deleteTransactionsButton;
	
	public ManagerHomeView() {
		this.setTitle("eMart");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));
		
		JPanel salesSummaryPanel;
		JPanel customerAdjustmentPanel;
		JPanel priceAdjustmentPanel;
		JPanel ordersPanel;
		JPanel salesTransactionsPanel;
		
		//Sales Summary
		salesSummaryPanel = new JPanel();
		salesSummaryPanel.setLayout(new GridLayout(2, 2));
		salesSummaryPanel.setBorder(BorderFactory.createTitledBorder("Sales Summary"));
		
		summaryMonthField = new JTextField(15);
		printReportButton = new JButton("Print Report");
		
		salesSummaryPanel.add(new JLabel("Month (MM/YYY):"));
		salesSummaryPanel.add(new JLabel(""));
		salesSummaryPanel.add(summaryMonthField);
		salesSummaryPanel.add(printReportButton);
		
		//Customer Adjustment
		customerAdjustmentPanel = new JPanel();
		customerAdjustmentPanel.setLayout(new GridLayout(4, 3));
		customerAdjustmentPanel.setBorder(BorderFactory.createTitledBorder("Customer Adjustment"));
		
		customerIDField = new JTextField(15);
		customerStatusField = new JTextField(15);
		statusExpirationField = new JTextField(15);
		updateStatusButton = new JButton("Update Status");
		
		customerAdjustmentPanel.add(new JLabel("Customer ID:"));
		customerAdjustmentPanel.add(new JLabel("Status"));
		customerAdjustmentPanel.add(new JLabel(""));
		customerAdjustmentPanel.add(customerIDField);
		customerAdjustmentPanel.add(customerStatusField);
		customerAdjustmentPanel.add(updateStatusButton);
		
		customerAdjustmentPanel.add(new JLabel(""));
		/*
		customerAdjustmentPanel.add(new JLabel("Experiation (MM/DD/YYYY):"));
		*/
		customerAdjustmentPanel.add(new JLabel(""));
		
		customerAdjustmentPanel.add(new JLabel(""));
		/*
		customerAdjustmentPanel.add(statusExpirationField);
		customerAdjustmentPanel.add(new JLabel(""));
		*/
		
		//Price Adjustment
		priceAdjustmentPanel = new JPanel();
		priceAdjustmentPanel.setLayout(new GridLayout(2, 3));
		priceAdjustmentPanel.setBorder(BorderFactory.createTitledBorder("Price Adjustment"));
		
		stockNumberField = new JTextField(15);
		priceField = new JTextField(15);
		updatePriceButton = new JButton("Update price");
		
		priceAdjustmentPanel.add(new JLabel("Stock Number:"));
		priceAdjustmentPanel.add(new JLabel("Price:"));
		priceAdjustmentPanel.add(new JLabel(""));
		priceAdjustmentPanel.add(stockNumberField);
		priceAdjustmentPanel.add(priceField);
		priceAdjustmentPanel.add(updatePriceButton);
		
		//Orders
		ordersPanel = new JPanel();
		ordersPanel.setLayout(new GridLayout(1, 3));
		ordersPanel.setBorder(BorderFactory.createTitledBorder("Orders"));
		
		sendOrderButton = new JButton("Send an order");
		ordersPanel.add(sendOrderButton);
		ordersPanel.add(new JLabel(""));
		
		//Sales Transactions
		salesTransactionsPanel = new JPanel();
		salesTransactionsPanel.setLayout(new GridLayout(1, 3));
		salesTransactionsPanel.setBorder(BorderFactory.createTitledBorder("Sales Transactions"));
		
		deleteTransactionsButton = new JButton("Delete Old Transactions");
		salesTransactionsPanel.add(deleteTransactionsButton);
		salesTransactionsPanel.add(new JLabel(""));
		
		
		
		JLabel welcomeLabel = new JLabel("Welcome, manager.", SwingConstants.CENTER);
		welcomeLabel.setPreferredSize(new Dimension(FRAME_WIDTH, WELCOME_LABEL_HEIGHT));
		welcomeLabel.setFont(new Font(welcomeLabel.getFont().getName(), Font.PLAIN, WELCOME_LABEL_FONT_SIZE));
		welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		this.add(welcomeLabel, BorderLayout.NORTH);
		this.add(salesSummaryPanel);
		this.add(customerAdjustmentPanel);
		this.add(priceAdjustmentPanel);
		this.add(ordersPanel);
		this.add(salesTransactionsPanel);
				
		//this.setResizable(false);
	    this.setLocationRelativeTo(null); // center the window
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void addPrintReportButtonListener(ActionListener buttonListener) {
		printReportButton.addActionListener(buttonListener);
	}
	
	public void addUpdateStatusButtonListener(ActionListener buttonListener) {
		updateStatusButton.addActionListener(buttonListener);
	}
	
	public void addUpdatePriceButtonListener(ActionListener buttonListener) {
		updatePriceButton.addActionListener(buttonListener);
	}
	
	public void addSendOrderButtonListener(ActionListener buttonListener) {
		sendOrderButton.addActionListener(buttonListener);
	}
	
	public void addDeleteTransactionsButtonListener(ActionListener buttonListener) {
		deleteTransactionsButton.addActionListener(buttonListener);
	}
	
	public String getSummaryMonth() {
		return summaryMonthField.getText();
	}
	
	public String getCustomerID() {
		return customerIDField.getText();
	}
	
	public String getCustomerStatus() {
		return customerStatusField.getText();
	}
	
	public String getStatusExpiration() {
		return statusExpirationField.getText();
	}
	
	public String getStockNumber() {
		return stockNumberField.getText();
	}
	
	public int getPriceCents() {
		float dollars = Float.parseFloat(priceField.getText());
		int priceCents = (int)(dollars*100.0 + 0.5);
		return priceCents;
	}
	
	public static void main(String[] args) {
		ManagerHomeView m = new ManagerHomeView();
		m.setVisible(true);
	}
}
