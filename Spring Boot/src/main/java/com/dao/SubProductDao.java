package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.SubProductDetails;

public interface SubProductDao extends JpaRepository<SubProductDetails, Long> {
    
	@Query(value = "select sub_productid, status, product_name, product_id from subproductdetails where product_id=?1",nativeQuery = true)
	List<SubProductDetails> findById(long productId);
}
