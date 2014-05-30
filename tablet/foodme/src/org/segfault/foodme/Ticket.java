package org.segfault.foodme;

import java.util.Date;

public class Ticket {

	int ticket_id;
	int table_number;
	Date order_date;
	short checked_out;
	short callWaiterStatus;
	
	public Ticket(int ticket_id, int table_number, Date order_date,
			short checked_out, short callWaiterStatus) {
		this.ticket_id = ticket_id;
		this.table_number = table_number;
		this.order_date = order_date;
		this.checked_out = checked_out;
		this.callWaiterStatus = callWaiterStatus;
	}
	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	public int getTable_number() {
		return table_number;
	}
	public void setTable_number(int table_number) {
		this.table_number = table_number;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public short getChecked_out() {
		return checked_out;
	}
	public void setChecked_out(short checked_out) {
		this.checked_out = checked_out;
	}
	public short getCallWaiterStatus() {
		return callWaiterStatus;
	}
	public void setCallWaiterStatus(short callWaiterStatus) {
		this.callWaiterStatus = callWaiterStatus;
	}
}
