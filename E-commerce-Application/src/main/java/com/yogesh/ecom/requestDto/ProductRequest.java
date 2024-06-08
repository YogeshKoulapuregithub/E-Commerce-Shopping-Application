package com.yogesh.ecom.requestDto;

import com.yogesh.ecom.enums.AvailabilityStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ProductRequest
{
	private String productName;
	private String productDescription;
	private double productPrice;
	private int productQuantity;
	private String category;
	//private AvailabilityStatus availabilityStatus;
}
