package com.yogesh.ecom.service;

import org.springframework.http.ResponseEntity;

import com.yogesh.ecom.requestDto.AuthRequst;
import com.yogesh.ecom.requestDto.OtpRequest;
import com.yogesh.ecom.requestDto.UserRequst;
import com.yogesh.ecom.responseDto.AuthResponse;
import com.yogesh.ecom.responseDto.UserResponse;
import com.yogesh.ecom.utility.ResponseStructure;
import com.yogesh.ecom.utility.SimpleResponseStructure;

public interface UserService {

	ResponseEntity<SimpleResponseStructure> userRegistration(UserRequst userRequst);

	ResponseEntity<ResponseStructure<UserResponse>> verifyOtp(OtpRequest otpRequest);

	ResponseEntity<ResponseStructure<AuthResponse>> logIn(AuthRequst authRequst);

	ResponseEntity<ResponseStructure<AuthResponse>> logOut(String accesToken, String refreshToken);

	ResponseEntity<ResponseStructure<AuthResponse>> refreshLogin(String accesToken, String refreshToken);

}
