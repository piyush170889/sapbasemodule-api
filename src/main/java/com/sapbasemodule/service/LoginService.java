package com.sapbasemodule.service;

import com.sapbasemodule.domain.UserLoginDtl;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.BaseWrapper;

public interface LoginService {

	BaseWrapper doLoginUserWitValidCreadentials(UserLoginDtl userLoginDtl) throws ServicesException;

}
