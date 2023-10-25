package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SpecificProductDetail {

	@Id
	private long product_id;
	private long sub_id;
	private long subsub_id;
	private String product_name;
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
	
	@Transient
	private long discount;
}
