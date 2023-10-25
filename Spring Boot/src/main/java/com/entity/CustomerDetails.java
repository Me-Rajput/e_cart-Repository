package com.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customerdetails")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetails {

	@Id
	@Column(name = "customer_id", nullable = false, unique = true)
	private long customerId;
	
	@Column(name = "customer_name", nullable = false)
	private String customerName;
	
	@Column(name="customer_ph")
	private String customerPh;
	
	@Column(name="customer_address")
	private String customerAddress;
	
	@OneToMany(mappedBy = "customerDetails", fetch = FetchType.LAZY)
	private List<OrderDetails> orderDetail;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "password")
	private String password;
	
}
