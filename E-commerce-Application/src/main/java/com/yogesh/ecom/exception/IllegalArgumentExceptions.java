package com.yogesh.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IllegalArgumentExceptions extends RuntimeException{
	String message;

}
