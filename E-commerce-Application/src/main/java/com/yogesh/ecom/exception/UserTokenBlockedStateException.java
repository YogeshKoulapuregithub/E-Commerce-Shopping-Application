package com.yogesh.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTokenBlockedStateException extends RuntimeException {
	String message;

}
