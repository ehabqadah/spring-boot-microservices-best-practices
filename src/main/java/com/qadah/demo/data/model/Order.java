package com.qadah.demo.data.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ehab Qadah
 */
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

	@Column(name = "customer_id")
	private String customerId;

	@Column(name = "date_purchased")
	private Instant datePurchased;

	@Column(name = "order_total")
	private BigDecimal total;

	@Column(name = "order_status")
	@Enumerated(value = EnumType.STRING)
	private OrderStatus status;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private Set<Product> orderProducts = new HashSet<>();

	@Column(name = "PAYMENT_TYPE")
	@Enumerated(value = EnumType.STRING)
	private PaymentType paymentType;

	@Column(name = "shipping_mode")
	private String shippingMode;

	public Order() {
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Instant getDatePurchased() {
		return datePurchased;
	}

	public void setDatePurchased(Instant datePurchased) {
		this.datePurchased = datePurchased;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public OrderStatus getStatus() {
		return status;
	}

	public void setStatus(OrderStatus status) {
		this.status = status;
	}

	public Set<Product> getOrderProducts() {
		return orderProducts;
	}

	public void setOrderProducts(Set<Product> orderProducts) {
		this.orderProducts = orderProducts;
	}
}
