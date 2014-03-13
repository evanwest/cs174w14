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

import cs174w14.view.components.ProductScrollPane;
import cs174w14.view.components.SearchResultProductPanel;

public class SearchResultsView extends JFrame {
	private static final int FRAME_WIDTH = 665;
	private static final int FRAME_HEIGHT = 600;
	
	private ProductScrollPane productScrollPane;
	private JButton backButton;
	
	public SearchResultsView() {
		this.setTitle("Search Results");
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLayout(new BorderLayout());
		
		JPanel searchResultsPanel = new JPanel();
		searchResultsPanel.setLayout(new GridBagLayout());
				
		productScrollPane = new ProductScrollPane();
		backButton = new JButton("< Back to Search");

		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10,0,10,0);
		c.weightx = 1.0;
		c.gridx = 0;
		c.gridy = 0;
		searchResultsPanel.add(backButton, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.weightx = 0.0;
		c.weighty = 1.0;   //request any extra vertical space
		c.fill = GridBagConstraints.BOTH;
		searchResultsPanel.add(productScrollPane, c);
		
		this.add(searchResultsPanel);
		this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public void setResults(ArrayList<SearchResultProductPanel> results) {
		productScrollPane.setChildren(results);
	}
	
	public void addBackButtonListener(ActionListener buttonListener) {
		backButton.addActionListener(buttonListener);
	}

	public void updateInStock(String stockNumber) {
		productScrollPane.updateInStock(stockNumber);
	}
}
