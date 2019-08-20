package com.ibs.i18n.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.ibs.i18n.entity.I18nMessage;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nUpdateService {

	@Autowired
	private I18nMessageTableService tableService;

	/**
	 * 添加国际化消息
	 * @param messages
	 */
	@Transaction
	public void adds(List<I18nMessage> messages) {
		messages.forEach(message -> {
			SessionContext.getTableSession().save(message);
		});
	}
}
