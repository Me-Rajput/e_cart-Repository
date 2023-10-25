package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.entity.CustomerDetails;
import com.entity.OrderDetails;

import jakarta.transaction.Transactional;

@Transactional
public interface OrderDao extends JpaRepository<OrderDetails, Long> {

	@Query(value="select order_id, delivery_address, delivery_date, description, order_date, productid, quantity, customer_id, total_price, unit_price, imageurl, order_status, order_status_value  from orderdetails where customer_id=?1 order by order_date desc", nativeQuery = true)
	List<OrderDetails> findById(long custID);
	
	@Modifying
	@Query(value="update orderdetails set order_status='Cancelled', order_status_value=-1 where order_id=?1 and customer_id=?2",nativeQuery=true)
	int cancelOrder(long orderId, long customerID);
	
	@Query(value="call get_filtered_orderlist(:customerId, :filterQuery)", nativeQuery=true)
	List<OrderDetails> getFilteredOrderList(@Param("customerId") long customerId, @Param("filterQuery") String filterQuery);
	
}
