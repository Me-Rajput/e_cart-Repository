package com.entity;

import java.util.ArrayList;
import java.util.List;

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
@Table(name="subsubproductdetails")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubSubProductDetails {

	@Id
	private long subsubId;
	
	private String brandeName;
	
	/*private double color;
	
	private String price;
	
	private String size;*/
	
	private long subId;
	
	@Transient
	private List<BrandDetails> brandDetails= new ArrayList<>();
	
}
