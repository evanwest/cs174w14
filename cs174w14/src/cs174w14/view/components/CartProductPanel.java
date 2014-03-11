package cs174w14.view.components;

public class CartProductPanel extends ProductPanel {
	public CartProductPanel(
			String stockNumber,
			String category, 
			String manufacturer,
			String modelNumber,
			String description, 
			String warranty, 
			String accessoryOf,
			int priceCents,
			int quantity) {
		
		super(
				stockNumber,
				category,
				manufacturer,
				modelNumber,
				description,
				warranty,
				accessoryOf,
				priceCents,
				"Update Qty ");
		setQuantity(quantity);
	}
}
