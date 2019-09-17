package com.ibs.i18n.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibs.components.filters.dynamic.table.DynamicTableConfigurationProperties;
import com.ibs.components.filters.dynamic.table.DynamicTableIndexContext;

/**
 * 
 * @author DougLei
 */
@Service
public class I18nUtilService {
	
	@Autowired
	private DynamicTableConfigurationProperties config;
	
	/**
	 * I18nMessage表名
	 * @return
	 */
	public String i18nMessageTableName() {
		return config.getMappingCodes()[0] + DynamicTableIndexContext.getIndex();
	}
}
