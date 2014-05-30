package org.segfault.foodme;

import java.util.Date;

public class Ticket {

	int ticketId;
	int tableNumber;
	Date orderDate;
	short checkedOut;
	short callWaiterStatus;
	
	public Ticket(){}

	public Ticket(int ticketId, int tableNumber, Date orderDate,
			short checkedOut, short callWaiterStatus) 
	{
		this.ticketId = ticketId;
		this.tableNumber = tableNumber;
		this.orderDate = orderDate;
		this.checkedOut = checkedOut;
		this.callWaiterStatus = callWaiterStatus;
	}

	public int getTicketId() 
	{
		return ticketId;
	}

	public void setTicketId(int ticketId) 
	{
		this.ticketId = ticketId;
	}

	public int getTableNumber() 
	{
		return tableNumber;
	}

	public void setTableNumber(int tableNumber) 
	{
		this.tableNumber = tableNumber;
	}

	public Date getOrderDate() 
	{
		return orderDate;
	}

	public void setOrderDate(Date orderDate) 
	{
		this.orderDate = orderDate;
	}

	public short getCheckedOut() 
	{
		return checkedOut;
	}

	public void setCheckedOut(short checkedOut) 
	{
		this.checkedOut = checkedOut;
	}

	public short getCallWaiterStatus() 
	{
		return callWaiterStatus;
	}

	public void setCallWaiterStatus(short callWaiterStatus) 
	{
		this.callWaiterStatus = callWaiterStatus;
	};

}
