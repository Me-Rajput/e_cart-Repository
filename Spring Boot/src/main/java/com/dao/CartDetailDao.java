package com.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.entity.CartDetail;

@Transactional
public interface CartDetailDao extends JpaRepository<CartDetail, Long> {

	@Query(value="select cart_id, color, customer_id, description, imageurl, is_available, mrp, new_arrival, price, product_id, size, star_rating, sub_sub_id, added_time, quantity from cartdetail where customer_id=?1 order by added_time desc", nativeQuery=true)
	List<CartDetail> findAllProduct(long customerId);
	
	@Query(value="select cart_id, color, customer_id, description, imageurl, is_available, mrp, new_arrival, price, product_id, size, star_rating, sub_sub_id, added_time, quantity from cartdetail where product_id=?1 and customer_id=?2", nativeQuery=true)
	CartDetail isProductPresent(long productId, long customerId);
	
	@Modifying
	@Query(value="update cartdetail set quantity=?1 where customer_id=?2 and product_id=?3", nativeQuery=true)
	int updateQuantity(int quantity, long customerId, long productId);
	
	@Modifying
	@Query(value="delete from cartdetail where customer_id=?1 and product_id=?2", nativeQuery=true)
	int removeFromCart(long customerId, long productId);
	
	@Modifying
	@Query(value="delete from cartdetail where customer_id=?1",nativeQuery=true)
	int emptyCartAfterOrder(long customerId);
}
