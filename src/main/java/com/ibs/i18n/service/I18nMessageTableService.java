package com.ibs.i18n.service;

import com.douglei.orm.configuration.environment.mapping.MappingType;
import com.douglei.orm.sessionfactory.dynamic.mapping.DynamicMapping;
import com.douglei.tools.instances.reader.ResourcesReader;
import com.ibs.dynamic.table.DynamicTableService;

/**
 * 
 * @author DougLei
 */
public class I18nMessageTableService extends DynamicTableService{
	private static final String i18nMessageTable_Template = new ResourcesReader("mappings/I18nMessage.tmp.xml.template").readAll(1500).toString();// 表映射模板

	@Override
	protected DynamicMapping[] getDynamicTableMapping(byte tableIndex) {
		return new DynamicMapping[] {new DynamicMapping(MappingType.TABLE, String.format(i18nMessageTable_Template, tableIndex))};
	}
}
