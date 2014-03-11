package cs174w14.view;

public class CartProductPanel extends ProductPanel {
	public CartProductPanel(
			String stockNumber,
			String category, 
			String manufacturer,
			String modelNumber,
			String description, 
			String warranty, 
			String accessoryOf,
			int priceCents) {
		
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
	}
}
