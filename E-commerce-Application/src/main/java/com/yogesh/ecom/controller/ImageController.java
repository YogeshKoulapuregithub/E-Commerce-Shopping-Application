package com.yogesh.ecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yogesh.ecom.service.ContactService;
import com.yogesh.ecom.service.ImageService;
import com.yogesh.ecom.utility.ResponseStructure;
import com.yogesh.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")
@RequestMapping("/api/v1")
public class ImageController {

	private ImageService imageService;

	@PostMapping("/products/{productId}/image-type/{imageType}/images")
	public ResponseEntity<SimpleResponseStructure> addImages(@RequestParam int productId,@RequestParam String imageType,
		@RequestParam	MultipartFile images)
	{
		return imageService.addImages(productId,imageType,images);
	}
	@GetMapping("/image/{imageId}")
	public ResponseEntity<byte[]> getImage(@RequestParam String imageId)
	{
		return imageService.findImage(imageId);
	}

}
