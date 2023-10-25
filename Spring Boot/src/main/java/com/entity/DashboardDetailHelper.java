package com.entity;

import java.io.Serializable;

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
public class DashboardDetailHelper implements Serializable {
	private long sub_productid;
	private long subsub_id;
	private long product_id;
	private String product_name;
	private boolean status;
	private String brande_name;
	private String color;
	private String description;
}
