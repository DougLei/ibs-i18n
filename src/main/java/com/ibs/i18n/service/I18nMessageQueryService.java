package com.ibs.i18n.service;

import java.util.ArrayList;
import java.util.List;

import com.douglei.i18n.I18nMessage;
import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.tools.utils.Collections;
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
		List<Object> parameters = new ArrayList<Object>(codes.length);
		StringBuilder query = new StringBuilder(100);
		query.append("select id, code, language, message, priority from ").append(I18nProperties.getI18nMessageTableName()).append(" where ");
		if(codes.length == 1) {
			query.append("code=?");
			parameters.add(codes[0]);
		}else {
			query.append("code in(");
			for (byte i = 0; i < codes.length; i++) {
				query.append("?");
				if(i < codes.length-1) {
					query.append(",");
				}
				parameters.add(codes[i]);
			}
			query.append(")");
		}
		
		SessionContext.getSqlSession().query(I18nMessage.class, "", Collections.toList(codes));
		
		
		
		
	}
}
