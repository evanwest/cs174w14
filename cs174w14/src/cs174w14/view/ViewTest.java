package cs174w14.view;

import javax.swing.JComponent;

public class ViewTest {

	public static void main(String[] args) {
		/*
		SearchResultsView searchResultsView = new SearchResultsView();
		
		ProductPanel[] results = {new SearchResultProductPanel("1000", "Laptop", "Samsung", "200", "Mem: 2GB", "12", "", 123000)};
		searchResultsView.setResults(results);
		searchResultsView.setVisible(true);
		*/
		
		///*
		PreviousOrdersView previousOrdersView = new PreviousOrdersView();
		
		ProductPanel[] results = {new PreviousOrderProductPanel("1000000", "Laptop", "Samsung", "200", "Mem: 2GB", "12", "", 123000, 10),
				new PreviousOrderProductPanel("1000000", "Laptop", "Samsungzzzzzzzzz", "200", "Mem: 2GB", "12", "", 123000, 10)};
		previousOrdersView.setOrder(results);
		previousOrdersView.setVisible(true);
		//*/
	}
}
