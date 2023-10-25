package com.service;

import java.util.Date;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;

import com.Exception.LogoutException;
import com.Exception.ResourceNotFound;
import com.dao.CustomerDao;
import com.dao.LoginLogoutDao;
import com.entity.CustomerDetails;
import com.entity.LoginLogoutDetails;
import com.utility.IdGenerator;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Service
public class LoginLogoutService {

	@Autowired
	LoginLogoutDao loginLogoutDao;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	ObjectFactory<HttpSession> objectFactory;
	
	private CustomerDetails getCustomerDetail(String username, String Password) {
		CustomerDetails customerDetails=null;
		customerDetails=customerDao.getCustomerDetail(username, Password);
		
		return customerDetails;
	}
	public LoginLogoutDetails doLogin(String username, String password, HttpServletRequest request) {
		CustomerDetails customerDetails=null;
		LoginLogoutDetails savedDetails=null;
		customerDetails=this.getCustomerDetail(username, password);
		if(customerDetails==null) {
			throw new ResourceNotFound("Username or password not found");
		}else {
			HttpSession httpSession= request.getSession();
			httpSession.setAttribute("customerId", customerDetails.getCustomerId());
			LoginLogoutDetails loginLogoutDetails=new LoginLogoutDetails();
			
			loginLogoutDetails.setLoginId(IdGenerator.generateLoginId());
			loginLogoutDetails.setCustomerId(customerDetails.getCustomerId());
			loginLogoutDetails.setSessionId(httpSession.getId());
			
			loginLogoutDetails.setLoginTime(new Date());
			loginLogoutDetails.setActive(true);
			
			httpSession.setAttribute("loginLogoutDetails", loginLogoutDetails);
			
			savedDetails=loginLogoutDao.save(loginLogoutDetails);
		}
		savedDetails.setCustomerName(customerDetails.getCustomerName());
		return savedDetails;
	}
	public LoginLogoutDetails doLogut(long loginId) {
		LoginLogoutDetails savedDetail=null;
		try {
			LoginLogoutDetails loginLogoutDetails=loginLogoutDao.findById(loginId).orElseThrow(()-> new ResourceNotFound("Login id not found"));
			loginLogoutDetails.setActive(false);
			loginLogoutDetails.setLogoutTime(new Date());
			
			savedDetail=loginLogoutDao.save(loginLogoutDetails);
			if(savedDetail==null) {
				throw new LogoutException("Logout not possible");
			}
		}catch(LogoutException exception) {
//			throw new LogoutException("Logout not possible");
		}
		return savedDetail;
	}
}
