package com.PaginationHelper;

import java.util.List;

import com.entity.BrandDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetSimilarProductResponse {

	List<BrandDetails> brandList;
	private int pageNumber;
	private int pageSize;
	private long totalElement;
	private int totalPage;
	private boolean lastPage;
	private boolean firstPage;
}
