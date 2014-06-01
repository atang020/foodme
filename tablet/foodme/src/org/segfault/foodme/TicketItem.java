package org.segfault.foodme;

public class TicketItem {
	
	int ticketItemId = -1;
	int orderId;
	int menuItemIndex;
	int menuItemId;
	short quantity;
	String notes;
	short kitchenStatus;
	ContentResolverMenuItem foodDetails;
	String menuItemName;
	double price;
	
	public int getMenuItemIndex() {
		return menuItemIndex;
	}

	public void setMenuItemIndex(int menuItemIndex) {
		this.menuItemIndex = menuItemIndex;
	}

	public String getMenuItemName() {
		return menuItemName;
	}

	public void setMenuItemName(String menuItemName) {
		this.menuItemName = menuItemName;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public TicketItem(int orderId, int menuItemId, int menuItemIndex, short quantity,
			String notes, short kitchenStatus, String menuItemName, double price) 
	{
		this.orderId = orderId;
		this.menuItemId = menuItemId;
		this.menuItemIndex = menuItemIndex;
		this.quantity = quantity;
		this.notes = notes;
		this.kitchenStatus = kitchenStatus;
		this.menuItemName = menuItemName;
		this.price = price;
	}
	
	@Override
	public String toString() {
		return menuItemName +" ---- "+ notes + " ---- $" + price 
				+ " ---- " + quantity;
	}
	
	public String toString2() {
		// <font color=#80808080>
		String s1 = menuItemName +" | "+ notes + " | $" + price 
				+ " | " + quantity;
		return s1;
	}

	public int getTicketItemId() {
		return ticketItemId;
	}

	public void setTicketItemId(int ticketItemId) {
		this.ticketItemId = ticketItemId;
	}

	public int getOrderId() 
	{
		return orderId;
	}
	
	public void setOrderId(int orderId) 
	{
		this.orderId = orderId;
	}
	
	public int getMenuItemId()
	{
		return menuItemId;
	}
	
	public void setMenuItemId(int menuItemId) 
	{
		this.menuItemId = menuItemId;
	}
	
	public short getQuantity() 
	{
		return quantity;
	}
	
	public void setQuantity(short quantity) 
	{
		this.quantity = quantity;
	}
	
	public String getNotes() 
	{
		return notes;
	}
	
	public void setNotes(String notes) 
	{
		this.notes = notes;
	}
	
	public short getKitchenStatus() 
	{
		return kitchenStatus;
	}
	
	public void setKitchenStatus(short kitchenStatus) 
	{
		this.kitchenStatus = kitchenStatus;
	}
	
	
}
