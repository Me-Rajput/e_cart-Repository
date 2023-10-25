package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.Exception.CustomerIDNotFoundException;
import com.Exception.ItemChange;
import com.entity.CartDetail;
import com.entity.CustomerDetails;
import com.service.CartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/cart")
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@Autowired
	RestTemplate restTemplate;

	@PostMapping("/additem/{customerId}")
	public ResponseEntity<CartDetail> addItem(@RequestBody CartDetail cartDetails, @PathVariable String customerId){
		CartDetail cartDetail=cartService.addItemToCart(cartDetails,customerId);
		
		return new ResponseEntity<CartDetail>(cartDetail, HttpStatus.OK);
	}
	@GetMapping("/getallproduct/{customerId}")
	public ResponseEntity<List<CartDetail>> getAllProduct(@PathVariable String customerId){
		if(customerId==null || customerId.contains("undefined")) {
			throw new CustomerIDNotFoundException("Undefined or invalid customer ID!!!");
		}
		long custId=Long.parseLong(customerId);
		List<CartDetail> cartDetails=cartService.getAllProduct(custId);
		
		return new ResponseEntity<List<CartDetail>>(cartDetails, HttpStatus.OK);
	}
	@GetMapping("/isproductadded/{productId}/{customerId}")
	public ResponseEntity<Boolean> isProductAdded(@PathVariable String productId, @PathVariable String customerId){
		//String custId=request.getAttribute("customerId").toString();
		long cId=Long.parseLong(customerId);
		long prodId=Long.parseLong(productId);
		boolean flag=cartService.isProductAdded(prodId,cId);
		
		return new ResponseEntity<Boolean>(flag, HttpStatus.OK);
	}
	@PostMapping("/increasequantity/{quantity}/{customerId}/{productId}")
	public ResponseEntity<List<CartDetail>> increaseQuantity(@PathVariable String quantity, @PathVariable String customerId, @PathVariable String productId){
		int qty=Integer.parseInt(quantity);
		long custId=Long.parseLong(customerId);
		long prodId=Long.parseLong(productId);
		
		
		if(qty==5) {
			throw new ItemChange("Number of item cannot increase more than 5");
		}
		
		cartService.increaseQuantity(qty, custId, prodId);
		
		List cartDetails=restTemplate.getForObject("http://localhost:8080/cart/getallproduct/"+custId, List.class);
		
		return new ResponseEntity<List<CartDetail>>(cartDetails,HttpStatus.OK);
	}
	@PostMapping("/decreasequantity/{quantity}/{customerId}/{productId}")
	public ResponseEntity<List<CartDetail>> decreaseQuantity(@PathVariable String quantity, @PathVariable String customerId, @PathVariable String productId){
		int qty=Integer.parseInt(quantity);
		long custId=Long.parseLong(customerId);
		long prodId=Long.parseLong(productId);
		
		if(qty==1) {
			throw new ItemChange("Number of item cannot decrease less than 1");
		}
		cartService.decreaseQuantity(qty, custId, prodId);
		
		List cartDetails=restTemplate.getForObject("http://localhost:8080/cart/getallproduct/"+custId, List.class);
		
		return new ResponseEntity<List<CartDetail>>(cartDetails,HttpStatus.OK);
	}
	@PostMapping("/removeitem/{customerId}/{productId}")
	public ResponseEntity<List<CartDetail>> removeItem(@PathVariable String customerId, @PathVariable String productId){
		long custId=Long.parseLong(customerId);
		long prodId=Long.parseLong(productId);
		List cartDetails=null;
		int val=cartService.removeFromCart(custId, prodId);
		
		if(val!=0) {
			cartDetails=restTemplate.getForObject("http://localhost:8080/cart/getallproduct/"+custId, List.class);
		}
		return new ResponseEntity<List<CartDetail>>(cartDetails, HttpStatus.OK);
	}
	
}
