package com.yogesh.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.yogesh.ecom.model.User;

public interface UserRepository extends JpaRepository<User,Integer> {

	boolean existsByEmail(String email);
	Optional<User> findByUserName(String username);





}
