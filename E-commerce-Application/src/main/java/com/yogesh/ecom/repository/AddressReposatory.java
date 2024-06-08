package com.yogesh.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogesh.ecom.model.Address;

public interface AddressReposatory extends JpaRepository<Address, Integer> {

}
