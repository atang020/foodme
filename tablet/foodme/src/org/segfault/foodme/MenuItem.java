package org.segfault.foodme;

public class MenuItem {

	int menuItemId;
	int subcategoryId;
	String name;
	String description;
	String picturePath;
	double price;
	
	public MenuItem(){}

	public MenuItem(int menuItemId, int subcategoryId, String name,
			String description, String picturePath, double price) 
	{
		this.menuItemId = menuItemId;
		this.subcategoryId = subcategoryId;
		this.name = name;
		this.description = description;
		this.picturePath = picturePath;
		this.price = price;
	}

	public int getMenuItemId()
	{
		return menuItemId;
	}

	public void setMenuItemId(int menuItemId) 
	{
		this.menuItemId = menuItemId;
	}

	public int getSubcategoryId() 
	{
		return subcategoryId;
	}

	public void setSubcategoryId(int subcategoryId) 
	{
		this.subcategoryId = subcategoryId;
	}

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	public String getDescription() 
	{
		return description;
	}

	public void setDescription(String description) 
	{
		this.description = description;
	}

	public String getPicturePath() 
	{
		return picturePath;
	}

	public void setPicturePath(String picturePath) 
	{
		this.picturePath = picturePath;
	}

	public double getPrice() 
	{
		return price;
	}

	public void setPrice(double price) 
	{
		this.price = price;
	}
}
