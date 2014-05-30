package org.segfault.foodme;

public class Subcategory {

	int subcategory_id;
	String name;
	int category;
	
	public Subcategory(int subcategory_id, String name, int category) {
		this.subcategory_id = subcategory_id;
		this.name = name;
		this.category = category;
	}
	
	public int getSubcategoryId() {
		return subcategory_id;
	}
	public void setSubcategoryId(int subcategory_id) {
		this.subcategory_id = subcategory_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	
	

}
