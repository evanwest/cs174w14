package cs174w14.run;

import cs174w14.control.ManagerHomeController;
import cs174w14.view.ManagerHomeView;
import cs174w14.view.ManufactureOrderView;
import cs174w14.view.MonthlySalesSummaryView;

public class ManagerRunner {
	public void run() {
		//all of the views are initialized, all are initially invisible.
		ManagerHomeView homeView = new ManagerHomeView();
		ManufactureOrderView orderView = new ManufactureOrderView();
		MonthlySalesSummaryView monthlySalesSummaryView = new MonthlySalesSummaryView();
		
		//TODO: instantiate all of the necessary Models here...
		
		//link the controllers with their views and models here...
		ManagerHomeController managerHomeController = new ManagerHomeController(homeView, monthlySalesSummaryView, orderView);
		
		//set the homeView as visible to start the manager interface.
		homeView.setVisible(true);
	}

}
