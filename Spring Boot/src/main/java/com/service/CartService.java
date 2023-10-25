package com.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Exception.CartDataNotFoundException;
import com.Exception.ItemChange;
import com.Exception.ItemRemovalException;
import com.Exception.QuantityUpdationWrongCredentialException;
import com.dao.CartDetailDao;
import com.entity.CartDetail;
import com.utility.IdGenerator;

@Service
public class CartService {

	@Autowired
	CartDetailDao cartDetailDao;
	
	public CartDetail addItemToCart(CartDetail cartDetail, String customerId) {
		long productId=cartDetail.getProductId();
		long cartId=IdGenerator.generateCartId();
		long custId=Long.parseLong(customerId);
		CartDetail newDetail=null;
		CartDetail detail= cartDetailDao.isProductPresent(productId, custId);
		if(detail!=null) {
			int quantity=detail.getQuantity();
			int newQuantity=quantity+1;
			if(newQuantity>5) {
				throw new ItemChange("Maximum number of this item is allready added to cart");
			}
			detail.setQuantity(newQuantity);
			detail.setAddedTime(new Date());
			newDetail= cartDetailDao.save(detail);
		}else {
			cartDetail.setCustomerId(custId);
			cartDetail.setCartId(cartId);
			cartDetail.setAddedTime(new Date());
			newDetail = cartDetailDao.save(cartDetail);
		}
		return newDetail;
	}
	public List<CartDetail> getAllProduct(long customerId){
		List<CartDetail> cartDetails=cartDetailDao.findAllProduct(customerId);
		
	cartDetails.forEach(x->x.setDiscount(Math.round(((x.getMrp()-x.getPrice())/x.getMrp())*100)));
		
		return cartDetails;
	}
	public boolean isProductAdded(long productId, long customerId) {
		boolean flag=false;
		CartDetail cartDetail=cartDetailDao.isProductPresent(productId,customerId);
		if(cartDetail!=null) {
			flag=true;
		}
		return flag;
	}
	public int increaseQuantity(int quantity, long customerId, long productId) {
		int newQuantity=quantity+1;
		
		int value=cartDetailDao.updateQuantity(newQuantity, customerId, productId);
		
		if(value==0) {
			throw new QuantityUpdationWrongCredentialException("Number of quantity cannot be updated due to wrong input value");
		}
		return value;
	}
	public int decreaseQuantity(int quantity, long customerId, long productId) {
		int newQuantity=quantity-1;
		int value=cartDetailDao.updateQuantity(newQuantity, customerId, productId);
		
		if(value==0) {
			throw new QuantityUpdationWrongCredentialException("Number of quantity cannot be updated due to wrong input value");
		}
		return value;
	}
	public int removeFromCart(long customerId, long productId) {
		int value=cartDetailDao.removeFromCart(customerId, productId);
		
		if(value==0) {
			throw new ItemRemovalException("Item cannot be removed due to some problem");
		}
		return value;
	}
}
