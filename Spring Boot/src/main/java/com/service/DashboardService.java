package com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Exception.TopProductNotFoundException;
import com.dao.DashboardDao;
import com.dao.TopProductDetailDao;
import com.entity.DashboardDetail;
import com.entity.TopProductDetail;

@Service
public class DashboardService {

	@Autowired
	DashboardDao dashboardDao;
	
	@Autowired
	TopProductDetailDao topProductDetailDao;
	
	public Map<Long, List<DashboardDetail>> getAllProduct(){
	    Map<Long, List<DashboardDetail>> dashboardKeyValPair=new HashMap<>();	
		List<DashboardDetail> dashboardDetails=dashboardDao.findAllProds();
		
	
		/*for(int i=0; i<dashboardDetails.size();i++) {
			
			long id=dashboardDetails.get(i).getSub_productid();
			if(!dashboardKeyValPair.containsKey(id)) {
				List<DashboardDetail> list= new ArrayList<>();
				list.add(dashboardDetails.get(i));
				
				dashboardKeyValPair.put(id, list);
			}else {
				dashboardKeyValPair.get(id).add(dashboardDetails.get(i));
			}
		}*/
		
		
		dashboardDetails.forEach(x->{
		if(!dashboardKeyValPair.containsKey(x.getSub_productid())) {
			List<DashboardDetail> lst= new ArrayList<>();
			lst.add(x);
			
			dashboardKeyValPair.put(x.getSub_productid(), lst);
		}else {
			dashboardKeyValPair.get(x.getSub_productid()).add(x);
		}
		});
		
		
		//System.out.println(dashboardKeyValPair);
		return dashboardKeyValPair;
	}
	public List<TopProductDetail> getTopProductList(long mainCategoryId, int numberOfProduct){
		String str="";
		if(numberOfProduct!=-1) {
			str="limit "+numberOfProduct;
		}
		List<TopProductDetail> productDetails=topProductDetailDao.getTopProductList(mainCategoryId,str);
		
		if(productDetails.size()==0) {
			throw new TopProductNotFoundException("Top product not found. May be due to invalid ID");
		}
		return productDetails;
	}
	public List<TopProductDetail> getAllTopProductCategoryWise(long mainCatId){
		List<TopProductDetail> topProductDetails=topProductDetailDao.getAllTopProductCategorywise(mainCatId);
		
		if(topProductDetails.size()==0) {
			throw new TopProductNotFoundException("Top product not found. May be due to invalid ID");
		}
		return topProductDetails;
	}
}
