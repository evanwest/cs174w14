package cs174w14.view;

public class PreviousOrderProductPanel extends ProductPanel {
	public PreviousOrderProductPanel(
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
				priceCents);
		setQuantity(quantity);
	}
}
