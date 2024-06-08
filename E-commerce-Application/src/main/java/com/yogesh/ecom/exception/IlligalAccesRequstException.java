package com.yogesh.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class IlligalAccesRequstException extends RuntimeException {

	private String message;

}
