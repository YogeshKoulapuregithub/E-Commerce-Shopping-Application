package com.yogesh.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserIsNotLoggedInException extends RuntimeException {
    private String messege;
} 
