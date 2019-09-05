package com.ibs.i18n.service;

import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.tools.instances.reader.ResourcesReader;
import com.ibs.dynamic.table.DynamicTableService;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nDynamicTableService {
	private static final String i18nMessageTableTemplate = new ResourcesReader("mappings/I18nMessage.tmp.xml.template").readAll(1500).toString();// 表映射模板
	
	private DynamicTableService service;
	
	/**
	 * 初始化指定项目的动态表
	 * @param projectId
	 */
	@Transaction
	public synchronized void initial(String projectId) {
		service.initial("i18n", projectId, i18nMessageTableTemplate);
	}
	
	/**
	 * 销毁指定项目的动态表
	 * @param projectId
	 */
	@Transaction
	public synchronized void destroy(String projectId) {
		// TODO Auto-generated method stub
		
	}
}
