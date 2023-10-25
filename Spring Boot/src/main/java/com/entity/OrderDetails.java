package com.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orderdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetails {

	@Id
	@Column(name="order_id", unique = true, nullable = false)
	private long orderId;
	private String productID;
	private String description;
	private String deliveryAddress;
	private int quantity;
	private String imageurl;
	private double unitPrice;
	private double totalPrice;
	@Column(name="order_date")
	private Date orderDate;
	@Column(name="delivery_date")
	private Date deliveryDate;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="customer_id")
	private CustomerDetails customerDetails;
	@Column(insertable = false, updatable = false)
	private long customer_id;
	private String orderStatus;
	private int orderStatusValue;
}
