package com.yogesh.ecom.service;

import org.springframework.http.ResponseEntity;

import com.yogesh.ecom.requestDto.ProductRequest;
import com.yogesh.ecom.responseDto.ProductResponse;
import com.yogesh.ecom.utility.ResponseStructure;

public interface ProductService {

	ResponseEntity<ResponseStructure<ProductResponse>> addProducts(ProductRequest productRequest);

	ResponseEntity<ResponseStructure<ProductResponse>> findProductById(int productId);

	ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest, int productId);

}
