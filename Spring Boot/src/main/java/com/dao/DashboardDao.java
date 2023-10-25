package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.entity.DashboardDetail;

public interface DashboardDao extends JpaRepository<DashboardDetail, Long> {

	@Query(value="select sub.sub_productid, subsub.subsub_id,brnd.product_id,sub.product_name, sub.status,subsub.brande_name,brnd.color, brnd.description from subproductdetails sub, subsubproductdetails subsub, branddetails brnd where sub.sub_productid=subsub.sub_id and subsub.subsub_id=brnd.sub_sub_id", nativeQuery=true)
	List<DashboardDetail> findAllProds();
}
