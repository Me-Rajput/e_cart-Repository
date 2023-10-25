package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.BrandFilter;

public interface BrandFilterDao extends JpaRepository<BrandFilter, Long> {

	@Query(value="select * from subsubproductdetails where sub_id=?1", nativeQuery=true)
	List<BrandFilter> getBrandNamesById(long productID);
	

}
