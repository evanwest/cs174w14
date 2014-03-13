package cs174w14.control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import cs174w14.model.Customer;
import cs174w14.model.LoyaltyClass;
import cs174w14.model.ManagerUtils;
import cs174w14.model.Product;
import cs174w14.model.ShippingNotice;
import cs174w14.model.Utils;
import cs174w14.view.ManagerHomeView;
import cs174w14.view.ManufactureOrderView;
import cs174w14.view.MonthlySalesSummaryView;
import cs174w14.view.components.ConfirmationDialog;

public class ManagerHomeController {
	final ManagerHomeView managerHomeView;
	final MonthlySalesSummaryView monthlySalesSummaryView;
	final ManufactureOrderView manufactureOrderView;

	public ManagerHomeController(ManagerHomeView mhView, MonthlySalesSummaryView mssView, ManufactureOrderView moView) {
		managerHomeView = mhView;
		monthlySalesSummaryView = mssView;
		manufactureOrderView = moView;

		mhView.addPrintReportButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printMonthlyReport(managerHomeView.getSummaryMonth());
			}
		});

		mhView.addUpdateStatusButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateCustomerStatus(
						managerHomeView.getCustomerID(),
						managerHomeView.getCustomerStatus());
			}
		});

		mhView.addUpdatePriceButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateProductPrice(
						managerHomeView.getStockNumber(),
						managerHomeView.getPriceCents());
			}
		});

		mhView.addSendOrderButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				manufactureOrderView.refresh();
				manufactureOrderView.setVisible(true);
			}
		});

		mhView.addDeleteTransactionsButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteTransactions();
			}
		});

		moView.addSendOrderButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ShippingNotice sn = new ShippingNotice(Utils.generateShippingId());

				List<String> modelNums = manufactureOrderView.getModelNumbers();
				List<String> qtys = manufactureOrderView.getQuantities();
				sn.setMfr(manufactureOrderView.getManufacturer());
				for(int i=0; i<qtys.size(); ++i){
					try{
						sn.addEntry(modelNums.get(i), Integer.valueOf(qtys.get(i)));
					} catch (SQLException sqle){
						sqle.printStackTrace();
						continue;
					}
				}
				sn.insert();
			}
		});
	}

	public void printMonthlyReport(String summaryMonth) {
		//TODO: get the highest paying customer, product sales info and
		//category sales info and set it to the monthlySalesSummaryView as below:

		/*
		List<String[]> productSalesInfo = new ArrayList<String[]>();
		productSalesInfo.add(new String[] {"AA4823", "200", "102400"});
		productSalesInfo.add(new String[] {"AA3834", "400", "1899"});
		 */

		/*
		List<String[]> categorySalesInfo = new ArrayList<String[]>();
		categorySalesInfo.add(new String[] {"Laptop", "200", "102400"});
		categorySalesInfo.add(new String[] {"Keyboard", "400", "1899"});
		 */
		monthlySalesSummaryView.setHighestPayingCustomer(ManagerUtils.getUserReport(summaryMonth)[0]);
		monthlySalesSummaryView.setProductSales(ManagerUtils.getProductReport(summaryMonth));
		monthlySalesSummaryView.setCategorySales(ManagerUtils.getCategoryReport(summaryMonth));
		monthlySalesSummaryView.setVisible(true);
	}

	public void updateCustomerStatus(String customerID, String status) {
		try{
			Customer c = new Customer(customerID);
			c.fill();
			LoyaltyClass lc = new LoyaltyClass(status);
			c.setLoyalty(lc.getId());
			c.setLoyalty_expiration(1);
			c.push();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		}	
	}

	public void updateProductPrice(String stockNumber, int priceCents) {
		try{
			Product p = new Product(stockNumber);
			p.fill();
			p.setPriceCents(priceCents);
			p.push();
		} catch (SQLException sqle){
			sqle.printStackTrace();
		}
	}

	public void deleteTransactions() {
		final ConfirmationDialog confirmDialog = new ConfirmationDialog(
				"Are you sure you would like to delete all  sales transactions that are no longer needed in computing customer status?");
		confirmDialog.addConfirmButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ManagerUtils.removeOldOrderHistory();
				confirmDialog.dispose();
			}
		});
		confirmDialog.addCancelButtonListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmDialog.dispose();
			}
		});
		confirmDialog.setVisible(true);	}
}
