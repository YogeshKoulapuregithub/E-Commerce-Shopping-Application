package com.yogesh.ecom.service;

import org.springframework.http.ResponseEntity;

import com.yogesh.ecom.requestDto.ContactRequst;
import com.yogesh.ecom.responseDto.ContactResponse;
import com.yogesh.ecom.utility.ResponseStructure;

public interface ContactService {

	ResponseEntity<ResponseStructure<ContactResponse>> addContacts(int addressId, ContactRequst contactRequst);

	ResponseEntity<ResponseStructure<ContactResponse>> updateContact(ContactRequst contactRequst, int contactId);

}
