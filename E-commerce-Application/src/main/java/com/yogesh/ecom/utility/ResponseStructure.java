package com.yogesh.ecom.utility;

import org.springframework.stereotype.Component;

@Component
public class ResponseStructure<T> {
	private int status;
	private String message;
	private T data;
	public int getStatuscode() {
		return status;
	}
	public ResponseStructure<T> setStatuscode(int statuscode) {
		this.status = statuscode;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ResponseStructure<T> setMessage(String message) {
		this.message = message;
		return this;
	}
	public T getData() {
		return data;
	}
	public ResponseStructure<T> setData(T data) {
		this.data = data;
		return this;
	}


}
