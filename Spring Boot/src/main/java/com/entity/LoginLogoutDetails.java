package com.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "loginlogoutdetails")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginLogoutDetails {
	
	@Id
	private long loginId;
	private long customerId;
	private String sessionId;
	private Date loginTime;
	private Date logoutTime;
	private boolean isActive;
	
	@Transient
	private String customerName;
}
