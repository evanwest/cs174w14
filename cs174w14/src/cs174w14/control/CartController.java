package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JDialog;

import cs174w14.view.CartView;
import cs174w14.view.components.CartProductPanel;
import cs174w14.view.components.CheckoutDialog;

public class CartController {
	private final CartView cartView;
	
	//TODO: add appropriate models to this constructor
	public CartController(CartView cView) {
		cartView = cView;
		
		//set up the action listeners
		cartView.addCheckoutButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				checkout();
			}
		});
	}
	
	public void refresh() {
		//This method gets called by the CustomerHomeController when
		//it sets the CartView to visible so it can get a chance to refresh
		//its visual contents according to any changes to its model.
		cartView.setEmptyMessage("There are no items in your cart.");
		
		//TODO: Grab the list of items in the cart here.
		ArrayList<CartProductPanel> cartContents = new ArrayList<CartProductPanel>();
		for (int i = 0; i < 1; i++) {
			//TODO:fix this code to actually loop through the list of items in the cart (don't forget to fix the condition in the loop)
			//and grabt ehstockNumber and construct a cartProductPanel for each.
			final String stockNumber = "";
			final CartProductPanel cartProductPanel = new CartProductPanel(stockNumber, "", "", "", "", "", "", 0, 0);
			
			cartProductPanel.addQuantityButtonListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					updateCartProductQuantity(stockNumber, cartProductPanel.getQuantity());
				}
			});
			cartContents.add(cartProductPanel);
		}
		cartView.setCartContents(cartContents);
		cartView.setVisible(true);
	}
	
	public void checkout() {
		//TODO: use real subtotal, discount, and ship&hand values to make this dialog
		final CheckoutDialog checkoutDialog = new CheckoutDialog(102400, 5, 10);
		checkoutDialog.addConfirmButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: add here the logic for checking out from the model
				//passed in through the constructor. remember to update customer status
				
				cartView.clearContents();
				cartView.setEmptyMessage("Successfully checked out order number: 4434434");
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
	
	public void updateCartProductQuantity(String stockNumber, int quantity) {
		//TODO: update the quantity of a particular stock number in the cart.
	}
}
