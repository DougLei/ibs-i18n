package com.ibs.i18n.service;

import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.ibs.parent.code.service.DownloadService;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nMessageQueryService extends DownloadService{

	/**
	 * 查询指定code, 所有语言的的国际化消息
	 * @param codes
	 */
	@Transaction
	public void queryByCodes(String[] codes) {
		// TODO Auto-generated method stub
		
		
		
		
		
	}
}
