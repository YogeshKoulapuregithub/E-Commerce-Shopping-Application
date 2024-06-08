package com.yogesh.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OptExpiredException extends RuntimeException {
	private String message;

}
