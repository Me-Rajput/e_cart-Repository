package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.TopProductDetail;

public interface TopProductDetailDao extends JpaRepository<TopProductDetail, Long> {

	@Query(value="call get_top_product_from_each_group(:mainCategoryId, :optionalQuery);", nativeQuery=true)
	List<TopProductDetail> getTopProductList(@Param("mainCategoryId") long mainCategoryId, @Param("optionalQuery") String optionalQuery);
	
	@Query(value="call get_all_top_product_categorywise(:mainCategoryId)", nativeQuery=true)
	List<TopProductDetail> getAllTopProductCategorywise(@Param("mainCategoryId") long mainCategoryId);
	
}
