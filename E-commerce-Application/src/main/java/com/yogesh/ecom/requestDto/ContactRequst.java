package com.yogesh.ecom.requestDto;

import com.yogesh.ecom.enums.priority;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ContactRequst {
	private String name;
	private long phoneNumber;
	private String email;
	private priority priority;

}
