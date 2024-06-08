package com.yogesh.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yogesh.ecom.model.RefreshToken;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken,Integer> {

	

	boolean existsByTokenAndBlocked(String rt, boolean b);

	Optional<RefreshToken> findByToken(String refreshToken);

}
