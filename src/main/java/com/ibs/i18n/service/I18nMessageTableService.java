package com.ibs.i18n.service;

import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.tools.instances.reader.ProjectConfigurationResourceReader;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nMessageTableService {
	
	private static final String tableNamePrefix = "I18N_MESSAGE_";// 表名前缀
	private static final String templatePath = "mappings/I18nMessage.tmp.xml.template";// 模板路径
	private static final String tableMappingTemplate = new ProjectConfigurationResourceReader(templatePath).readAll(1360).toString();// 表映射模板
	
	/**
	 * 获得国际化消息表的表名
	 * @param tableIndex
	 * @return
	 */
	public String getName(short tableIndex) {
		return tableNamePrefix + tableIndex;
	}
	
	/**
	 * 获得国际化消息表的表映射内容
	 * @param tableIndex
	 * @return
	 */
	public String getMappingContent(short tableIndex) {
		return String.format(tableMappingTemplate, tableIndex);
	}
}
