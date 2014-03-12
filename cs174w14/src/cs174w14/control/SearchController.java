package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs174w14.model.Cart;
import cs174w14.model.Product;
import cs174w14.model.ProductSearchFactory;
import cs174w14.model.ProductSearchQuery;
import cs174w14.view.SearchResultsView;
import cs174w14.view.SearchView;
import cs174w14.view.components.SearchResultProductPanel;

public class SearchController {
	private final SearchView searchView;
	private final SearchResultsView searchResultsView;
	private final Cart cart;
	
	//TODO: add appropriate models to this constructor
	public SearchController(SearchView sView, SearchResultsView srView, Cart c) {
		searchView = sView;
		searchResultsView = srView;
		cart=c;
		
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
		ProductSearchFactory psf = new ProductSearchFactory();
		if (accessoryOf.length()>0) psf.setAccessoryOf(accessoryOf);
		if(stockNumber.length()>0) psf.setStockNum(stockNumber);
		if(manufacturer.length()>0) psf.setManufacturer(manufacturer);
		if(modelNumber.length()>0) psf.setModelNumber(modelNumber);
		if(category.length()>0) psf.setCategory(category);
		if(descriptionValue.length()>0) psf.setDescriptionValue(descriptionValue);
		if(descriptionAttr.length()>0) psf.setDescriptionAttribute(descriptionAttr);
		ProductSearchQuery ps = psf.create();
		
		try{
		List<Product> results = ps.execute();
		ArrayList<SearchResultProductPanel> searchResults = new ArrayList<SearchResultProductPanel>();
		for (Product p : results) {
			p.fill();
			final String stockNum = p.getStockNum();
			final SearchResultProductPanel searchResultPanel = new SearchResultProductPanel(
					p.getStockNum(), p.getCategory(), p.getManufacturer(), 
					p.getModelNum(), p.getDescriptionParagraph(), String.valueOf(p.getWarranty()),
					p.getAccessoryOfParagraph(), p.getPriceCents());
			
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
		} catch (SQLException sqle){
			sqle.printStackTrace();
			//this is a fatal error
		}
	}
	
	public void addToCart(String stockNumber, int quantity) {
		try{
			cart.fill();
			for(Map.Entry<Product, Integer> entry : cart.getContents().entrySet()){
				if(entry.getKey().getStockNum().equals(stockNumber)){
					entry.setValue(entry.getValue()+quantity);
					cart.push();
					return;
				}
			}
			//if not found
			cart.getContents().put(new Product(stockNumber), quantity);
			cart.push();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		}
	}
}
