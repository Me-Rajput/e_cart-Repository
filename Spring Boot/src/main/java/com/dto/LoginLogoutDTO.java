package com.dto;

import java.util.Date;

import jakarta.persistence.Transient;
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
public class LoginLogoutDTO {
	private String loginId;
	private String customerId;
	private String sessionId;
	private Date loginTime;
	private Date logoutTime;
	private boolean isActive;
	
	@Transient
	private String customerName;
}
