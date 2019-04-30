package com.sapbasemodule.service;

import java.util.Map;

import com.sapbasemodule.model.BaseWrapper;

public interface MasterService {

	Map<String, Object> getMasterData();

	BaseWrapper doGetItems();

}
