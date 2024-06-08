package com.yogesh.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RegistrationSessionExpiredException extends RuntimeException {
	private String meassage;
	

}
