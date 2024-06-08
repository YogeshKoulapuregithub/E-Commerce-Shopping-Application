package com.yogesh.ecom.controller;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.yogesh.ecom.model.Product;
import com.yogesh.ecom.repository.ProductReposatory;
import com.yogesh.ecom.requestDto.ProductRequest;
import com.yogesh.ecom.requestDto.SearchFilter;
import com.yogesh.ecom.responseDto.ProductResponse;
import com.yogesh.ecom.service.ProductService;
import com.yogesh.ecom.utility.ProductSpecification;
import com.yogesh.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")
@AllArgsConstructor
public class ProductController {
	
	private ProductService productService;
	private ProductSpecification productSpecification;
	private  ProductReposatory productReposatory;
	
	
	@PreAuthorize("hasAuthority('SELLER')")
	@PostMapping("/products")
	public ResponseEntity<ResponseStructure<ProductResponse>> addProducts(@RequestBody ProductRequest productRequest)
	{
		return productService.addProducts(productRequest);
	}
	@GetMapping("/product/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> findProductById(@PathVariable int productId)
	{
		return productService.findProductById(productId);
	}
	@PutMapping("/product/{productId}")
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(@RequestBody ProductRequest productRequest,@PathVariable int productId)
	{
		return productService.updateProduct(productRequest,productId);
	}
	@GetMapping("/products/filter")
	public List<Product> findProductByFilter (SearchFilter searchFilter)
	{
		
		  Specification<Product> specification = productSpecification.buildSpecification();
		  List<Product> list = productReposatory.findAll(specification);
		  return list;
	}
	

}
