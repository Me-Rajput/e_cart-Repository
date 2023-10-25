package com.dto;

import java.util.Date;

import com.entity.CustomerDetails;

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
public class OrderDTO {

	private String orderId;
    private String productId;
	private String description;
	private String deliveryAddress;
	private String imageurl;
	private int quantity;
	private double unitPrice;
	private Date orderDate;
	private Date deliveryDate;
	private String orderStatus;
	private int orderStatusValue;
}
