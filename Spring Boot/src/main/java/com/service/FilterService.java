package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Exception.ResourceNotFound;
import com.dao.BrandFilterDao;
import com.dao.PriceFilterDao;
import com.entity.BrandFilter;
import com.entity.PriceFilterData;

@Service
public class FilterService {

	@Autowired
	BrandFilterDao filterDao;
	
	@Autowired
	PriceFilterDao priceFilterDao;
	
	public List<BrandFilter> getBrandNamesById(long productId){
		List<BrandFilter> brandFilters=filterDao.getBrandNamesById(productId);
		
		return brandFilters;
	}
	public PriceFilterData getAllPriceRangeById(long productId) {
		PriceFilterData priceFilterData=priceFilterDao.findById(productId).orElseThrow(()-> new ResourceNotFound("Price list not found"));
		
		
		
		return priceFilterData;
	}
}
