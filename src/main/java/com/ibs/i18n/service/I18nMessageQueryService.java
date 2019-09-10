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
public class I18nMessageQueryService {
	
	@Autowired
	private I18nUtilService util;
	
	/**
	 * 
	 * @param codes
	 */
	@Transaction
	public void queryByCodes(String[] codes) {
		List<Object> parameters = new ArrayList<Object>(codes.length);
		StringBuilder querySql = new StringBuilder(100);
		querySql.append("select id, code, language, message, priority from ").append(util.i18nMessageTableName()).append(" where ");
		if(codes.length == 1) {
			parameters.add(codes[0]);
			querySql.append("code=?");
		}else {
			querySql.append("code in(");
			for (byte i = 0; i < codes.length; i++) {
				parameters.add(codes[i]);
				querySql.append("?");
				if(i < codes.length-1) {
					querySql.append(",");
				}
			}
			querySql.append(")");
		}
		ResponseContext.addData(SessionContext.getSqlSession().query(I18nMessage.class, querySql.toString(), parameters));
	}
}
