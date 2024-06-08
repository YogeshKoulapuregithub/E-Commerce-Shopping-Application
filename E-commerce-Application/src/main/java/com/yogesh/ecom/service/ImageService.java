package com.yogesh.ecom.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.yogesh.ecom.utility.ResponseStructure;
import com.yogesh.ecom.utility.SimpleResponseStructure;

public interface ImageService {

	ResponseEntity<SimpleResponseStructure> addImages(int productId, String imageType, MultipartFile images);

	ResponseEntity<byte[]> findImage(String imageId);

}
