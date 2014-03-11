package cs174w14.view;

import java.util.ArrayList;

import cs174w14.view.components.CartProductPanel;
import cs174w14.view.components.PreviousOrderProductPanel;
import cs174w14.view.components.SearchResultProductPanel;

public class ViewTest {

	public static void main(String[] args) {
		SearchResultsView searchResultsView = new SearchResultsView();
		ArrayList<SearchResultProductPanel> results = new ArrayList<SearchResultProductPanel>();
		results.add(new SearchResultProductPanel("1000", "Laptop", "Samsung", "200", "Mem: 2GB", "12", "", 123000));
		searchResultsView.setResults(results);
		searchResultsView.setVisible(true);
		
		
		
		CartView cartView = new CartView();
		ArrayList<CartProductPanel> cartContents = new ArrayList<CartProductPanel>();
		cartContents.add(new CartProductPanel("1000", "Laptop", "Samsung", "200", "Mem: 2GB", "12", "", 123000, 1));
		cartView.setCartContents(cartContents);
		cartView.setVisible(true);
		
		
		
		PreviousOrdersView previousOrdersView = new PreviousOrdersView();
		ArrayList<PreviousOrderProductPanel> previousOrder = new ArrayList<PreviousOrderProductPanel>();
		previousOrder.add(new PreviousOrderProductPanel("1000000", "Laptop", "Samsung", "200", "Mem: 2GB", "12", "", 123000, 10));
		previousOrder.add(new PreviousOrderProductPanel("1000000", "Laptop", "Samsungzzzzzzzzz", "200", "Mem: 2GB", "12", "", 123000, 10));
		previousOrdersView.setOrder(previousOrder);
		previousOrdersView.setVisible(true);
	}
}
