package com.dto;

import com.entity.SubSubProductDetails;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubSubProductDTO {

    private long subsubId;
	private String brandeName;
	private long subId;
}
