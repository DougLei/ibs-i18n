package com.ibs.i18n.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.transaction.component.TransactionComponent;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nQueryService {
	
	@Autowired
	private I18nMessageTableService tableService;
	
	
}
