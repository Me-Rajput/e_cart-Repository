package com.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.BrandDetails;
import com.entity.DashboardDetail;

public interface BrandDetailDao extends JpaRepository<BrandDetails, Long> {

	@Query(value="select product_id, color, is_available, price, size,sub_sub_id,description,imageurl, new_arrival, star_Rating, mrp, is_Top_Product from branddetails where sub_sub_id=?1", nativeQuery=true)
	List<BrandDetails> findById(long subSubID);
	
	@Query(value="select * from branddetails where product_id=?1", nativeQuery=true)
	BrandDetails getById(long id);
	
	@Query(value="select product_id, color, is_available, price, size,sub_sub_id,description,imageurl, new_arrival, star_Rating, mrp, is_Top_Product from branddetails where sub_sub_id=(select sub_sub_id from branddetails where product_id=?1) and product_id !=?2", nativeQuery=true)
	Page<BrandDetails> getSimilarProducts(long id1, long id2, Pageable pg);
	
	
}
