package com.yogesh.ecom.responseDto;

import org.springframework.stereotype.Component;

import com.yogesh.ecom.enums.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class UserResponse {
	private int userId;
	private String displayName;
	private String userName;
	private String email;
	private UserRole userRole;
	private boolean isEmailVerified;
	private boolean isDeleted;

}
