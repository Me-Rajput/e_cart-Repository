package com.dto;

import java.util.ArrayList;
import java.util.List;

import com.entity.SubProductDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

	private long productId;
	private String productName;
	private List<SubProductDetails> subProductDetails= new ArrayList<>();
}
