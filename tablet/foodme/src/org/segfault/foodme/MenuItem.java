package org.segfault.foodme;

public class MenuItem {

	int menu_item_id;
	int subcategory_id;
	String name;
	String description;
	String picture_path;
	double price;
	
	public MenuItem(int menu_item_id, int subcategory_id, String name,
			String description, String picture_path, double price) {
		this.menu_item_id = menu_item_id;
		this.subcategory_id = subcategory_id;
		this.name = name;
		this.description = description;
		this.picture_path = picture_path;
		this.price = price;
	}
	
	public int getMenuItemId() {
		return menu_item_id;
	}
	public void setMenuItemId(int menu_item_id) {
		this.menu_item_id = menu_item_id;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicturePath() {
		return picture_path;
	}
	public void setPicturePath(String picture_path) {
		this.picture_path = picture_path;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
}
