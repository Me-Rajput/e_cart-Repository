package com.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Exception.EmailOrPasswordAllreadyPresentException;
import com.dao.CustomerDao;
import com.dao.OrderDao;
import com.dto.CustomerDTO;
import com.entity.CustomerDetails;
import com.entity.OrderDetails;
import com.utility.IdGenerator;

@Service
public class CustomerService {
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	ModelMapper modelMapper;

	public CustomerDTO addNewCustomer(CustomerDTO customerDTO) {
		long customerId=IdGenerator.generateCustomerId();
		String customerEmail=customerDTO.getEmail();
		String password=customerDTO.getPassword();
		CustomerDetails custDetails=customerDao.getCustomerDetail(customerEmail, password);
		if(custDetails!=null) {
			throw new EmailOrPasswordAllreadyPresentException("Email or Password allready present. Try with another credential.");
		}
		CustomerDetails details=modelMapper.map(customerDTO, CustomerDetails.class);
		details.setCustomerId(customerId);
		
		CustomerDetails addedDetails=customerDao.save(details);
		
		return modelMapper.map(addedDetails, CustomerDTO.class);
	}
	public CustomerDTO getCustDetails(long custId) {
		Optional<CustomerDetails> cust=customerDao.findById(custId);
		CustomerDetails details=cust.get();
		
		CustomerDTO customerDTO=modelMapper.map(details, CustomerDTO.class);
		return customerDTO;
	}
	public List<CustomerDTO> getAllCustomer(){
		List<CustomerDetails> customerList=customerDao.findAll();
		
		List<CustomerDTO> custDtoList=customerList.stream().map((cust)->modelMapper.map(cust, CustomerDTO.class)).collect(Collectors.toList());
		
		return custDtoList;
	}
}
