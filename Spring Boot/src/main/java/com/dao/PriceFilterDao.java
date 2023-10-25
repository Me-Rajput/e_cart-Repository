package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.PriceFilterData;

public interface PriceFilterDao extends JpaRepository<PriceFilterData, Long> {

}
