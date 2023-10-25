package com.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BulkOrderDTO {

	private long customerId;
	private long productId;
	private long subSubId;
	private long cartId;
	private double mrp;
	private double starRating;
	private double price;
	private int quantity;	
	private boolean available;
	private boolean newArrival;
	private String color;
	private String description;
	private String imageURL;
	private String size;
	private String deliveryAddress;
	private Date addedTime;
}
