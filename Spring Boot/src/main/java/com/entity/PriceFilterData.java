package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="pricefilterdata")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PriceFilterData {
	
	@Id
	private long productId;
	private int priceRange1;
	private int priceRange2;
	private int priceRange3;
	private int priceRange4;
	private int priceRange5;
}
