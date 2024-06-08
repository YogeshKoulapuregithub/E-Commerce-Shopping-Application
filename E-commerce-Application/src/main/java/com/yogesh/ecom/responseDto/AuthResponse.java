package com.yogesh.ecom.responseDto;

import com.yogesh.ecom.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
      private int userId;
      private String username;
      private UserRole userRole;
      private long accesExpiretion;
      private long refreshExpiretion;
}
