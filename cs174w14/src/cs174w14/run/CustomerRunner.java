package cs174w14.run;

import cs174w14.control.CartController;
import cs174w14.control.CustomerHomeController;
import cs174w14.control.PreviousOrdersController;
import cs174w14.control.SearchController;
import cs174w14.model.Cart;
import cs174w14.view.CartView;
import cs174w14.view.CustomerHomeView;
import cs174w14.view.PreviousOrdersView;
import cs174w14.view.SearchResultsView;
import cs174w14.view.SearchView;


public class CustomerRunner {
	public void run(String cust_id) {
		//all of the views are initialized, all are initially invisible.
		CustomerHomeView homeView = new CustomerHomeView();
		SearchView searchView = new SearchView();
		SearchResultsView searchResultsView = new SearchResultsView();
		CartView cartView = new CartView();
		PreviousOrdersView previousOrdersView = new PreviousOrdersView();
		
		//TODO: instantiate all of the necessary Models here...
		Cart c = new Cart(cust_id);
		
		//link the controllers with their views and models here....
		CartController cartController = new CartController(cartView, c);
		SearchController searchController = new SearchController(searchView, searchResultsView);
		PreviousOrdersController previousOrdersController = new PreviousOrdersController(previousOrdersView);
		
		CustomerHomeController customerHomeController = new CustomerHomeController(
				homeView, cartView, cartController, previousOrdersView, previousOrdersController, searchView);
		
		//set the homeView as visible to start the customer interface.
		homeView.setVisible(true);
	}

}
