package org.segfault.foodme;

public class TicketItem {
	
	int orderId;
	int menuItemId;
	short quantity;
	String notes;
	short kitchenStatus;
	
	public TicketItem(int orderId, int menuItemId, short quantity,
			String notes, short kitchenStatus) 
	{
		this.orderId = orderId;
		this.menuItemId = menuItemId;
		this.quantity = quantity;
		this.notes = notes;
		this.kitchenStatus = kitchenStatus;
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
