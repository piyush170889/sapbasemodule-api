package com.sapbasemodule.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapbasemodule.domain.UserLoginDtl;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.persitence.UserLoginDtlRepository;

@Service
@Transactional(rollbackFor=Throwable.class)
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private UserLoginDtlRepository userLoginDtlRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public BaseWrapper doLoginUserWitValidCreadentials(UserLoginDtl request) throws ServicesException {
		
		String userName = request.getUsername();
		UserLoginDtl userLoginDtl2 = userLoginDtlRepository.findByUsername(userName);
		if(userLoginDtl2 == null) {
			throw new ServicesException("623");
		}else if(userName.equals(userLoginDtl2.getUsername()) && 
			passwordEncoder.matches(request.getPassword(), userLoginDtl2.getPassword())) {
			return new BaseWrapper();
		}else {
			throw new ServicesException("623");
		}
	}

}
