package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import cs174w14.model.Customer;
import cs174w14.model.CustomerOrder;
import cs174w14.model.LoyaltyClass;
import cs174w14.model.Product;
import cs174w14.view.PreviousOrdersView;
import cs174w14.view.components.CheckoutDialog;
import cs174w14.view.components.PreviousOrderProductPanel;

public class PreviousOrdersController {
	private final PreviousOrdersView previousOrdersView;
	private final String username;

	//TODO: add the appropriate models to this constructor
	public PreviousOrdersController(PreviousOrdersView poView, String username) {
		previousOrdersView = poView;
		this.username=username;

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
		try{
			CustomerOrder co = new CustomerOrder(Integer.valueOf(orderNumber));
			co.fill();
			List<PreviousOrderProductPanel> previousOrderContents = new ArrayList<PreviousOrderProductPanel>();
			for (Map.Entry<Product, Integer> entry : co.getContents().entrySet()) {
				Product p = entry.getKey();
				p.fill();
				final String stockNumber = p.getStockNum();
				final PreviousOrderProductPanel previousOrderProductPanel = new PreviousOrderProductPanel(
						stockNumber, p.getCategory(), p.getManufacturer(), p.getModelNum(), p.getDescriptionParagraph(),
						String.valueOf(p.getWarranty()), p.getAccessoryOfParagraph(), p.getPriceCents(), entry.getValue());
				previousOrderContents.add(previousOrderProductPanel);
			}
			previousOrdersView.setOrder(previousOrderContents);
		} catch (SQLException sqle){
			sqle.printStackTrace();
		}
	}

	public void rerunOrder(String orderNumber) {		
		//TODO: use real subtotal, discount, and ship&hand values to make this dialog
		CustomerOrder co = new CustomerOrder(Integer.valueOf(orderNumber));
		try{
			co.fill();
		} catch(SQLException sqle){
			sqle.printStackTrace();
			//this is a fata error for this operation
			return;
		}
		int subtotal=0, discount=0, ship_hand=0;
		for(Map.Entry<Product, Integer> entry : co.getContents().entrySet()){
			Product p = entry.getKey();
			int qty = entry.getValue();
			try{
				p.fill();
			} catch (SQLException sqle){
				sqle.printStackTrace();
				continue;
			}
			subtotal+=p.getPriceCents()*qty;
		}
		try{
			Customer c = new Customer(username);
			c.fill();
			LoyaltyClass lc = new LoyaltyClass(c.getLoyaltyExpiration()>0 ? c.getLoyalty() : c.getLoyaltyTemp());
			lc.fill();
			discount=lc.getDiscount_pct();
			ship_hand=lc.getShipping_handling_pct();
		} catch(SQLException sqle){
			System.err.println("Error loading info for customer: "+username);
			sqle.printStackTrace();
		}

		final String orderNo = orderNumber;
		final CheckoutDialog checkoutDialog = new CheckoutDialog(subtotal, discount, ship_hand);
		checkoutDialog.addConfirmButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					//TODO: send to edepot?

					CustomerOrder co = new CustomerOrder(Integer.valueOf(orderNo));
					co.fill();
					co.recalculate();
					co.insert();
					Customer cust = new Customer(co.getCustId());
					cust.fill();
					cust.updateStatus();
					cust.push();
					previousOrdersView.clearContents();
					previousOrdersView.setEmptyMessage("Order number " + orderNo + " rerun as order number "+co.getOrderNum());
					checkoutDialog.dispose();
				} catch(SQLException sqle){
					sqle.printStackTrace();
				}
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
