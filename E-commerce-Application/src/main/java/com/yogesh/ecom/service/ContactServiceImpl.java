package com.yogesh.ecom.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.yogesh.ecom.exception.AddressNotFoundByIdException;
import com.yogesh.ecom.exception.ContactLimitOverFlowException;
import com.yogesh.ecom.exception.ContactNotFoundByIdException;
import com.yogesh.ecom.model.Contacts;
import com.yogesh.ecom.repository.AddressReposatory;
import com.yogesh.ecom.repository.ContactReposatory;
import com.yogesh.ecom.requestDto.ContactRequst;
import com.yogesh.ecom.responseDto.ContactResponse;
import com.yogesh.ecom.utility.ResponseStructure;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService{
     private ResponseStructure<ContactResponse> responseStructure;
     private ContactReposatory contactReposatory;
     private AddressReposatory addressReposatory;
	@Override
	public ResponseEntity<ResponseStructure<ContactResponse>> addContacts(int addressId, ContactRequst contactRequst) {
		
		return  addressReposatory.findById(addressId).map(address->{
			Contacts contact=maptoContact(contactRequst);
			if(address.getContacts().size()>2)throw new ContactLimitOverFlowException("the contact limit is reached");
	
			address.getContacts().add(contact);
			contactReposatory.save(contact);
			addressReposatory.save(address);
			return ResponseEntity.ok().body(responseStructure.setData(maptoContactResponse(contact))
					.setMessage("done")
					.setStatuscode(HttpStatus.OK.value()));
			}).orElseThrow(()->new AddressNotFoundByIdException("Address is Not found"));

	}

	private ContactResponse maptoContactResponse(Contacts contact) {
		
		return ContactResponse.builder()
				.contactId(contact.getContactId())
				.name(contact.getName())
				.email(contact.getEmail())
				.phoneNumber(contact.getPhoneNumber()).build();

	}

	private Contacts maptoContact(ContactRequst contactRequst) {
		Contacts contact=new Contacts();
		System.out.println(contactRequst.getName());
		contact.setName(contactRequst.getName());
		contact.setEmail(contactRequst.getEmail());
		contact.setPhoneNumber(contactRequst.getPhoneNumber());
		contact.setPriority(contactRequst.getPriority());
		return contact;
	}

	@Override
	public ResponseEntity<ResponseStructure<ContactResponse>> updateContact(ContactRequst contactRequst,
			int contactId) {
		Contacts contact2=contactReposatory.findById(contactId).map(contact -> {
			return contactReposatory.save(mapToContact(contact,contactRequst));
		}).orElseThrow(() -> new ContactNotFoundByIdException("contact not found by id"));
		return ResponseEntity.ok(new ResponseStructure<ContactResponse>().setData(maptoContactResponse(contact2))
				.setMessage("update contact").setStatuscode(HttpStatus.OK.value()));	
	}

	private Contacts mapToContact(Contacts contact, ContactRequst contactRequst) {
		contact.setEmail(contactRequst.getEmail());
		contact.setName(contactRequst.getName());
		contact.setPhoneNumber(contactRequst.getPhoneNumber());
		contact.setPriority(contactRequst.getPriority());
		return contact;
	}

	
}
