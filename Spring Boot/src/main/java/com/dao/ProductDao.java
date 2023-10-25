package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.ProductDetails;

public interface ProductDao extends JpaRepository<ProductDetails, Long> {

}
