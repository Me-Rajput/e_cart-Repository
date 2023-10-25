package com.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="cartdetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDetail {

	@Id
	private long cartId;
	private long customerId;
	private long productId;
	private long subSubId;
	private String color;
	private String size;
	private String description;
	private double price;
	private boolean isAvailable;
	private String imageURL;
	private boolean newArrival;
	private double starRating;
	private double mrp;
	private int quantity;
	private Date addedTime;
	@Transient
	private long discount;

}
