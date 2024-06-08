package com.yogesh.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvaliodCredentionalException extends RuntimeException {
	private String message;

}
