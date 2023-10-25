package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productdetail")
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetails{

	@Id
	@Column(name = "main_category_id", nullable = false, unique = true)
	private long mainCategoryId;
	
	@Column(name="product_name",nullable = false)
	private String productName;
	
	@Transient
	List<SubProductDetails> subProductDetails= new ArrayList<>();
}