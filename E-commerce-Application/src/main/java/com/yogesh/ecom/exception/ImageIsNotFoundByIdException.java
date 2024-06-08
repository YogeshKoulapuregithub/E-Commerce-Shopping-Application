package com.yogesh.ecom.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImageIsNotFoundByIdException extends RuntimeException {
      private String message;
}
