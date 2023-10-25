package com.entity;

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
@Table(name="branddetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrandDetails {

	@Id
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
	private boolean isTopProduct;
	@Transient
	private double discount;
}
