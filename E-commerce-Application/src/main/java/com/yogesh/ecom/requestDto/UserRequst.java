package com.yogesh.ecom.requestDto;

import org.springframework.stereotype.Component;

import com.yogesh.ecom.enums.UserRole;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserRequst {
	
	@NotNull(message = "Fill the Username")
    private String displayName;
	@Email(regexp = "[a-z 0-9]+@a-z0-9.-]+\\.[a-z]{2,3}")
	@NotNull(message = "Invalid Email")
	private String email;
	@NotBlank(message = "Password is required")
	@NotNull(message = "Password is required")
	@Size(min = 8,max = 20,message = "Password must be between 8 to 20 Charecters")
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]) (?=\\S+$).{8,}$",
	message = "Password must" +" contains atleast one latter,one number, one special charecter")
	private String password;
	private UserRole userRole;
	

}
