package com.sapbasemodule.service;

import com.sapbasemodule.domain.NormalPacketDescDtls;
import com.sapbasemodule.domain.SiteDtls;
import com.sapbasemodule.exception.ServicesException;
import com.sapbasemodule.model.BaseWrapper;

public interface LocationMgmtService {

	BaseWrapper doUpdateUserLocation(NormalPacketDescDtls request) throws Exception;

	BaseWrapper doAddUpdateSiteLocation(SiteDtls request) throws ServicesException;

	BaseWrapper doGetSiteLocations();
}
