package com.yogesh.ecom.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yogesh.ecom.requestDto.ContactRequst;
import com.yogesh.ecom.responseDto.ContactResponse;
import com.yogesh.ecom.service.AddressService;
import com.yogesh.ecom.service.ContactService;
import com.yogesh.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin(allowCredentials = "true",origins = "http://localhost:5173/")
@RequestMapping("/api/v1")
public class ContactController {
	
	private ContactService contactService;
	
	@PostMapping("/addContacts/{addressId}")
	public ResponseEntity<ResponseStructure<ContactResponse>> addContacts(@PathVariable int addressId,@RequestBody ContactRequst contactRequst )
	{
	   return contactService.addContacts(addressId,contactRequst);	
	}
	@PutMapping("/updates/{contactId}")
	public ResponseEntity<ResponseStructure<ContactResponse>> updateContact(@RequestBody ContactRequst contactRequst,@PathVariable int contactId)
	{
		return contactService.updateContact(contactRequst,contactId);
	}

}
