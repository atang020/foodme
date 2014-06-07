package org.segfault.foodme.test;

import java.math.BigDecimal;
import java.util.ArrayList;

import junit.framework.TestCase;

import org.segfault.foodme.MyOrderActivity;
import org.segfault.foodme.TicketItem;

public class MyOrderTest extends TestCase {
	private final short TicketItem = 0;
	ArrayList<TicketItem> items = new ArrayList<TicketItem>();
	MyOrderActivity orders = new MyOrderActivity();
	protected void setUp() throws Exception {
		super.setUp();
		short a = 3;
		short b = 4;
		short noKitchenStatus = 0;
		TicketItem item = new TicketItem(10, 10, 10, a,"no pickles", noKitchenStatus, "CheeseBurger", 6.50);
		TicketItem item2 = new TicketItem(10, 10, 10, b,"extra sauce", noKitchenStatus, "Bacon CheeseBurger", 7.50);
		items.add(item);
		items.add(item2);
	}

	private void testSubTotal() {
		String subTotal = orders.subTotal();
		assertEquals("Subtotal: 49.50", subTotal);
	}
	
	private void testTotalString() {
		BigDecimal a = new BigDecimal(100);
		String total = orders.totalString(a);
		assertEquals("| Total: 100.00", total);
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
}
