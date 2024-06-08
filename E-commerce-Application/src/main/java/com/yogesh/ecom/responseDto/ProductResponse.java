package com.yogesh.ecom.responseDto;

import java.util.List;

import com.yogesh.ecom.enums.AvailabilityStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ProductResponse {
	private int productId;
	private String productName;
	private String productDescription;
	private double productPrice;
	private int productQuantity;
	private String category;
	private AvailabilityStatus availabilityStatus;
	private List<ImageResponse> imagesresponse;

}
