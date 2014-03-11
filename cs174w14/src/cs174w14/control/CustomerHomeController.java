package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cs174w14.view.CartView;
import cs174w14.view.CustomerHomeView;
import cs174w14.view.PreviousOrdersView;
import cs174w14.view.SearchView;

public class CustomerHomeController {
	private final CustomerHomeView customerHomeView;
	private final CartView cartView;
	private final CartController cartController;
	private final PreviousOrdersView previousOrdersView;
	private final PreviousOrdersController previousOrdersController;
	private final SearchView searchView;
	
	public CustomerHomeController(
			CustomerHomeView chView,
			CartView cView,
			CartController cController,
			PreviousOrdersView poView,
			PreviousOrdersController poController,
			SearchView sView) {
		customerHomeView = chView;
		cartView = cView;
		cartController = cController;
		previousOrdersView = poView;
		previousOrdersController = poController;
		searchView = sView;
		
		//set up the action listeners
		customerHomeView.addViewCartButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cartController.refresh();
				cartView.setVisible(true);
			}
		});
		
		customerHomeView.addPreviousOrdersButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				previousOrdersController.refresh();
				previousOrdersView.setVisible(true);
			}
		});
		
		customerHomeView.addSearchButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searchView.setVisible(true);
			}
		});
	}
}
