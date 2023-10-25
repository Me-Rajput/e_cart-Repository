package com.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TopProductDetail_IDClass implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long main_category_id;
	private long sub_productid;
	private long subsub_id;
	private long product_id;
}
