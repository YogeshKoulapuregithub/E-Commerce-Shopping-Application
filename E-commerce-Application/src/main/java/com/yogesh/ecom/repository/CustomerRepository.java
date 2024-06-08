package com.yogesh.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogesh.ecom.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

}
