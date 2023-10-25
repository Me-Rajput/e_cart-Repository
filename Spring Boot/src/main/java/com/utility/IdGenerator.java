package com.utility;

import java.util.UUID;

public class IdGenerator {
	private static long customerId;
	private static long loginId;
	private static long orderId;
	private static long cartId;
	
	public static long generateCustomerId() {
		customerId= UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE;
		return customerId;
	}
	public static long generateLoginId() {
		loginId= UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE;
		return loginId;
	}
	public static long generateOrderId() {
		orderId= UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE;
		return orderId;
	}
	public static long generateCartId() {
		cartId= UUID.randomUUID().getMostSignificantBits()&Long.MAX_VALUE;
		return cartId;
	}
}
