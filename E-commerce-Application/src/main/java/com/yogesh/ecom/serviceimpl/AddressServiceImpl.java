package com.yogesh.ecom.serviceimpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yogesh.ecom.enums.UserRole;
import com.yogesh.ecom.exception.AddressNotFoundById;
import com.yogesh.ecom.exception.AddressNotFoundByIdException;
import com.yogesh.ecom.exception.AddressNotFoundByUser;
import com.yogesh.ecom.exception.AlreadyHaveAddressException;
import com.yogesh.ecom.exception.ContactAlreadtExistsException;
import com.yogesh.ecom.exception.ContactLimitOverFlowException;
import com.yogesh.ecom.exception.ContactNotFoundById;
import com.yogesh.ecom.exception.UserIsNotLoggedInException;
import com.yogesh.ecom.jwt.JwtService;
import com.yogesh.ecom.model.Address;
import com.yogesh.ecom.model.Contacts;
import com.yogesh.ecom.model.Customer;
import com.yogesh.ecom.model.Seller;
import com.yogesh.ecom.repository.AddressReposatory;
import com.yogesh.ecom.repository.ContactReposatory;
import com.yogesh.ecom.repository.CustomerRepository;
import com.yogesh.ecom.repository.SellerRepository;
import com.yogesh.ecom.repository.UserRepository;
import com.yogesh.ecom.requestDto.AddressRequst;
import com.yogesh.ecom.requestDto.ContactRequst;
import com.yogesh.ecom.responseDto.AddressResponse;
import com.yogesh.ecom.responseDto.ContactResponse;
import com.yogesh.ecom.service.AddressService;
import com.yogesh.ecom.utility.ResponseStructure;
import com.yogesh.ecom.utility.SimpleResponseStructure;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AddressServiceImpl implements AddressService{
	private AddressReposatory addressReposatory;
	private JwtService jwtService;
	private UserRepository userRepository;
	private SellerRepository sellerRepository;
	private CustomerRepository customerRepository;
	private ResponseStructure<AddressResponse> responseStructure;
	private ResponseStructure<ContactResponse> responseStructure2;
	private ContactReposatory contactReposatory;


	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> addAddress(AddressRequst addressRequst,
			String accessToken) 
	{
		//String userName = jwtService.getuserName("yogikoulapure5");
		Optional<Address> optional = userRepository.findByUserName("yogikoulapure5").map(user->{
			//System.out.println(userName);
			Address address=null;

			if(user.getUserRole().equals(UserRole.SELLER))
			{
				if(((Seller)user).getAddress()==null)
					throw new AlreadyHaveAddressException("Seller is already have a address can't add ");
				address=addressReposatory.save(mapTOAddress(new Address(),addressRequst));
				((Seller)user).setAddress(address);
				sellerRepository.save(((Seller)user));
			}
			else if (user.getUserRole().equals(UserRole.CUSTOMER))
			{
				if(((Customer)user).getAddress().size()>=5)
					throw new AlreadyHaveAddressException("Custumer cant have more then 5 address");
				address=addressReposatory.save(mapTOAddress(new Address(),addressRequst));
				((Customer)user).getAddress().add(address);
				customerRepository.save(((Customer)user));
			}
			return address;
		});

		return ResponseEntity.ok(new ResponseStructure<AddressResponse>()
				.setStatuscode(HttpStatus.OK.value())
				.setMessage("Address Added")
				.setData(mapToAddressResponse(optional.get())));

	}


	private AddressResponse mapToAddressResponse(Address address) {
		List<ContactResponse> mapToContactResponse=null;
		if(address.getContacts()!=null)
		{
			mapToContactResponse=mapToContactResponse(address.getContacts());
		}
		return AddressResponse.builder().addressId(address.getAddressId())
				.state(address.getState())
				.city(address.getCity())
				.pincode(address.getPincode())
				.streetAddress(address.getStreetAddress())
				.addressType(address.getAddressType())
				.streetAddressAdditional(address.getStreetAddressAdditional())
				.contacts(mapToContactResponse).build();
	}



	private List<ContactResponse> mapToContactResponse(List<Contacts> contacts) {
		List<ContactResponse> contactResponses=new ArrayList<>();

		for(Contacts contact:contacts)
		{
			contactResponses.add(mapToContactResponse(contact));
		}
		return contactResponses;

	}


	private ContactResponse mapToContactResponse(Contacts list) {

		return ContactResponse.builder()
				.contactId(list.getContactId())
				.name(list.getName())
				.phoneNumber(list.getPhoneNumber())
				.email(list.getEmail())
				.priority(list.getPriority())
				.build();
	}


	private Address mapTOAddress(Address address, AddressRequst addressRequst) 
	{
		address.setAddressType(addressRequst.getAddressType());
		address.setCity(addressRequst.getCity());
		address.setState(addressRequst.getState());
		address.setPincode(addressRequst.getPincode());
		address.setStreetAddress(addressRequst.getStreetAddress());
		address.setStreetAddressAdditional(addressRequst.getStreetAddressAdditional());
		return address;
	}


	@Override
	public ResponseEntity<ResponseStructure<AddressResponse>> updateAddress(AddressRequst addressRequst,
			int addressId) {
		Address address2=addressReposatory.findById(addressId).map(address->{
			return addressReposatory.save(mapTOAddress(address, addressRequst));
		}).orElseThrow(()-> new AddressNotFoundById("Address not found By ID"));
		return ResponseEntity.ok(new ResponseStructure<AddressResponse>()
				.setStatuscode(HttpStatus.OK.value())
				.setMessage("Address Updated")
				.setData(mapToAddressResponse(address2)));
	}
	private List<AddressResponse> mappToAddressResponseList(List<Address> addresses)
	{

		List<AddressResponse> addressResponses=new ArrayList<>();
		for(Address address: addresses)
		{
			addressResponses.add(mapToAddressResponse(address));
		}
		return addressResponses;

	}
	@Override
	public ResponseEntity<ResponseStructure<List<AddressResponse>>> fondAddressByUser(String accessToken,String refreshToken) {
		if(accessToken==null || refreshToken==null)
			throw new UserIsNotLoggedInException("user is not logged in");

		String userName = jwtService.getuserName(refreshToken);
		List<AddressResponse> address=userRepository.findByUserName(userName).map(user->{
			List<AddressResponse> addressList=new ArrayList<>();

			if(user instanceof Seller)
			{
				Seller seller=(Seller) user;
				addressList.add(mapToAddressResponse(seller.getAddress()));
			}
			if(user instanceof Customer)
			{
				Customer customer=(Customer) user;
				for (Address add : customer.getAddress()) {
					addressList.add(mapToAddressResponse(add));
				}
			}
			return addressList;
		}).orElseThrow(()-> new UsernameNotFoundException("User not found"));

		return ResponseEntity.ok(new ResponseStructure<List<AddressResponse>>()
				.setStatuscode(HttpStatus.OK.value())
				.setMessage("List of Addresss :")
				.setData(address));	
	}






}
