package com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.PaginationHelper.GetSimilarProductResponse;
import com.dao.BrandDetailDao;
import com.dao.ProductDao;
import com.dao.SubProductDao;
import com.dao.SubSubProductDao;
import com.dto.ProductDTO;
import com.dto.SubProductDTO;
import com.dto.SubSubProductDTO;
import com.entity.BrandDetails;
import com.entity.ProductDetails;
import com.entity.SubProductDetails;
import com.entity.SubSubProductDetails;

@Service
public class ProductService {

	@Autowired
	ProductDao productDao;
	
	@Autowired
	SubProductDao subProductDao;
	
	@Autowired
	SubSubProductDao subSubProductDao;
	
	@Autowired
	BrandDetailDao brandDetailDao;
	
	@Autowired
	ModelMapper modelMapper;
	
	public ProductDTO addProduct(ProductDTO productDTO) {
		ProductDetails productDetails=modelMapper.map(productDTO, ProductDetails.class);
		ProductDetails addedProduct=productDao.save(productDetails);
		
		return modelMapper.map(addedProduct, ProductDTO.class);
	}
	public SubProductDTO addSubProduct(SubProductDTO productDTO) {
		SubProductDetails subProductDetails=modelMapper.map(productDTO, SubProductDetails.class);
		//subProductDetails.setProductId(productId);
		
		SubProductDetails productDetails=subProductDao.save(subProductDetails);
		
		return modelMapper.map(productDetails, SubProductDTO.class);
	}
	public ProductDTO getProductById(long productId){
		Optional<ProductDetails> productDetails=productDao.findById(productId);
		ProductDetails details=productDetails.get();
		
		List<SubProductDetails> subProductDetails=subProductDao.findById(productId);
		subProductDetails.forEach(sp-> sp.setSubSubProductDetails(getSubSubProduct(sp.getSubProductID())));
		details.setSubProductDetails(subProductDetails);

		return modelMapper.map(details, ProductDTO.class);
	}
	public List<SubSubProductDetails> getSubSubProduct(long subProdId){
		List<SubSubProductDetails> subSubProductDetails=subSubProductDao.findById(subProdId);
		subSubProductDetails.forEach(prod->prod.setBrandDetails(getBrandDetails(prod.getSubsubId())));
		//List<SubSubProductDTO> subSubProductDTOs=subSubProductDetails.stream().map((product)->modelMapper.map(product, SubSubProductDTO.class)).collect(Collectors.toList());
		return subSubProductDetails;
	}
	public List<ProductDTO> getAllProduct(){
		List<ProductDetails> productDetails=productDao.findAll();
		
		productDetails.forEach(prod->prod.setSubProductDetails(getSubProduct(prod.getMainCategoryId())));
		
		List<ProductDTO> productDTOList=productDetails.stream().map((product)->modelMapper.map(product, ProductDTO.class)).collect(Collectors.toList());
		return productDTOList;
	}
	public List<SubProductDetails> getSubProduct(long prodId){
		List<SubProductDetails> subProductDetails=subProductDao.findById(prodId);
		subProductDetails.forEach((product)->product.setSubSubProductDetails(getSubSubProduct(product.getSubProductID())));
		return subProductDetails;
	}
	public List<BrandDetails> getBrandDetails(long subSubId){
		List<BrandDetails> brandDetails=brandDetailDao.findById(subSubId);
		//List<BrandDetails> brandDetails=new ArrayList<>();
		
		return brandDetails;
	}
	public List<BrandDetails> getAllBrandDetails(){
		List<BrandDetails> brandDetails=brandDetailDao.findAll();
		brandDetails.forEach(x->x.setDiscount(Math.round(((x.getMrp()-x.getPrice())/x.getMrp())*100)));
		return brandDetails;
	}
	public BrandDetails getBrandDetailById(long id) {
		BrandDetails details=brandDetailDao.getById(id);
		details.setDiscount(Math.round(((details.getMrp()-details.getPrice())/details.getMrp())*100));
		return details;
	}
	public GetSimilarProductResponse getSimilarProduct(long id, int pageNumber,int pageSize){
		Pageable p=PageRequest.of(pageNumber, pageSize);
		Page<BrandDetails> brandDetails=brandDetailDao.getSimilarProducts(id,id,p);
		List<BrandDetails> brandDetailList=  brandDetails.getContent();
		
		GetSimilarProductResponse getSimilarProduct= new GetSimilarProductResponse();
		getSimilarProduct.setBrandList(brandDetailList);
		getSimilarProduct.setPageNumber(brandDetails.getNumber());
		getSimilarProduct.setPageSize(brandDetails.getSize());
		getSimilarProduct.setTotalElement(brandDetails.getTotalElements());
		getSimilarProduct.setTotalPage(brandDetails.getTotalPages());
		getSimilarProduct.setLastPage(brandDetails.isLast());
		getSimilarProduct.setFirstPage(brandDetails.isFirst());
	
		return getSimilarProduct;
	}
}
