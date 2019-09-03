package com.ibs.i18n.service;

import com.douglei.tools.instances.reader.ResourcesReader;

/**
 * 
 * @author DougLei
 */
public class I18nMessageTableService {
	private static final String i18nMessageTable_Template = new ResourcesReader("mappings/I18nMessage.tmp.xml.template").readAll(1500).toString();// 表映射模板

}
