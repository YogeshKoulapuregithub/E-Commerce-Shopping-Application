package com.yogesh.ecom.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yogesh.ecom.requestDto.AddressRequst;
import com.yogesh.ecom.requestDto.ContactRequst;
import com.yogesh.ecom.responseDto.AddressResponse;
import com.yogesh.ecom.responseDto.ContactResponse;
import com.yogesh.ecom.service.AddressService;
import com.yogesh.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5174/")
@RequestMapping("/api/v1")
public class AddressController {

	private AddressService addressService;
	@PostMapping("/addAddress")
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(@RequestBody AddressRequst addressRequst,@CookieValue(name = "at",required = false) String accessToken)
	{
		return addressService.addAddress(addressRequst,accessToken);	
	}
	@GetMapping("/address/{addressId}")
	public ResponseEntity<ResponseStructure<List<AddressResponse>>> findAddressByUser(@CookieValue(name = "at",required = false) String accestoken,
	@CookieValue(name = "rt" ,required = false)String refreshToken)
	{
		return addressService.fondAddressByUser(accestoken,refreshToken);
	}
	@PutMapping("/update/{addressId}")
	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(@RequestBody AddressRequst addressRequst,@PathVariable int addressId)
	{
		return addressService.updateAddress(addressRequst,addressId);
	}
	

}
