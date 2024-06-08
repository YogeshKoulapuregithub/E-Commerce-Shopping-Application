package com.yogesh.ecom.responseDto;

import com.yogesh.ecom.enums.priority;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ContactResponse {
	private int contactId;
	private String name;
	private long phoneNumber;
	private String email;
	private priority priority;

}
