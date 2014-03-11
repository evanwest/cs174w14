package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import cs174w14.model.Cart;
import cs174w14.model.Customer;
import cs174w14.model.CustomerOrder;
import cs174w14.model.LoyaltyClass;
import cs174w14.model.Product;
import cs174w14.view.CartView;
import cs174w14.view.components.CartProductPanel;
import cs174w14.view.components.CheckoutDialog;

public class CartController {
	private final CartView cartView;
	private final Cart cart;

	//TODO: add appropriate models to this constructor
	public CartController(CartView cView, Cart c) {
		cartView = cView;
		cart=c;

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
		try{
			cart.fill();
		} catch (SQLException sqle){
			System.err.println("Error loading cart contents for user: "+cart.getCustomerId());
			sqle.printStackTrace();
		}

		ArrayList<CartProductPanel> cartContents = new ArrayList<CartProductPanel>();
		for (Map.Entry<Product, Integer> entry : cart.getContents().entrySet()) {
			//TODO:fix this code to actually loop through the list of items in the cart (don't forget to fix the condition in the loop)
			//and grabt ehstockNumber and construct a cartProductPanel for each.
			Product prod = entry.getKey();
			int qty = entry.getValue();
			try{
				prod.fill();
			} catch(SQLException sqle){
				System.err.println("Error loading data for product: "+prod.getStockNum());
				sqle.printStackTrace();
				continue;
			}
			final String stockNumber = prod.getStockNum();
			final CartProductPanel cartProductPanel = new CartProductPanel(stockNumber, 
					prod.getCategory(), prod.getManufacturer(), prod.getModelNum(), prod.getDescriptionParagraph(), 
					Integer.toString(prod.getWarranty()), prod.getAccessoryOfParagraph(), prod.getPriceCents(), qty);

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
		int subtotal=0, total=0, discount=0, ship_hand=0;
		for(Map.Entry<Product, Integer> entry : cart.getContents().entrySet()){
			Product p = entry.getKey();
			int qty = entry.getValue();
			try{
				p.fill();
			} catch (SQLException sqle){
				sqle.printStackTrace();
				continue;
			}
			subtotal+=p.getPriceCents();
		}
		try{
			Customer c = new Customer(cart.getCustomerId());
			c.fill();
			LoyaltyClass lc = new LoyaltyClass(c.getLoyaltyExpiration()>0 ? c.getLoyalty() : c.getLoyaltyTemp());
			lc.fill();
			discount=lc.getDiscount_pct();
			ship_hand=lc.getShipping_handling_pct();
		} catch(SQLException sqle){
			System.err.println("Error loading info for customer: "+cart.getCustomerId());
			sqle.printStackTrace();
		}

		final CheckoutDialog checkoutDialog = new CheckoutDialog(subtotal, discount, ship_hand);
		checkoutDialog.addConfirmButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO: add here the logic for checking out from the model
				//turn this cart into an order, (TODO: pass to store_orders later)
				try{
					CustomerOrder co = new CustomerOrder(cart);
					boolean result = co.insert();
					if(!result){
						System.err.println("Error placing order!");
					}
				} catch(SQLException sqle){
					System.err.println("Error placing order!");
					sqle.printStackTrace();
				}
				
				//now reset cart contents
				for(Map.Entry<Product, Integer> entry : cart.getContents().entrySet() ){
					entry.setValue(0);
				}
				boolean result = cart.push();
				if(!result){
					System.err.println("Error updating cart contents!");
					//TODO: something useful here
				}

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
