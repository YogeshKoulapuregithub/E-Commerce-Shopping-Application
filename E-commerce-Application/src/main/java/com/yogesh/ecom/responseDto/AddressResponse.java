package com.yogesh.ecom.responseDto;

import java.util.List;

import com.yogesh.ecom.enums.AddressType;
import com.yogesh.ecom.model.Contacts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder

public class AddressResponse {
	private int addressId;
	private String streetAddress;
	private String streetAddressAdditional;
	private String city;
	private String state;
	private int pincode;
	private AddressType addressType;
	private List<ContactResponse> contacts;
	
}
