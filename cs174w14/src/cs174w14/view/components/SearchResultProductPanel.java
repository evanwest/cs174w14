package cs174w14.view.components;

public class SearchResultProductPanel extends ProductPanel {
	public SearchResultProductPanel(
			String stockNumber,
			String category, 
			String manufacturer,
			String modelNumber,
			String description, 
			String warranty, 
			String accessoryOf,
			int inStock,
			int priceCents) {
		super(
				stockNumber,
				category,
				manufacturer,
				modelNumber,
				description,
				warranty,
				accessoryOf,
				inStock,
				priceCents,
				"Add to cart");
		
	}

}
