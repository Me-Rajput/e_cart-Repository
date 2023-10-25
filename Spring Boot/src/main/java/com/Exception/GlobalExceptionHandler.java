package com.Exception;

import org.springframework.aop.AopInvocationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFound.class)
	public ResponseEntity<String> resourceNotFound(ResourceNotFound exception){
		String str= exception.getMessage();
		
		return new ResponseEntity<String>(str, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(LogoutException.class)
	public ResponseEntity<String> logoutException(LogoutException exception){
		String str= exception.getMessage();
		
		return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
	}
   @ExceptionHandler(ItemChange.class)
   public ResponseEntity<String> itemNumberChangeException(ItemChange exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(QuantityUpdationWrongCredentialException.class)
   public ResponseEntity<String> wrongCredentialException(QuantityUpdationWrongCredentialException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(ItemRemovalException.class)
   public ResponseEntity<String> itemRemoveException(ItemRemovalException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(BatchInsertionException.class)
   public ResponseEntity<String> batchInsertionException(BatchInsertionException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(OrderInitiationException.class)
   public ResponseEntity<String> orderInitiationException(OrderInitiationException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(SingleItemPurchaseFromCartException.class)
   public ResponseEntity<String> singleItemPurchaseFromCartException(SingleItemPurchaseFromCartException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(ProductNotFoundException.class)
   public ResponseEntity<String> productNotFoundException(ProductNotFoundException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(TopProductNumberOfQuantityException.class)
   public ResponseEntity<String> topProductNUmberOfQuantityException(TopProductNumberOfQuantityException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(TopProductNotFoundException.class)
   public ResponseEntity<String> topProductNotFoundException(TopProductNotFoundException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str,HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(InvalidQueryStringException.class)
   public ResponseEntity<String> invalidQueryStringException(InvalidQueryStringException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(StarFilterDataException.class)
   public ResponseEntity<String> starfilterDataException(StarFilterDataException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(OrderCancellationException.class)
   public ResponseEntity<String> orderCancellationException(OrderCancellationException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(OrderListNotFoundException.class)
   public ResponseEntity<String> orderListNotFoundException(OrderListNotFoundException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(CustomerIDNotFoundException.class)
   public ResponseEntity<String> customerIDNotFoundException(CustomerIDNotFoundException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(CartDataNotFoundException.class)
   public ResponseEntity<String> cartDataNotFoundException(CartDataNotFoundException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(EmailOrPasswordAllreadyPresentException.class)
   public ResponseEntity<String> emailOrPasswordAllreadyPresentException(EmailOrPasswordAllreadyPresentException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(OrderNotFoundException.class)
   public ResponseEntity<String> orderNOtFoundException(OrderNotFoundException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(ProductSearchException.class)
   public ResponseEntity<String> searchingProductNotFound(ProductSearchException exception){
	   String str=exception.getMessage();
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
   @ExceptionHandler(AopInvocationException.class)
   public ResponseEntity<String> aopInvocationException(AopInvocationException exception){
	   String str="No product found with this name. Try with another product name.";
	   
	   return new ResponseEntity<String>(str, HttpStatus.BAD_REQUEST);
   }
}
