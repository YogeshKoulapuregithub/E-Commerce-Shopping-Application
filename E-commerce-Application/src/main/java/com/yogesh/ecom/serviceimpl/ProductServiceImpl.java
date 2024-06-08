package com.yogesh.ecom.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yogesh.ecom.exception.ProductNotFoundByID;
import com.yogesh.ecom.model.Product;
import com.yogesh.ecom.repository.ProductReposatory;
import com.yogesh.ecom.repository.UserRepository;
import com.yogesh.ecom.requestDto.ProductRequest;
import com.yogesh.ecom.responseDto.ProductResponse;
import com.yogesh.ecom.service.ProductService;
import com.yogesh.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ProductServiceImpl  implements ProductService{

	private ProductReposatory productReposatory;
	private UserRepository userRepository;
	private ResponseStructure<ProductResponse> responseStructure;
	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> addProducts(ProductRequest productRequest) {

		Product	saveProducts=productReposatory.save(mapToProductEntity(productRequest));
		return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.CREATED.value())
				.setMessage("Products added ")
				.setData(mapToProductResponse(saveProducts)));
	}
	private ProductResponse mapToProductResponse(Product products) {
		return ProductResponse.builder()
				.productName(products.getProductName())
				.productDescription(products.getProductDescription())
				.productPrice(products.getProductPrice())
				.productQuantity(products.getProductQuantity())
				.availabilityStatus(products.getAvailabilityStatus())
				.category(products.getCategory())
				.build();
	}
	private Product mapToProductEntity(ProductRequest productRequest) {

		return Product.builder()
				.productName(productRequest.getProductName())
				.productDescription(productRequest.getProductDescription())
				.productPrice(productRequest.getProductPrice())
				.productQuantity(productRequest.getProductQuantity())
				//.availabilityStatus(productRequest.getAvailabilityStatus())
				.category(productRequest.getCategory())
				.build();
	}
	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> findProductById(int productId) {
		return productReposatory.findById(productId).map(product->
		ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
				.setMessage("Product found By Id seccusFully")
				.setData(mapToProductResponse(product)))
				).orElseThrow(()-> new ProductNotFoundByID("Product not found by id"));

	}
	@Override
	public ResponseEntity<ResponseStructure<ProductResponse>> updateProduct(ProductRequest productRequest,
			int productId) {
		return productReposatory.findById(productId).map(exstingProduct->{
			Product updatedProduct = mapToProductEntity(productRequest);
			exstingProduct.setProductId(productId);
			productReposatory.save(updatedProduct);

			return ResponseEntity.ok(responseStructure.setStatuscode(HttpStatus.OK.value())
					.setMessage("Product Updated")
					.setData(mapToProductResponse(updatedProduct)));
		}).orElseThrow(()-> new ProductNotFoundByID("product Not Found By Id"));


	}

}
