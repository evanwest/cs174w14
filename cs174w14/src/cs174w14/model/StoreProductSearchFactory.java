package cs174w14.model;

public class StoreProductSearchFactory {
	public enum Type{
		products, 
		accessories_only,
		products_and_accessories
	}
	
	private String stock_num=null;
	private String manufacturer=null;
	private String model_number=null;
	private String category=null;
	private String description_attr=null;
	private String description_val=null;
	private Type type;
	
	public StoreProductSearchFactory(){
		this.type=Type.products;
	}

	public void setStockNum(String stock_num) {
		this.stock_num = stock_num;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setModelNumber(String model_number) {
		this.model_number = model_number;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setDescriptionAttribute(String description_attr) {
		this.description_attr = description_attr;
	}

	public void setDescriptionValue(String description_val) {
		this.description_val = description_val;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	public StoreProductSearch create(){
		StringBuilder query = new StringBuilder();
		
		switch(type){
		case products:
			query.append("SELECT * FROM Products WHERE ");
			if(stock_num != null){
				query.append("stock_num='"+stock_num+"' AND ");
			} 
			if(manufacturer!=null){
				query.append("mfr='"+manufacturer+"' AND ");
			}
			if(model_number!=null){
				query.append("model_num='"+model_number+"' AND ");
			}
			if(category!=null){
				query.append("category='"+category+"' AND ");
			}
			break;
		case accessories_only:
			//let's ignore these for now
			break;
		case products_and_accessories:
			//still ignoring these
			break;
		}
		query.append(" 1=1;");

		return new StoreProductSearch(query.toString());
	}
}
