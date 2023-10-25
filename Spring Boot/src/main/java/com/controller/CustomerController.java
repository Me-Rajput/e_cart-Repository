package com.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.CustomerDTO;
import com.entity.CustomerDetails;
import com.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	CustomerService customerService;
	
	@PostMapping("/addnewcustomer")
	public ResponseEntity<CustomerDTO> addNewCustomer(@RequestBody CustomerDTO customerDTO){
		CustomerDTO details=customerService.addNewCustomer(customerDTO);
		
		return new ResponseEntity<CustomerDTO>(details, HttpStatus.CREATED);
	}
	@GetMapping("/getcustomerdata/{custId}")
	public ResponseEntity<CustomerDTO> getCustomerData(@PathVariable long custId){
		CustomerDTO details=customerService.getCustDetails(custId);
		
			return new ResponseEntity<CustomerDTO>(details,  HttpStatus.ACCEPTED);
	}
	@GetMapping("/getallcustomer")
	public ResponseEntity<List<CustomerDTO>> getAllCustomer(){
		List<CustomerDTO> custList=customerService.getAllCustomer();
		
		return new ResponseEntity<List<CustomerDTO>>(custList, HttpStatus.CREATED);
	}
}
