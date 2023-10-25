package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.SubSubProductDetails;

public interface SubSubProductDao extends JpaRepository<SubSubProductDetails, Long> {

	@Query(value = "select subsub_id, brande_name, sub_id from subsubproductdetails where sub_id=?1", nativeQuery = true)
	List<SubSubProductDetails> findById(long subID);
}
