package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import cs174w14.view.PreviousOrdersView;
import cs174w14.view.components.CheckoutDialog;
import cs174w14.view.components.PreviousOrderProductPanel;

public class PreviousOrdersController {
	private final PreviousOrdersView previousOrdersView;
	
	//TODO: add the appropriate models to this constructor
	public PreviousOrdersController(PreviousOrdersView poView) {
		previousOrdersView = poView;
		
		//set up the action listeners
		previousOrdersView.addFindOrderButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				findOrder(previousOrdersView.getOrderNumber());
			}
		});
		
		previousOrdersView.addRerunOrderButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				rerunOrder(previousOrdersView.getOrderNumber());
			}
		});
	}
	
	public void refresh() {
		previousOrdersView.clearContents();
		previousOrdersView.setEmptyMessage("Previous order not found.");
	}
	
	public void findOrder(String orderNumber) {
		//TODO: Grab the list of items in the previous order here.
		ArrayList<PreviousOrderProductPanel> previousOrderContents = new ArrayList<PreviousOrderProductPanel>();
		for (int i = 0; i < 1; i++) {
			//TODO:fix this code to actually loop through the list of items in the cart (don't forget to fix the condition in the loop)
			//and grab the stock Number and construct a real cartProductPanel for each.
			final String stockNumber = "";
			final PreviousOrderProductPanel previousOrderProductPanel = new PreviousOrderProductPanel(stockNumber, "", "", "", "", "", "", 0, 0);
			previousOrderContents.add(previousOrderProductPanel);
		}
		previousOrdersView.setOrder(previousOrderContents);
	}
	
	public void rerunOrder(String orderNumber) {		
		//TODO: use real subtotal, discount, and ship&hand values to make this dialog
		final String orderNo = orderNumber;
		final CheckoutDialog checkoutDialog = new CheckoutDialog(102400, 5, 10);
		checkoutDialog.addConfirmButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: add here the logic for rerunning a previous order number
				//remember to update customer status
				
				previousOrdersView.clearContents();
				previousOrdersView.setEmptyMessage("Order number " + orderNo + " rerun as order number 5534435");
				//TODO: fix the above line to print the real order number.
				checkoutDialog.dispose();
			}
		});
		checkoutDialog.addCancelButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkoutDialog.dispose();
			}
		});
		checkoutDialog.setVisible(true);
	}
}
