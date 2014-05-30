package org.segfault.foodme;

public class Subcategory {

	int subcategoryId;
	String name;
	int category;
	
	public Subcategory(){};
	
	public Subcategory(int subcategoryId, String name, int category) 
	{
		this.subcategoryId = subcategoryId;
		this.name = name;
		this.category = category;
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
	
	public int getCategory()
	{
		return category;
	}
	
	public void setCategory(int category) 
	{
		this.category = category;
	}
}
