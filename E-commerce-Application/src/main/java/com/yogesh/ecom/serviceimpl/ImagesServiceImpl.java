package com.yogesh.ecom.serviceimpl;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.yogesh.ecom.enums.ImageType;
import com.yogesh.ecom.exception.IlligalAccesRequstException;
import com.yogesh.ecom.exception.ImageIsNotFoundByIdException;
import com.yogesh.ecom.exception.InvalidCredentialException;
import com.yogesh.ecom.exception.InvalidInputException;
import com.yogesh.ecom.exception.ProductNotFoundByID;
import com.yogesh.ecom.model.Image;
import com.yogesh.ecom.repository.ImageReposatory;
import com.yogesh.ecom.repository.ProductReposatory;
import com.yogesh.ecom.service.ImageService;
import com.yogesh.ecom.utility.ResponseStructure;
import com.yogesh.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ImagesServiceImpl implements ImageService {

	private ImageReposatory imageReposatory;
	private ProductReposatory productReposatory;
	private SimpleResponseStructure simpleResponseStructure;

	@Override
	public ResponseEntity<SimpleResponseStructure> addImages(int productId, String imageType, MultipartFile images)  {
		if(!productReposatory.existsById(productId)) throw new ProductNotFoundByID("the product Id is not Found");
		ImageType type=null;
		try {
			type=ImageType.valueOf(imageType.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new InvalidCredentialException("Invalid Image Type");
		}
		if(type.equals(ImageType.COVER)) 
			imageReposatory.findByProductIdAndImageType(productId,ImageType.COVER).ifPresent(imageData->{
				imageData.setImageType(ImageType.REGULAR);
				imageReposatory.save(imageData);

			});
		try {
			imageReposatory.save(Image.builder()
					.imageByte(images.getBytes())
					.productId(productId)
					.imageType(type)
					.contentType(images.getContentType())
					.build());
		} catch (IOException e) {
             throw new InvalidInputException("Invalid Input Exception");
		}


		return ResponseEntity.ok(simpleResponseStructure.setStatus(HttpStatus.OK.value())
				.setMessage("Images Added "));

	}

	@Override
	public ResponseEntity<byte[]> findImage(String imageId) {
		
		return imageReposatory.findById(imageId).map(image->{
			return ResponseEntity.ok()
					.contentLength(image.getImageByte().length)
					.contentType(MediaType.valueOf(image.getContentType()))
					.body(image.getImageByte());
		}).orElseThrow(()->new ImageIsNotFoundByIdException("Image not found"));
	}

}
