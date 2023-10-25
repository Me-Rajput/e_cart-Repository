package com.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerDTO {

	
	private String customerName;
	private String customerPh;
	private String customerAddress;
	private String email;
	private String password;
}
