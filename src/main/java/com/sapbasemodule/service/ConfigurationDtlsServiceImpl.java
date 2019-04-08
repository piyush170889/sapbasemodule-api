package com.sapbasemodule.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sapbasemodule.domain.ConfigurationDtls;
import com.sapbasemodule.model.BaseWrapper;
import com.sapbasemodule.persitence.ConfigurationDtlsRepository;

@Service
@Transactional(rollbackFor = Throwable.class)
public class ConfigurationDtlsServiceImpl implements ConfigurationDtlsService {

	@Autowired
	private ConfigurationDtlsRepository configurationDtlsRepository;

	@Override
	public BaseWrapper updateConfigValue(ConfigurationDtls request) {

		int configDtlsId = configurationDtlsRepository.findByConfigurationName(request.getConfigurationName())
				.getConfigurationDtlsId();
		
		configurationDtlsRepository.updateConfigurationValue(configDtlsId, request.getConfigurationValue());
		
		return new BaseWrapper();
	}
}
