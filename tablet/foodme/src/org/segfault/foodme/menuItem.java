/**
 * 
 */
package org.segfault.foodme;

/**
 * @author peter
 *
 */
public class menuItem 
{	
	int menuItemId;
	int subcategoryId;
	String name;
	String description;
	String picturePath;
	String notes;
	int quantity;
	double price;
	
	public menuItem(){}
	//NEEDS QUANTITY and NOTES
	public menuItem(int menuItemId, int subcategoryId, String name, String description,
			       String picturePath, double price)
	{
		this.menuItemId = menuItemId;
		this.subcategoryId = subcategoryId;
		this.name = name;
		this.description = description;
		this.picturePath = picturePath;
		this.price = price;
	}
	
	public menuItem(String name, String notes, int quantity, double price) {
		this.name = name;
		this.notes = notes;
		this.quantity = quantity;
		this.price = price;
	}
	
	public int getMenuItemId()
	{
		return menuItemId;
	}

	public int getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(int subcategoryId) {
		this.subcategoryId = subcategoryId;
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
		return picturePath;
	}

	public void setPicturePath(String picturePath) {
		this.picturePath = picturePath;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setMenuItemId(int menuItemId) {
		this.menuItemId = menuItemId;
	}
	
	public String getNotes() {
		return this.notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
