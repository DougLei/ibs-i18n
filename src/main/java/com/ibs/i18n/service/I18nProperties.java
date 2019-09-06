package com.ibs.i18n.service;

import com.douglei.tools.instances.reader.ResourcesReader;
import com.ibs.dynamic.table.DynamicTableIndexContext;

/**
 * 
 * @author DougLei
 */
public class I18nProperties {
	
	/**
	 * 项目名
	 */
	public static final String name = "i18n";
	
	/**
	 * i18nMessage 表的code
	 */
	public static final String i18nMessageTableCode = "I18N_MESSAGE_";
	
	/**
	 * i18nMessage tmp.xml模板
	 */
	public static final String i18nMessageTableTemplate = new ResourcesReader("mappings/I18nMessage.tmp.xml.template").readAll(1500).toString();
	
	/**
	 * 获取i18nMessage表名
	 * @return
	 */
	public static final String getI18nMessageTableName() {
		return i18nMessageTableCode + DynamicTableIndexContext.getIndex();
	}
}
