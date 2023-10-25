package com.dto;

import java.util.ArrayList;
import java.util.List;

import com.entity.SubProductDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubProductDTO {

    private long subProductID;
	private boolean status;
	private String productName;
	private long productId;
	//private List<SubSubProductDTO> subProductDTOs= new ArrayList<>();
}
