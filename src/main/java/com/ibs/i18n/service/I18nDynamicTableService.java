package com.ibs.i18n.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.ibs.dynamic.table.DynamicTableService;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nDynamicTableService {

	@Autowired
	private DynamicTableService service;
	
	/**
	 * 系统启动时加载所有满足条件的动态表映射信息
	 */
	@Transaction
	public synchronized void start() {
		service.start(I18nProperties.name, I18nProperties.i18nMessageTableTemplate);
	}
	
	/**
	 * 初始化指定项目的动态表
	 */
	@Transaction
	public synchronized void initial() {
		service.initial(I18nProperties.name, I18nProperties.i18nMessageTableTemplate);
	}
	
	/**
	 * 销毁指定项目的动态表
	 */
	@Transaction
	public synchronized void destroy() {
		service.destroy(I18nProperties.name, I18nProperties.i18nMessageTableCode);
	}
}
