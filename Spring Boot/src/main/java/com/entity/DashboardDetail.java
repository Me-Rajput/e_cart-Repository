package com.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@IdClass(DashboardDetailHelper.class)
//@Table(name = "dashboarddetail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DashboardDetail {
	  
	@Id
	private long sub_productid;
	private long subsub_id;
	private long product_id;
	private String product_name;
	private boolean status;
	private String brande_name;
	private String color;
	private String description;
}
