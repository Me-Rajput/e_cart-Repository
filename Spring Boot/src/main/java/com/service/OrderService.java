package com.service;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.core.pattern.AbstractStyleNameConverter.Black;
import org.aspectj.apache.bcel.classfile.Utility;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.Exception.BatchInsertionException;
import com.Exception.CustomerIDNotFoundException;
import com.Exception.ItemRemovalException;
import com.Exception.OrderCancellationException;
import com.Exception.OrderInitiationException;
import com.Exception.OrderListNotFoundException;
import com.Exception.OrderNotFoundException;
import com.Exception.ResourceNotFound;
import com.dao.CartDetailDao;
import com.dao.CustomerDao;
import com.dao.OrderDao;
import com.dto.BulkOrderDTO;
import com.dto.CustomerDTO;
import com.dto.OrderDTO;
import com.dto.SingleOrderFromCart;
import com.entity.CartDetail;
import com.entity.CustomerDetails;
import com.entity.OrderDetails;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPRow;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.draw.LineSeparator;
import com.utility.IdGenerator;

@Service
public class OrderService {

	@Autowired
	OrderDao orderDao;
	
	@Autowired
	CustomerDao customerDao;
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	CartDetailDao cartDetailDao;
	
	@Autowired
	ModelMapper modelMapper;
	
	public OrderDTO addNewOrder(OrderDTO details, long customerId){
		long orderId=IdGenerator.generateOrderId();
		CustomerDetails customer=customerDao.findById(customerId).orElseThrow(()-> new ResourceNotFound("Customer id not found."));
		OrderDetails orderDetails=modelMapper.map(details, OrderDetails.class);
		
		orderDetails.setOrderId(orderId);
		orderDetails.setCustomerDetails(customer);
		orderDetails.setOrderDate(new Date());
		orderDetails.setDeliveryDate(new Date());
		orderDetails.setTotalPrice(orderDetails.getUnitPrice()*orderDetails.getQuantity());
		orderDetails.setOrderStatus("Order Placed");
		orderDetails.setOrderStatusValue(25);
		
		OrderDetails addedOrder=orderDao.save(orderDetails);
		
		return modelMapper.map(addedOrder, OrderDTO.class);
	}
	public List<OrderDTO> getOrderByOrderId(long customerId) {
		List<OrderDetails> orderDetails=orderDao.findById(customerId);
		   if(orderDetails==null) {
			 throw new ResourceNotFound("Customer Id Not Found");
		  }
		List<OrderDTO> dtoList=new ArrayList<>();
		
		orderDetails.forEach(orderData->{
			OrderDTO orderDTO=new OrderDTO();
			
			orderDTO.setOrderId(String.valueOf(orderData.getOrderId()));
			orderDTO.setDeliveryAddress(orderData.getDeliveryAddress());
			orderDTO.setDeliveryDate(orderData.getDeliveryDate());
			orderDTO.setDescription(orderData.getDescription());
			orderDTO.setImageurl(orderData.getImageurl());
			orderDTO.setOrderDate(orderData.getOrderDate());
			orderDTO.setOrderStatus(orderData.getOrderStatus());
			orderDTO.setOrderStatusValue(orderData.getOrderStatusValue());
			orderDTO.setProductId(orderData.getProductID());
			orderDTO.setQuantity(orderData.getQuantity());
			orderDTO.setUnitPrice(orderData.getUnitPrice());
			
			dtoList.add(orderDTO);
		});
		return dtoList;
	}
	public List<OrderDTO> getAllOrder(){
		List<OrderDetails> orderDetails=orderDao.findAll();
		List<OrderDTO> orderDTOs=orderDetails.stream().map((order)->modelMapper.map(order, OrderDTO.class)).collect(Collectors.toList());
		
		return orderDTOs;
	}
	public int bulkOrderFromCart(List<BulkOrderDTO> listOfOrder, long customerId, String address) {
		int val=0;
		CustomerDetails customerDetails=customerDao.findById(customerId).orElseThrow(()->new ResourceNotFound("Customer not found"));
		List<OrderDetails> orderDetailList=new ArrayList<>();
		 
		listOfOrder.forEach(order->{
			OrderDetails orderDetails= new OrderDetails();	
			long orderId=IdGenerator.generateOrderId();
			orderDetails.setCustomerDetails(customerDetails);
			orderDetails.setDeliveryAddress(address);
			orderDetails.setDeliveryDate(new Date());
			orderDetails.setDescription(order.getDescription());
			orderDetails.setImageurl(order.getImageURL());
			orderDetails.setOrderDate(new Date());
			orderDetails.setOrderId(orderId);
			orderDetails.setProductID(String.valueOf(order.getProductId()));
			orderDetails.setQuantity(order.getQuantity());
			orderDetails.setTotalPrice(order.getPrice()*order.getQuantity());
			orderDetails.setUnitPrice(order.getPrice());
			orderDetails.setOrderStatus("Order Placed");
			orderDetails.setOrderStatusValue(25);
			
			orderDetailList.add(orderDetails);
	});
			List<OrderDetails> savedOrderDetailList=orderDao.saveAll(orderDetailList);
			
			if(savedOrderDetailList.isEmpty()) {
				throw new BatchInsertionException("Batch insertion failed");
			}
			if(savedOrderDetailList.size()==listOfOrder.size()) {
				 val=cartDetailDao.emptyCartAfterOrder(customerId);
				 if(val==0) {
					 throw new ItemRemovalException("Items cannot be removed from cart");
				 }
			}
			return val;
  }
	public int singleOrderFromCart(SingleOrderFromCart singleOrderFromCart) {
		int val=0;
		long prodId=singleOrderFromCart.getProductId();
		long custId=singleOrderFromCart.getCustomerId();
		long orderId=IdGenerator.generateOrderId();
		CustomerDetails customerDetails=customerDao.findById(custId).orElseThrow(()->new ResourceNotFound("Customer not found"));
		CartDetail cartDetail=cartDetailDao.isProductPresent(prodId, custId);
		if(cartDetail==null) {
			throw new ResourceNotFound("Product not found");
		}
		OrderDetails orderDetails= new OrderDetails();
		orderDetails.setCustomerDetails(customerDetails);
		orderDetails.setDeliveryAddress(singleOrderFromCart.getAddress());
		orderDetails.setDeliveryDate(null);
		orderDetails.setDescription(cartDetail.getDescription());
		orderDetails.setImageurl(cartDetail.getImageURL());
		orderDetails.setOrderDate(new Date());
		orderDetails.setOrderId(orderId);
		orderDetails.setProductID(String.valueOf(singleOrderFromCart.getProductId()));
		orderDetails.setQuantity(cartDetail.getQuantity());
		orderDetails.setTotalPrice(cartDetail.getQuantity()*cartDetail.getPrice());
		orderDetails.setUnitPrice(cartDetail.getPrice());
		orderDetails.setOrderStatus("Order Placed");
		orderDetails.setOrderStatusValue(25);
		
		OrderDetails saveOrderDetails=orderDao.save(orderDetails);
		if(saveOrderDetails==null) {
			throw new OrderInitiationException("Order Cannot Placed");
		}
		val=cartDetailDao.removeFromCart(custId, prodId);
		if(val==0) {
			throw new ItemRemovalException("Item cannot removed from cart");
		}
		return val;
	}
	public int cancelOrder(long orderId, long customerId) {
		int cancelValue=orderDao.cancelOrder(orderId, customerId);
		if(cancelValue==0) {
			throw new OrderCancellationException("Order cancellation failed!!!");
		}
		return cancelValue;
	}
	public List<OrderDTO> getFilteredOrderList(long customerId, int filterValue){
		String filterQuery="";
		
		if(filterValue==-1) {
			filterQuery=" and order_status_value=-1";
		}else if(filterValue==100) {
			filterQuery=" and order_status_value=100";
		}else if(filterValue==-2) {
			filterQuery="";
		}else {
			throw new OrderListNotFoundException("Invalid filter query value!!!");
		}
		
		List<OrderDetails> orderDetails=orderDao.getFilteredOrderList(customerId, filterQuery);
		
		List<OrderDTO> dtoList=new ArrayList<>();
		
		orderDetails.forEach(orderData->{
			OrderDTO orderDTO=new OrderDTO();
			
			orderDTO.setOrderId(String.valueOf(orderData.getOrderId()));
			orderDTO.setDeliveryAddress(orderData.getDeliveryAddress());
			orderDTO.setDeliveryDate(orderData.getDeliveryDate());
			orderDTO.setDescription(orderData.getDescription());
			orderDTO.setImageurl(orderData.getImageurl());
			orderDTO.setOrderDate(orderData.getOrderDate());
			orderDTO.setOrderStatus(orderData.getOrderStatus());
			orderDTO.setOrderStatusValue(orderData.getOrderStatusValue());
			orderDTO.setProductId(orderData.getProductID());
			orderDTO.setQuantity(orderData.getQuantity());
			orderDTO.setUnitPrice(orderData.getUnitPrice());
			
			dtoList.add(orderDTO);
		});
		
		return dtoList;
	}
	public ByteArrayInputStream createInvoicePdf(Long orderId) {
		OrderDetails orderDetails=orderDao.findById(orderId).orElseThrow(()-> new OrderNotFoundException("Order not found. May be invalid order id"));
		Date date=orderDetails.getOrderDate();
		SimpleDateFormat dateFormat=new SimpleDateFormat("dd-MM-yyyy");
		String orderDate=dateFormat.format(date);
		
		String invoiceDate=dateFormat.format(new Date());
		
		Long custId=orderDetails.getCustomer_id();
		CustomerDTO customerDTO=restTemplate.getForObject("http://localhost:8080/customer/getcustomerdata/"+custId, CustomerDTO.class);
		String title="Shopfast";

		ByteArrayOutputStream out=new ByteArrayOutputStream();
		
		Document document=new Document(PageSize.A4);
		PdfWriter.getInstance(document, out);
		
		float width = document.getPageSize().getWidth();
		/*------------------------------------Font style----------------------------------------------------------------------------------*/
		Font titleFont=FontFactory.getFont(FontFactory.TIMES_ITALIC,20,Color.BLUE);
		Font font8 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 15);
        
		/*------------------------------------Footer----------------------------------------------------------------------------------*/
        HeaderFooter footer=new HeaderFooter(true, new Paragraph(title,titleFont));
        footer.setAlignment(Element.ALIGN_CENTER);
        footer.setBorderWidthBottom(0);
        document.setFooter(footer);
		
        /*------------------------------------Document Open----------------------------------------------------------------------------------*/
		document.open();
        LineSeparator lineSeparator=new LineSeparator();
        
        PdfPTable invoiceTable=new PdfPTable(2);
        invoiceTable.setHorizontalAlignment(0);
        invoiceTable.setSpacingAfter(10);
        invoiceTable.setSpacingBefore(10);
        invoiceTable.setWidthPercentage(100);
        
        PdfPCell invoiceCell=new PdfPCell(new Paragraph("Invoice Details",font8));
        invoiceCell.setHorizontalAlignment(Element.ALIGN_LEFT);
        invoiceCell.setBorder(0);
        invoiceTable.addCell(invoiceCell);
        
        PdfPCell invoiceDateCell=new PdfPCell(new Paragraph("Invoice Date : "+invoiceDate)); 
        invoiceDateCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        invoiceDateCell.setBorder(0);
        invoiceTable.addCell(invoiceDateCell);
        
        
        /*---------------------------------------- Order Table-----------------------------------------------------------------------------*/
        
        Font orderDetailFont=FontFactory.getFont(FontFactory.HELVETICA_BOLD,12);
        PdfPTable orderDetailTable=new PdfPTable(2);
        orderDetailTable.setSpacingBefore(10);
        orderDetailTable.setSpacingAfter(10);
        orderDetailTable.getDefaultCell().setBorder(0);
        orderDetailTable.setHorizontalAlignment(0);
        orderDetailTable.setTotalWidth(width - 72);
        orderDetailTable.setLockedWidth(true);
        
        /*----------------------------------------Ordetail detail Left Table (2nd Table)-----------------------------------------------------------*/
        
        PdfPTable leftTable =new PdfPTable(1);
        PdfPCell leftTableCell1=new PdfPCell(new Paragraph("Order Detail ",orderDetailFont));
        leftTableCell1.setBorder(0);
        leftTable.addCell(leftTableCell1);
        
        PdfPCell leftTableCell2=new PdfPCell(new Paragraph("Order ID : "+orderDetails.getOrderId()));
        leftTableCell2.setBorder(0);
        leftTable.addCell(leftTableCell2);
        
        PdfPCell leftTableCell3=new PdfPCell(new Paragraph("Order Date : "+orderDate));
        leftTableCell3.setBorder(0);
        leftTable.addCell(leftTableCell3);
        
        PdfPCell leftTableCell4=new PdfPCell(new Paragraph("Delivery Address : "+orderDetails.getDeliveryAddress()));
        leftTableCell4.setBorder(0);
        leftTable.addCell(leftTableCell4);
        
        orderDetailTable.addCell(leftTable);
        
       /*----------------------------------------Ordetail detail Right Table (2nd Table)-----------------------------------------------------------*/
        PdfPTable rightTable=new PdfPTable(1);
        
        PdfPCell rightTableCell1=new PdfPCell(new Paragraph("Customer Detail",orderDetailFont));
        rightTableCell1.setBorder(0);
        rightTableCell1.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        rightTable.addCell(rightTableCell1);
        
        PdfPCell rightTableCell2=new PdfPCell(new Paragraph("Name : "+customerDTO.getCustomerName()));
        rightTableCell2.setBorder(0);
        rightTable.addCell(rightTableCell2);
        
        PdfPCell rightTableCell3=new PdfPCell(new Paragraph("Mobile : "+customerDTO.getCustomerPh()));
        rightTableCell3.setBorder(0);
        rightTable.addCell(rightTableCell3);
        
        PdfPCell rightTableCell4=new PdfPCell(new Paragraph("Address : "+customerDTO.getCustomerAddress()));
        rightTableCell4.setBorder(0);
        rightTable.addCell(rightTableCell4);
        
        orderDetailTable.addCell(rightTable);

        /* -----------------------------------------Product Detail---------------------------------------------------------------------*/
        PdfPTable productDetailTable=new PdfPTable(5);
        productDetailTable.setSpacingBefore(5);
        productDetailTable.setSpacingAfter(20);
        productDetailTable.setHorizontalAlignment(0);
        productDetailTable.setWidthPercentage(100);
        
        PdfPCell row1Cell1=new PdfPCell(new Paragraph("Sl No.",orderDetailFont));
        row1Cell1.setMinimumHeight(30);
        row1Cell1.setBackgroundColor(Color.LIGHT_GRAY);
        row1Cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
        row1Cell1.setBorder(0);
        row1Cell1.setBorderWidthTop(1);
        row1Cell1.setBorderWidthBottom(1);
        row1Cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row1Cell1);
        
        PdfPCell row1Cell2=new PdfPCell(new Paragraph("Description",orderDetailFont));
        row1Cell2.setBackgroundColor(Color.LIGHT_GRAY);
        row1Cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        row1Cell2.setBorder(0);
        row1Cell2.setBorderWidthTop(1);
        row1Cell2.setBorderWidthBottom(1);
        row1Cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row1Cell2);
        
       PdfPCell row1Cell3=new PdfPCell(new Paragraph("QTY.",orderDetailFont));
       row1Cell3.setBackgroundColor(Color.LIGHT_GRAY);
       row1Cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
       row1Cell3.setBorder(0);
       row1Cell3.setBorderWidthTop(1);
       row1Cell3.setBorderWidthBottom(1);
       row1Cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row1Cell3);
        
        PdfPCell row1Cell4=new PdfPCell(new Paragraph("Unit Price",orderDetailFont));
        row1Cell4.setBackgroundColor(Color.LIGHT_GRAY);
        row1Cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        row1Cell4.setBorder(0);
        row1Cell4.setBorderWidthTop(1);
        row1Cell4.setBorderWidthBottom(1);
        row1Cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row1Cell4);
        
        PdfPCell row1Cell5=new PdfPCell(new Paragraph("Total Price",orderDetailFont));
        row1Cell5.setBackgroundColor(Color.LIGHT_GRAY);
        row1Cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
        row1Cell5.setBorder(0);
        row1Cell5.setBorderWidthTop(1);
        row1Cell5.setBorderWidthBottom(1);
        row1Cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row1Cell5);
        
        PdfPCell row2Cell1=new PdfPCell(new Paragraph("1."));
        row2Cell1.setBorder(0);
        row2Cell1.setMinimumHeight(100);
        row2Cell1.setBorderWidthBottom(1);
        row2Cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row2Cell1);
        
        PdfPCell row2Cell2=new PdfPCell(new Paragraph(orderDetails.getDescription()));
        row2Cell2.setBorder(0);
        row2Cell2.setBorderWidthBottom(1);
        row2Cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row2Cell2);
        
        PdfPCell row2Cell3=new PdfPCell(new Paragraph(String.valueOf(orderDetails.getQuantity())));
        row2Cell3.setBorder(0);
        row2Cell3.setBorderWidthBottom(1);
        row2Cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row2Cell3);
        
        PdfPCell row2Cell4=new PdfPCell(new Paragraph(String.valueOf(orderDetails.getUnitPrice())));
        row2Cell4.setBorder(0);
        row2Cell4.setBorderWidthBottom(1);
        row2Cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row2Cell4);
        
        PdfPCell row2Cell5=new PdfPCell(new Paragraph(String.valueOf(orderDetails.getTotalPrice())));
        row2Cell5.setBorder(0);
        row2Cell5.setBorderWidthBottom(1);
        row2Cell5.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row2Cell5);
        
        PdfPCell row3Cell1=new PdfPCell(new Paragraph("Grand Total",orderDetailFont));
        row3Cell1.setColspan(4);
        row3Cell1.setBorder(0);
        row3Cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
        productDetailTable.addCell(row3Cell1);
        
        PdfPCell row3Cell2=new PdfPCell(new Paragraph(String.valueOf(orderDetails.getTotalPrice())));
        row3Cell2.setBorder(0);
        row3Cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        productDetailTable.addCell(row3Cell2);
        
        
        /*------------------------------------------Adding values to document-----------------------------------------------------------*/
        document.add(invoiceTable);
        document.add(lineSeparator);
        document.add(orderDetailTable);
     
        document.add(productDetailTable);
        
		document.close();
		
		return new ByteArrayInputStream(out.toByteArray());
	}
}