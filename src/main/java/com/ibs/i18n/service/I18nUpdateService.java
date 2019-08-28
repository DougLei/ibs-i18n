package com.ibs.i18n.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.ibs.i18n.entity.I18nMessage;
import com.ibs.parent.code.service.BasicService;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nUpdateService extends BasicService{

	@Autowired
	private I18nMessageTableService tableService;

	/**
	 * 添加国际化消息
	 * @param message
	 */
	@Transaction
	public void add(I18nMessage message) {
		if(validateInDB(message) != null) {
			SessionContext.getTableSession().save(message);
		}
	}
	
	/**
	 * 批量添加国际化消息
	 * @param messages
	 */
	@Transaction
	public void adds(List<I18nMessage> messages) {
		if(validateInDB(messages) != null) {
			SessionContext.getTableSession().save(messages);
		}
	}
}
