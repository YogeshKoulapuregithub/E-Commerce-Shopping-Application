package com.yogesh.ecom.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.yogesh.ecom.exception.UserNameNotFoundException;
import com.yogesh.ecom.repository.UserRepository;

import lombok.AllArgsConstructor;
@Service
@AllArgsConstructor
public class CustumUserDetailService implements UserDetailsService{
   
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(username).map(user -> new CustumUserDetails(user))
				.orElseThrow(()-> new UserNameNotFoundException("User not found.."));
	}

}
