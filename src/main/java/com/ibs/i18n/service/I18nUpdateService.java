package com.ibs.i18n.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.ibs.i18n.entity.I18nMessage;
import com.ibs.response.ResponseContext;

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
		ValidatorHandler handler = SessionContext.getTableSession().getValidatorHandler(I18nMessage.class);
		
		List<I18nMessage> ok_messages = new ArrayList<I18nMessage>(messages.size());
		messages.forEach(message -> {
			VaidationResult result = handler.doValidate(message);
			if(result != null) {
				ResponseContext.addValidation(data, code, params);
				return;
			}
			ok_messages.add(message);
		});
		
		
		ok_messages.forEach(message -> {
			SessionContext.getTableSession().save(message);
		});
	}
}
