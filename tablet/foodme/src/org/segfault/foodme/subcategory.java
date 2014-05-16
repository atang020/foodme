/**
 * 
 */
package org.segfault.foodme;

/**
 * @author peter
 *
 */
public class subcategory {

	/**
	 * 
	 */
	int subcategoryId;
	String name;
	int category;
	
	public subcategory() {
		
	}
	
	public subcategory(int subcategoryId, String name, int category) {
		super();
		this.subcategoryId = subcategoryId;
		this.name = name;
		this.category = category;
	}

	public subcategory(String name, int category) {
		super();
		this.name = name;
		this.category = category;
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

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

}
