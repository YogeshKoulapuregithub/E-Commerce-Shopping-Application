package com.yogesh.ecom.utility;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.yogesh.ecom.exception.AddressNotFoundById;
import com.yogesh.ecom.exception.AlreadyHaveAddressException;
import com.yogesh.ecom.exception.ContactAlreadtExistsException;
import com.yogesh.ecom.exception.IllegalArgumentExceptions;
import com.yogesh.ecom.exception.InvalidCredentialException;
import com.yogesh.ecom.exception.InvalidEmailException;
import com.yogesh.ecom.exception.InvalidIUserRoleSpecifiedException;
import com.yogesh.ecom.exception.InvaliodCredentionalException;
import com.yogesh.ecom.exception.OptExpiredException;
import com.yogesh.ecom.exception.OtpInvalidException;
import com.yogesh.ecom.exception.ProductNotFoundByID;
import com.yogesh.ecom.exception.RegistrationSessionExpiredException;
import com.yogesh.ecom.exception.UserIsNotLoggedInException;
import com.yogesh.ecom.exception.UserNameNotFoundException;
import com.yogesh.ecom.exception.UserTokenBlockedStateException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestControllerAdvice
public class ApplicationHandler {
	private ErrorStructure<String> errorStructure;

	public ResponseEntity<ErrorStructure<String>> erroeResponce(HttpStatus code,String message,String rootCause)
	{
		return ResponseEntity.ok(errorStructure.setErrorStatuscode(code.value())
				.setErrorMessage(message)
				.setRootCause(rootCause));
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleInvalidEmailException(InvalidEmailException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST,e.getMessage(),"Email Id Already Exists in Database");  
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleInvalidUserRole(InvalidIUserRoleSpecifiedException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST,e.getMessage(), "Invalid UserRole Specified Please Specify valid user Role");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleInvalidCredentionalException(InvaliodCredentionalException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(), "invalid Credentional");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleOtpException(OptExpiredException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(), "Otp Expired so please Resend Otp..!!");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleOtpInvalidException(OtpInvalidException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(),"Invalid OTP So please enter valid otp");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleRegistartionSessionException(RegistrationSessionExpiredException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(), "Registration Session Exception");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserNameNotFoundException(UserNameNotFoundException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(), "User name is not found ");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleIllegalArgumentExceptions(IllegalArgumentExceptions e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST,e.getMessage(), "User is not logged In");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserTokenBlockedStateException(UserTokenBlockedStateException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST,e.getMessage(), "User is not logged In");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleAlreadyHaveAddressException(AlreadyHaveAddressException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(), "Seller is already have address can't add");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleAddressNotFoundById(AddressNotFoundById e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(), "Address not found by Id");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleUserIsNotLoggedInException(UserIsNotLoggedInException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(), "user is not logged in ...");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleContactAlreadtExistsException(ContactAlreadtExistsException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(), "cannot add more then 2 contacts so please add below 2 numbers of contacts..");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleProductNotFoundByID(ProductNotFoundByID e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(), "product not found by id");
	}
	@ExceptionHandler
	public ResponseEntity<ErrorStructure<String>> handleInvalidCredentialException(InvalidCredentialException e)
	{
		return erroeResponce(HttpStatus.BAD_REQUEST, e.getMessage(), "ImageType is Invalid");
	}


}
