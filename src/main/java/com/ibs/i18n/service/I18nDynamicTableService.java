package com.ibs.i18n.service;

import org.springframework.beans.factory.annotation.Autowired;

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
	private static final String name = "i18n";
	private static final String i18nMessageTableCode = "I18N_MESSAGE_";
	private static final String i18nMessageTableTemplate = new ResourcesReader("mappings/I18nMessage.tmp.xml.template").readAll(1500).toString();
	
	@Autowired
	private DynamicTableService service;
	
	/**
	 * 系统启动时加载所有满足条件的动态表映射信息
	 */
	@Transaction
	public synchronized void start() {
		service.start(name, i18nMessageTableTemplate);
	}
	
	/**
	 * 初始化指定项目的动态表
	 */
	@Transaction
	public synchronized void initial() {
		service.initial(name, i18nMessageTableTemplate);
	}
	
	/**
	 * 销毁指定项目的动态表
	 */
	@Transaction
	public synchronized void destroy() {
		service.destroy(name, i18nMessageTableCode);
	}
}
