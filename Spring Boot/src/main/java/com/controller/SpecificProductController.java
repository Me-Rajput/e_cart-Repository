package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Exception.InvalidQueryStringException;
import com.Exception.ProductSearchException;
import com.entity.SpecificProductDetail;
import com.service.SpecificProductService;

@RestController
@RequestMapping("/specificproduct")
public class SpecificProductController {

	@Autowired
	SpecificProductService specificProductService;
	
	@GetMapping("/getproductbyid/{productID}")
	public ResponseEntity<List<SpecificProductDetail>> getSpecificProductById(@PathVariable long productID,
			@RequestParam(defaultValue ="", required = false, value = "queryString") String queryString,
			@RequestParam(defaultValue ="", required = false, value = "filterValues") long[] filterValues,
			@RequestParam(defaultValue ="-1", required = false, value = "starFilterValue") int starFilterValue,
			@RequestParam(defaultValue = "", required = false, value="priceFilterValue") String priceFilterValue){
			
		
		String str="";
		if(queryString.contains("Price--High to Low")){
			str= "order by price desc";
		} else if(queryString.contains("Price--Low to High")) {
			str= "order by price asc";
		} else if(queryString.contains("Popularity")) {
			str= "order by star_rating desc";
		} else if(queryString.contains("")) {
			str="";
		}else {
			throw new InvalidQueryStringException("Invalis conditional query string exception");
		}
		List<SpecificProductDetail> productDetailList=specificProductService.getSpecificProductById(productID ,str, filterValues, 
				starFilterValue, priceFilterValue);
		
		return new ResponseEntity<List<SpecificProductDetail>>(productDetailList, HttpStatus.OK);
	}
	@GetMapping("/searchproductid")
	public ResponseEntity<Integer> searchProductIdByProductName(
			@RequestParam(defaultValue="",required = false,name = "productName")String productName){
		
		if(productName.isEmpty()) {
			throw new ProductSearchException("Please enter a search keyword...");
		}
		int productId=specificProductService.searchProductIdByProductName(productName);
		return new ResponseEntity<Integer>(productId, HttpStatus.OK);
	}
}
