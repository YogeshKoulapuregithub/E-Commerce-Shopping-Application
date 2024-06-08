package com.yogesh.ecom.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.mongodb.client.MongoDatabase;
import com.yogesh.ecom.enums.ImageType;
import com.yogesh.ecom.model.Image;

public interface ImageReposatory extends MongoRepository<Image,String>{

	Optional<Image> findByProductIdAndImageType(int productId, ImageType cover);

}
