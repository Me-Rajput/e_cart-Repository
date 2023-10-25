package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.PaginationHelper.GetSimilarProductResponse;
import com.dto.ProductDTO;
import com.dto.SubProductDTO;
import com.entity.BrandDetails;
import com.entity.DashboardDetail;
import com.entity.SubProductDetails;
import com.entity.SubSubProductDetails;
import com.service.ProductService;

@RestController
@RequestMapping("/product")
//@CrossOrigin(origins ="*")
public class ProductController {
	
	@Autowired
	ProductService productService;

	@PostMapping("/addproduct")
	public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO){
		ProductDTO dto=productService.addProduct(productDTO);
	
		return new ResponseEntity<ProductDTO>(dto, HttpStatus.OK);
	}
	@PostMapping("/addsubproduct")
	public ResponseEntity<SubProductDTO> addSubProduct(@RequestBody SubProductDTO subProductDTO){
		SubProductDTO subProdDTO=productService.addSubProduct(subProductDTO);
			
		return new ResponseEntity<SubProductDTO>(subProdDTO, HttpStatus.OK);
	}
	@GetMapping("/getparentproductbyid/{productId}")
	public ResponseEntity<ProductDTO> getParentProductById(@PathVariable long productId){
		ProductDTO productDTO=productService.getProductById(productId);
		
		return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
	}
	@GetMapping("/getallproduct")
	public ResponseEntity<List<ProductDTO>> getAllProduct(){
		List<ProductDTO> productDTO=productService.getAllProduct();
		
		return new ResponseEntity<List<ProductDTO>>(productDTO, HttpStatus.OK);
	}
	@GetMapping("/getsubproductdetail/{id}")
	public ResponseEntity<List<SubSubProductDetails>> getSubProductDetail(@PathVariable long id){
		
		List<SubSubProductDetails> subSubProductDetails=productService.getSubSubProduct(id);
		
		return new ResponseEntity<List<SubSubProductDetails>>(subSubProductDetails, HttpStatus.OK);
	}
	@GetMapping("/getallbranddetail")
	public ResponseEntity<List<BrandDetails>> getAllBrandDetail(){
		List<BrandDetails> brandList=productService.getAllBrandDetails();
		
		return new ResponseEntity<List<BrandDetails>>(brandList, HttpStatus.OK);
	}

	/*@GetMapping("/getbranddetailbyid")
	public ResponseEntity<BrandDetails> getBrandDetailById(){
		BrandDetails details=productService.getBrandDetailById(0);
		
		return new ResponseEntity<BrandDetails>(details, HttpStatus.OK);
	}*/
	@GetMapping("/getsimilarproduct/{id}")
	public ResponseEntity<GetSimilarProductResponse> getSimilarProduct(@PathVariable long id,
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "4", required = false) int pageSize)
	{
		GetSimilarProductResponse brandDetailsList=productService.getSimilarProduct(id, pageNumber, pageSize );
		
		return new ResponseEntity<GetSimilarProductResponse>(brandDetailsList, HttpStatus.OK);
	}
}
