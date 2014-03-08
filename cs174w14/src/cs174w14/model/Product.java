package cs174w14.model;

import java.util.List;
import java.util.Map;

public class Product {
	String stock_num;
	String model_num;
	int warranty;
	int price_cents;
	String category;
	String manufacturer;
	
	Map<String, String> descriptions=null;
	List<Product> accessories=null;
	
	public Product(String stock_num){
		this.stock_num=stock_num;
		this.manufacturer=null;
		this.model_num=null;
		this.category=null;
		this.price_cents=-1;
		this.warranty=-1;
	}

	public Product(String stock_num, String mfr, String model_num, String category, int price_cents, int warranty){
		this.stock_num=stock_num;
		this.manufacturer=mfr;
		this.model_num=model_num;
		this.category=category;
		this.price_cents=price_cents;
		this.warranty=warranty;
	}

	public String getStockNum() {
		return stock_num;
	}

	public String getModelNum() {
		return model_num;
	}

	public int getWarranty() {
		return warranty;
	}

	public int getPriceCents() {
		return price_cents;
	}

	public String getCategory() {
		return category;
	}

	public String getManufacturer() {
		return manufacturer;
	}
	
	public Map<String, String> getDescriptions(){
		if(descriptions==null){
			loadDescriptions();
		}
		return this.descriptions;
	}
	
	public String getDescription(String key){
		if(descriptions==null){
			loadDescriptions();
		}
		return this.descriptions.get(key);
	}
	
	public List<Product> getAccessories(){
		return this.accessories;
	}
	
	private void fillStub(){
		//do SQL here? maybe pass self to SQL handler
		//this fills in other fields for Product created with just stock num
	}
	
	private void loadDescriptions(){
		//do SQL here? maybe pass self to SQL handler
	}
}
