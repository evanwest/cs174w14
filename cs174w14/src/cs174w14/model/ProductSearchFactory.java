package cs174w14.model;

public class ProductSearchFactory {

	private String stock_num=null;
	private String manufacturer=null;
	private String model_number=null;
	private String category=null;
	private String description_attr=null;
	private String description_val=null;
	private String acc_of;

	public ProductSearchFactory(){
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

	public void setAccessoryOf(String stock_num){
		this.acc_of=stock_num;
	}

	public ProductSearchQuery create(){
		StringBuilder query = new StringBuilder();

		query.append("SELECT * FROM Products NATURAL JOIN Depot_Products WHERE ");
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
		if(acc_of!=null){
			query.append("stock_num IN (SELECT acc_stock_num FROM Accessories "
					+ "WHERE acc_of_stock_num='"+this.acc_of+"') AND");
		}
		if(description_attr!=null && description_val!=null){
			query.append("stock_num IN (SELECT stock_num FROM Descriptions "
					+ "WHERE attr='"+this.description_attr+"' AND val='"+this.description_val+"') AND ");
		}

		query.append(" 1=1");

		return new ProductSearchQuery(query.toString());
	}
}
