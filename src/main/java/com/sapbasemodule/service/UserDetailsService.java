package com.sapbasemodule.service;

import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sapbasemodule.configuration.CustomUserDetails;
import com.sapbasemodule.domain.RoleMasterDtl;
import com.sapbasemodule.domain.UserLoginDtl;
import com.sapbasemodule.persitence.UserLoginDtlRepository;

@Service
@Qualifier("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserLoginDtlRepository userLoginDtlRepository;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		try {
//			System.out.println("username = " + username);
			
			UserLoginDtl userLoginDtl = userLoginDtlRepository.findByContactNum(username);	//Find User
			
			if (null == userLoginDtl) {	//Validate User Object
				System.out.println("User is null. User with ID - " + username + " not found.");
				throw new UsernameNotFoundException("User " + username + " is not registered.");
			}
			
			CustomUserDetails userDetails = new CustomUserDetails();
			BeanUtils.copyProperties(userLoginDtl, userDetails);	//Copy properties of User Object to UserCustomDetails Object
			userDetails.setUserDtl(userLoginDtl.getUserDtl());
			Set<RoleMasterDtl> roles = userLoginDtl.getRoles();
			userDetails.setRoles(roles);
			
//			System.out.println(userDetails.toString());
			return userDetails;
		} catch (Exception e) {
			//TODO: Log Exception
			e.printStackTrace();
			throw new UsernameNotFoundException("Some Error Occured. Please contact support");
		}
	}

}
