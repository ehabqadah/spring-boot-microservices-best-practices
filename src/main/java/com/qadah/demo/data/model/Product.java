package com.qadah.demo.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * @author Ehab Qadah
 */
@Entity
@Table(name = "products")
public class Product extends BaseEntity {


	@Column(name = "code")
	private String code;

	@Column(name = "product_name", length = 100, nullable = false)
	private String productName;

	@Column(name = "product_quantity")
	private int productQuantity;

	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@JsonIgnore
	@ManyToOne(targetEntity = Order.class)
	@JoinColumn(name = "order_id", nullable = false)
	private Order order;

	public Product() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public boolean equals(Object o) {
		if (this==o) {
			return true;
		}
		if (o==null || getClass()!=o.getClass()) {
			return false;
		}
		Product product = (Product) o;
		return Objects.equals(id, product.id) &&
				productQuantity==product.productQuantity &&
				Objects.equals(code, product.code) &&
				Objects.equals(productName, product.productName) &&
				Objects.equals(price, product.price) &&
				Objects.equals(order, product.order);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, code, productName, productQuantity, price, order);
	}
}
