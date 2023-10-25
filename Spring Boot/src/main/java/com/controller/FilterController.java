package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.BrandFilter;
import com.entity.PriceFilterData;
import com.service.FilterService;

@RestController
@RequestMapping("/filter")
public class FilterController {
	
	@Autowired
	FilterService filterService;

	@GetMapping("/getallbrandname/{productId}")
	public ResponseEntity<List<BrandFilter>> getAllBrandName(@PathVariable long productId){
		List<BrandFilter> brandFilters=filterService.getBrandNamesById(productId);
		
		return new ResponseEntity<List<BrandFilter>>(brandFilters , HttpStatus.OK);
	}
	@GetMapping("/getallpricerange/{productId}")
	public ResponseEntity<PriceFilterData> getAllPriceRangeById(@PathVariable long productId){
		PriceFilterData priceFilterData=filterService.getAllPriceRangeById(productId);
		
		return new ResponseEntity<PriceFilterData>(priceFilterData ,HttpStatus.OK);
		
	}
}
