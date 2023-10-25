package com.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.Exception.OrderCancellationException;
import com.Exception.OrderListNotFoundException;
import com.Exception.SingleItemPurchaseFromCartException;
import com.dto.BulkOrderDTO;
import com.dto.CancelOrderDTO;
import com.dto.OrderDTO;
import com.dto.SingleOrderFromCart;
import com.entity.CartDetail;
import com.entity.LoginLogoutDetails;
import com.entity.OrderDetails;
import com.service.OrderService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	RestTemplate restTemplate;
	
	@PostMapping("/addneworder/{userId}")
	public ResponseEntity<OrderDTO> addNewOrder(@RequestBody OrderDTO orderDetails, @PathVariable long userId){
		OrderDTO details=orderService.addNewOrder(orderDetails, userId);
		
		return new ResponseEntity<OrderDTO>(details, HttpStatus.CREATED);
	}
	@GetMapping("/getbyorderid/{customerId}")
	public ResponseEntity<List<OrderDTO>> getByOrderID(@PathVariable long customerId){
		List<OrderDTO> orderDetails=orderService.getOrderByOrderId(customerId);
		
		return new ResponseEntity<List<OrderDTO>>(orderDetails, HttpStatus.OK);
	}
	@GetMapping("/getallorder")
	public ResponseEntity<List<OrderDTO>> getAllOrder(){
		List<OrderDTO> orderDTOs=orderService.getAllOrder();
		
		return new ResponseEntity<List<OrderDTO>>(orderDTOs, HttpStatus.OK);
	}
	@PostMapping("/bulkorderfromcart/{customerId}/{address}")
	public ResponseEntity<List<CartDetail>> placeBulkOrderFromCart(@RequestBody List<BulkOrderDTO> listOfOrder, @PathVariable String customerId, @PathVariable String address){
		//SecurityContextImpl securityContextImpl =(SecurityContextImpl) request.getSession().getAttribute("loginLogoutDetails");
		long custId=Long.parseLong(customerId);
		int val=orderService.bulkOrderFromCart(listOfOrder, custId, address);
		List cartDetails=null;
		if(val!=0) {
			cartDetails=restTemplate.getForObject("http://localhost:8080/cart/getallproduct/"+custId, List.class);
		}
		return new ResponseEntity<List<CartDetail>>(cartDetails,HttpStatus.OK);
	}
	@PostMapping("/singleorderfromcart")
	public ResponseEntity<List> placeSingleOrderFromCart(@RequestBody SingleOrderFromCart orderDetails ){
		String customerId=String.valueOf(orderDetails.getCustomerId());
		List cartList=null;
		int val=orderService.singleOrderFromCart(orderDetails);
		if(val==0) {
			throw new SingleItemPurchaseFromCartException("Transaction to purchase this item failed");
		}else {
			cartList=restTemplate.getForObject("http://localhost:8080/cart/getallproduct/"+customerId, List.class);
		}
		return new ResponseEntity<List>(cartList, HttpStatus.OK);
	}
	@GetMapping("/cancelorder/{orderId}/{customerId}")
	public ResponseEntity<CancelOrderDTO> cancelOrder(@PathVariable long orderId, @PathVariable long customerId){
		int cancelValue=orderService.cancelOrder(orderId, customerId);
		CancelOrderDTO orderDTO=null;
		List orderList=null;
		if(cancelValue==1) {
			orderList=restTemplate.getForObject("http://localhost:8080/order/getbyorderid/"+customerId, List.class);
			if(orderList.size()!=0) {
				orderDTO=new CancelOrderDTO();
				orderDTO.setCancelValue(cancelValue);
				orderDTO.setOrderList(orderList);
			}else {
				throw new OrderListNotFoundException("Orderlist not found!!!");
			}
		}else {
			throw new OrderCancellationException("Order cancellation failed!!!");
		}
		
		return new ResponseEntity<CancelOrderDTO>(orderDTO, HttpStatus.OK);
	}
	@GetMapping("/getfilteredorderlist/{customerId}")
	public ResponseEntity<List<OrderDTO>> getFilteredOrder(@PathVariable long customerId,
			@RequestParam(defaultValue = "-2", required = false, name = "filterValue") int filterValue){
		List<OrderDTO> orderDetails=orderService.getFilteredOrderList(customerId, filterValue);
		
		return new ResponseEntity<List<OrderDTO>>(orderDetails, HttpStatus.OK);
	}
	@GetMapping("/downloadinvoice/{orderId}")
	public ResponseEntity<InputStreamResource> downloadInvoice(@PathVariable Long orderId){
		String fileName="Invoice.pdf";
		ByteArrayInputStream pdf=orderService.createInvoicePdf(orderId);
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=\""+fileName+"\"");
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(new InputStreamResource(pdf));
	}
	@GetMapping("/downloadinvoicefromreact/{orderId}")
	public ResponseEntity<ByteArrayInputStream> downloadInvoiceFromReact(@PathVariable Long orderId){
		String fileName="Invoice.pdf";
		ByteArrayInputStream pdf=orderService.createInvoicePdf(orderId);
		
		HttpHeaders headers=new HttpHeaders();
		headers.add("Content-Disposition", "attachment; filename=\""+fileName+"\"");
		
		return new ResponseEntity<ByteArrayInputStream>(pdf,headers, HttpStatus.OK);
	}
}
