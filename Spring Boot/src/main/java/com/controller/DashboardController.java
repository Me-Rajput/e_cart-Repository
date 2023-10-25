package com.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;

import com.Exception.TopProductNumberOfQuantityException;
import com.entity.BrandDetails;
import com.entity.DashboardDetail;
import com.entity.TopProductDetail;
import com.service.DashboardService;
import com.service.ProductService;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {
	
	@Autowired
	DashboardService dashboardService;
	
	@Autowired
	ProductService productService;
	
	//@Autowired
	//RestTemplate restTemplate;

	@GetMapping("/getallproduct")
	public ResponseEntity<Map<Long, List<DashboardDetail>>> getAllProduct(){
		Map<Long, List<DashboardDetail>> dashboardDetails=dashboardService.getAllProduct();
		
		return new ResponseEntity<Map<Long, List<DashboardDetail>>>(dashboardDetails, HttpStatus.OK);
	}
	@GetMapping("/getbranddetailbyid/{id}")
	public ResponseEntity<BrandDetails> getBrandDetailById(@PathVariable long id){
		BrandDetails details=productService.getBrandDetailById(id);
		//Map<Long, List<DashboardDetail>> abc=restTemplate.getForObject("localhost:8080/dashboard/getallproduct", Map.class);
		return new ResponseEntity<BrandDetails>(details, HttpStatus.OK);
	}
	@GetMapping("/gettopproducts/{mainCategoryId}")
	public ResponseEntity<List<TopProductDetail>> getTopGarments(@PathVariable long mainCategoryId,
			@RequestParam(required = false, name = "numberOfProduct", defaultValue = "-1") int numberOfProduct){
		
		if(numberOfProduct<-1 || numberOfProduct==0) {
			throw new TopProductNumberOfQuantityException("Invalid number of quantity input value");
		}
		List<TopProductDetail> productDetails=dashboardService.getTopProductList(mainCategoryId,numberOfProduct);
		
		return new ResponseEntity<List<TopProductDetail>>(productDetails, HttpStatus.OK);
	}
	@GetMapping("/getalltopproductcategorywise/{mainCatId}")
	public ResponseEntity<List<TopProductDetail>> getAllTopProductDetailCategoryWise(@PathVariable long mainCatId){
		List<TopProductDetail> topProductDetails=dashboardService.getAllTopProductCategoryWise(mainCatId);
		
		return new ResponseEntity<List<TopProductDetail>>(topProductDetails, HttpStatus.OK);
	}
	
}

