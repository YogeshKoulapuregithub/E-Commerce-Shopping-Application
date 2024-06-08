package com.yogesh.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InvalidIUserRoleSpecifiedException extends RuntimeException {
    private String message;
}
