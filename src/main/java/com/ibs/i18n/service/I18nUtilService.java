package com.ibs.i18n.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibs.dynamic.table.DynamicTableConfigurationProperties;
import com.ibs.dynamic.table.DynamicTableIndexContext;
import com.ibs.parent.code.service.file.SystemFileService;

/**
 * 
 * @author DougLei
 */
@Service
public class I18nUtilService extends SystemFileService{
	
	@Autowired
	private DynamicTableConfigurationProperties config;
	
	/**
	 * I18nMessage表名
	 * @return
	 */
	protected String i18nMessageTableName() {
		return config.getMappingCodes()[0] + DynamicTableIndexContext.getIndex();
	}
}
