package com.ibs.i18n.service;

import org.springframework.stereotype.Component;

import com.ibs.components.filters.dynamic.table.DynamicTableIndexContext;

/**
 * 
 * @author DougLei
 */
@Component
public class I18nUtilService {
	private static final String[] MAPPING_CODES = {"I18N_MESSAGE_"};
	
	/**
	 * I18nMessage表名
	 * @return
	 */
	public String i18nMessageTableName() {
		return MAPPING_CODES[0] + DynamicTableIndexContext.getIndex();
	}
	
	/**
	 * 获取映射的code数组
	 * @return
	 */
	public String[] getMappingCodes() {
		return MAPPING_CODES;
	}
}
