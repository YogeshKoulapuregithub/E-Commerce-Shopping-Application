package com.yogesh.ecom.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.yogesh.ecom.requestDto.AddressRequst;
import com.yogesh.ecom.requestDto.ContactRequst;
import com.yogesh.ecom.responseDto.AddressResponse;
import com.yogesh.ecom.responseDto.ContactResponse;
import com.yogesh.ecom.utility.ResponseStructure;

public interface AddressService {

	ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequst addressRequst, String accessToken);

	ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(AddressRequst addressRequst, int addressId);

	ResponseEntity<ResponseStructure<List<AddressResponse>>> fondAddressByUser(String accessToken,String refreshToken);

	
}
