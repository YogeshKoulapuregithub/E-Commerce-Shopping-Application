package com.yogesh.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.yogesh.ecom.model.Product;

public interface ProductReposatory extends JpaRepository<Product, Integer>,JpaSpecificationExecutor<Product> {

}
