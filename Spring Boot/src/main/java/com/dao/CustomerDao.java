package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.entity.CustomerDetails;

@Repository
public interface CustomerDao extends JpaRepository<CustomerDetails, Long> {

	@Query(value = "select customer_id, customer_address, customer_name, customer_ph, email, password from customerdetails where email=?1 and password=?2 ", nativeQuery=true)
	CustomerDetails getCustomerDetail(String username, String password);
	
	@Query(value="select customer_id, customer_address, customer_name, customer_ph, email, password from customerdetails where customer_id=?1", nativeQuery = true)
	CustomerDetails getCustomerByID(long customerID);
}
