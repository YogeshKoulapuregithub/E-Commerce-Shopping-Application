package com.yogesh.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogesh.ecom.model.AccessToken;

public interface AccesTokenRepository extends JpaRepository<AccessToken,Integer> {

	

	boolean existsByTokenAndBlocked(String at, boolean b);

	Optional<AccessToken> findByToken(String accesToken);
	

	

}
