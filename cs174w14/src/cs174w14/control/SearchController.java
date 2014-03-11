package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import cs174w14.view.SearchResultsView;
import cs174w14.view.SearchView;
import cs174w14.view.components.CartProductPanel;
import cs174w14.view.components.SearchResultProductPanel;

public class SearchController {
	private final SearchView searchView;
	private final SearchResultsView searchResultsView;
	
	//TODO: add appropriate models to this constructor
	public SearchController(SearchView sView, SearchResultsView srView) {
		searchView = sView;
		searchResultsView = srView;
		
		//set up the action listeners
		searchView.addSearchButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				performSearch(
						searchView.getStockNumber(),
						searchView.getAccessoryOf(),
						searchView.getManufacturer(),
						searchView.getModelNumber(),
						searchView.getCategory(),
						searchView.getDescriptionAttr(),
						searchView.getDescriptionValue());
			}
		});
		searchResultsView.addBackButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchResultsView.setVisible(false);
				searchView.setVisible(true);
			}
		});
	}
	
	public void performSearch(
			String stockNumber,
			String accessoryOf,
			String manufacturer,
			String modelNumber,
			String category,
			String descriptionAttr,
			String descriptionValue) {
		
		//TODO: perform the search query here and grab the list of results
		ArrayList<SearchResultProductPanel> searchResults = new ArrayList<SearchResultProductPanel>();
		for (int i = 0; i < 0; i++) {
			final String stockNum = stockNumber;
			//TODO:fix this code to actually loop through the list of results (don't forget to fix the condition in the loop)
			//and construct a SearchResultProductPanel for each.
			final SearchResultProductPanel searchResultPanel = new SearchResultProductPanel(stockNum, "", "", "", "", "", "", 0);
			
			searchResultPanel.addQuantityButtonListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					addToCart(stockNum, searchResultPanel.getQuantity());
				}
			});
			searchResults.add(searchResultPanel);
		}
		searchResultsView.setResults(searchResults);
		
		searchView.setVisible(false);
		searchResultsView.setVisible(true);
	}
	
	public void addToCart(String stockNumber, int quantity) {
		//TODO: Write the code to add the qty of this stockNumber to the cart model held in this class
	}
}
