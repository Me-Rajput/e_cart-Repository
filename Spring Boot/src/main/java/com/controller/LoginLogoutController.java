package com.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dto.LoginLogoutDTO;
import com.entity.CustomerDetails;
import com.entity.LoginLogoutDetails;
import com.service.LoginLogoutService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/loginlogout")
public class LoginLogoutController {

	@Autowired
	LoginLogoutService loginLogoutService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@PostMapping("/login")
	public ResponseEntity<LoginLogoutDTO> doLogin(@RequestBody CustomerDetails customerDetails, HttpServletRequest request){
		String email=customerDetails.getEmail();
		String password=customerDetails.getPassword();
		LoginLogoutDetails loginLogoutDetails=loginLogoutService.doLogin(email, password,request);
		
		LoginLogoutDTO logoutDTO=new LoginLogoutDTO();
		logoutDTO.setLoginId(String.valueOf(loginLogoutDetails.getLoginId()));
		logoutDTO.setCustomerId(String.valueOf(loginLogoutDetails.getCustomerId()));
		logoutDTO.setSessionId(loginLogoutDetails.getSessionId());
		logoutDTO.setCustomerName(loginLogoutDetails.getCustomerName());
		logoutDTO.setLoginTime(loginLogoutDetails.getLoginTime());
		logoutDTO.setLogoutTime(loginLogoutDetails.getLogoutTime());
		logoutDTO.setActive(true);
		
		return new ResponseEntity<LoginLogoutDTO>(logoutDTO, HttpStatus.OK);
	}
	@PostMapping("/logout/{strId}")
	public ResponseEntity<LoginLogoutDetails> doLogout(@PathVariable String strId){
		long loginId=Long.parseLong(strId);
		LoginLogoutDetails details=loginLogoutService.doLogut(loginId);
		return new ResponseEntity<LoginLogoutDetails>(details,HttpStatus.OK);
	}
	
}
