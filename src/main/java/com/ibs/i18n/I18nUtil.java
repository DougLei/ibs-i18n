package com.ibs.i18n;

import org.springframework.stereotype.Component;

import com.ibs.components.filters.dynamic.table.DynamicTableIndexContext;
import com.ibs.parent.code.service.dynamic.table.DynamicTableUtil;

/**
 * 
 * @author DougLei
 */
@Component
public class I18nUtil extends DynamicTableUtil{
	
	/**
	 * I18nMessage表名
	 * @return
	 */
	public String i18nMessageTableName() {
		return getMappingCodes()[0] + DynamicTableIndexContext.getCurrentTableIndex();
	}
	

	/**
	 * 获取映射的code数组
	 * @return
	 */
	public String[] getMappingCodes() {
		if(mappingCodes == null)
			mappingCodes = new String[]{"I18N_MESSAGE_"};
		return mappingCodes;
	}
	
	/**
	 * 获取映射模板的内容数组
	 * @return
	 */
	public String[] getMappingTemplates() {
		if(mappingTemplates == null) 
			setMappingTemplates("mappings/I18nMessage.tmp.xml.template");
		return mappingTemplates;
	}
}
