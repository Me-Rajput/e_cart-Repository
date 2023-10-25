package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(TopProductDetail_IDClass.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopProductDetail {

	@Id
	private long product_id;
	private long main_category_id;
	private long sub_productid;
	private long subsub_id;
	private String product_name;
	private boolean status;
	private String brande_name;
	private String color;
	private boolean is_available;
	private double price;
	private String size;
	private String description;
	private String imageurl;
	private boolean new_arrival;
	private double star_rating;
	private double mrp;
	private boolean is_top_product;
}
