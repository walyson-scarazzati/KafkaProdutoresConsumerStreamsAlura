package br.com.alura.ecommerce;


public class Order {
	private final String userId, orderId;
	private final BigDecimal value;

	private Order(String userId, String orderId, BigDecimal value){
		this.userId = userId;
		this.orderId = orderId;
		this.value = value;
	}
	
}
