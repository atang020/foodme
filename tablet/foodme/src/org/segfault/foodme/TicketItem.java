package org.segfault.foodme;

public class TicketItem {
	
	int order_id;
	int menu_item_id;
	short quantity;
	String notes;
	short kitchen_status;
	
	public TicketItem(int order_id, int menu_item_id, short quantity,
			String notes, short kitchen_status) {
		this.order_id = order_id;
		this.menu_item_id = menu_item_id;
		this.quantity = quantity;
		this.notes = notes;
		this.kitchen_status = kitchen_status;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public int getMenu_item_id() {
		return menu_item_id;
	}
	public void setMenu_item_id(int menu_item_id) {
		this.menu_item_id = menu_item_id;
	}
	public short getQuantity() {
		return quantity;
	}
	public void setQuantity(short quantity) {
		this.quantity = quantity;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public short getKitchen_status() {
		return kitchen_status;
	}
	public void setKitchen_status(short kitchen_status) {
		this.kitchen_status = kitchen_status;
	}
	
	
	
}
