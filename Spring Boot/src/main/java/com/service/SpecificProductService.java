package com.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Exception.ProductNotFoundException;
import com.Exception.ProductSearchException;
import com.Exception.StarFilterDataException;
import com.dao.SpecificProductDao;
import com.entity.SpecificProductDetail;

@Service
public class SpecificProductService {

	@Autowired
	SpecificProductDao specificProductDao;
	
	public List<SpecificProductDetail> getSpecificProductById(long productID, String sortQueryString, long[] filterValues, 
			int starFilterData, String priceFilterValue){
		String priceFilterQuery="";
		if(!priceFilterValue.isEmpty()) {
			priceFilterQuery="and brnd.price"+priceFilterValue;
		}
		
		String starFilterQuery="";
		if(starFilterData>=1 && starFilterData<=5) {
			starFilterQuery="and brnd.star_rating>="+String.valueOf(starFilterData);
		}else if(starFilterData==-1) {
			starFilterQuery="";
		}else {
			throw new StarFilterDataException("Invalid star filter value");
		}
		//String filterKeyWord=" and brnd.star_rating>4";
		String filterQuery="";
		StringBuilder sb= new StringBuilder();
		for(int i=0; i<filterValues.length;i++) {
			sb.append(filterValues[i]);
			if(i!=filterValues.length-1) {
				sb.append(',');
			}
		}
		
		if(filterValues.length!=0) {
			filterQuery=" and subsub.brande_name in (select brande_name from subsubproductdetails where subsub_id in("+sb+"))";
			System.out.println(filterQuery);
		}
		
		
		List<SpecificProductDetail> productDetails=specificProductDao.getSortedAndFilteredSpecificProductById(productID,sortQueryString,
				filterQuery,starFilterQuery,priceFilterQuery);
		
		productDetails.forEach(productData->{
			double discount=((productData.getMrp()-productData.getPrice())/productData.getMrp())*100;
			productData.setDiscount(Math.round(discount));
		});
		
		if(productDetails.size()==0) {
			throw new ProductNotFoundException("Product not found");
		}
		return productDetails;
	}
	public int searchProductIdByProductName(String productName) {
		int productId=-1;
	    productId=specificProductDao.searchProductIdByProductName(productName);
	    
	    if(productId==-1) {
	    	throw new ProductSearchException("No product found with this name. Try with another product name");
	    }
		
		return productId;
	}
}
