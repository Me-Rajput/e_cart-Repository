package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.SpecificProductDetail;

public interface SpecificProductDao extends JpaRepository<SpecificProductDetail, Long> {

	@Query(value="call get_sorted_and_filtered_product(:productId, :sortQuery, :filterQuery, :starFilterQuery, :priceFilterValue)",nativeQuery=true)
	List<SpecificProductDetail> getSortedAndFilteredSpecificProductById(
			@Param("productId") long productId,
			@Param("sortQuery") String sortQuery, 
			@Param("filterQuery") String filterQuery,
			@Param("starFilterQuery") String starFilterQuery,
			@Param("priceFilterValue") String priceFilterValue);
	
	@Query(value="select sub_productid from subproductdetails where product_name=?1",nativeQuery = true)
	int searchProductIdByProductName(String productName);
	
}
