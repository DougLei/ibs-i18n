package com.ibs.i18n.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.mapping.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.sessions.Session;
import com.douglei.orm.sessionfactory.validator.table.UniqueValidationResult;
import com.ibs.code.entity.BasicProperty;
import com.ibs.code.result.DataValidationResult;
import com.ibs.code.service.BasicService;
import com.ibs.code.service.ServiceValidator;
import com.ibs.components.filters.dynamic.table.DynamicTableIndexContext;
import com.ibs.i18n.I18nUtil;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nMessageUpdateService extends BasicService{
	private BasicProperty[] basicPropertiesOnSave = {BasicProperty.CREATE_USER_ID, BasicProperty.CREATE_DATE, BasicProperty.LAST_UPDATE_USER_ID, BasicProperty.LAST_UPDATE_DATE};
	private BasicProperty[] basicPropertiesOnUpdate = {BasicProperty.LAST_UPDATE_USER_ID, BasicProperty.LAST_UPDATE_DATE};
	private CodeAndLanguageUniqueWhenAddValidator codeAndLanguageUniqueWhenAddValidator = new CodeAndLanguageUniqueWhenAddValidator();
	private ServiceValidator<Map<String, Object>> codeAndLanguageUniqueWhenUpdateValidator = new CodeAndLanguageUniqueWhenUpdateValidator();
	
	@Autowired
	private I18nUtil util;
	
	/**
	 * 
	 * @param message
	 */
	@Transaction
	public void insert(Map<String, Object> message) {
		if(validateByValidator(message, codeAndLanguageUniqueWhenAddValidator) == DataValidationResult.SUCCESS) {
			tableSessionSave(util.i18nMessageTableName(), message, basicPropertiesOnSave);
		}
	}
	
	/**
	 * 
	 * @param messages
	 */
	@Transaction
	public void insert(List<Map<String, Object>> messages) {
		if(validateByValidator(messages, codeAndLanguageUniqueWhenAddValidator) == DataValidationResult.SUCCESS) {
			tableSessionSave(util.i18nMessageTableName(), messages, basicPropertiesOnSave);
		}
	}
	
	/**
	 * 
	 * @param message
	 */
	@Transaction
	public void update(Map<String, Object> message) {
		if(validateByValidator(message, codeAndLanguageUniqueWhenUpdateValidator) == DataValidationResult.SUCCESS) {
			tableSessionUpdate(util.i18nMessageTableName(), message, basicPropertiesOnUpdate);
		}
	}
	
	/**
	 * 
	 * @param messages
	 */
	@Transaction
	public void update(List<Map<String, Object>> messages) {
		if(validateByValidator(messages, codeAndLanguageUniqueWhenUpdateValidator) == DataValidationResult.SUCCESS) {
			tableSessionUpdate(util.i18nMessageTableName(), messages, basicPropertiesOnUpdate);
		}
	}

	/**
	 * 
	 * @param ids
	 */
	@Transaction
	public void deleteByIds(String ids) {
		deleteByIds(util.i18nMessageTableName(), ids);
	}
}

//在添加时验证code和language唯一
class CodeAndLanguageUniqueWhenAddValidator implements ServiceValidator<Map<String, Object>> {
	@Override
	public ValidationResult validate(short index, Map<String, Object> message, Session session, String projectId, String customerId, String databaseId) {
		byte originCount = Byte.parseByte(session.getSqlSession().uniqueQuery_("select count(id) from I18N_MESSAGE_"+DynamicTableIndexContext.getCurrentTableIndex()+" where code=? and language=?", Arrays.asList(message.get("CODE"), message.get("LANGUAGE")))[0].toString());
		if(originCount  > 0) {
			return new UniqueValidationResult("CODE,LANGUAGE");
		}
		return null;
	}
}

// 在修改时验证code和language唯一
class CodeAndLanguageUniqueWhenUpdateValidator implements ServiceValidator<Map<String, Object>> {
	@Override
	public ValidationResult validate(short index, Map<String, Object> message, Session session, String projectId, String customerId, String databaseId) {
		Object[] objs = session.getSqlSession().uniqueQuery_("select id, priority from I18N_MESSAGE_"+DynamicTableIndexContext.getCurrentTableIndex()+" where code=? and language=?", Arrays.asList(message.get("CODE"), message.get("LANGUAGE")));
		if(objs.length > 0) {
			int id = Integer.parseInt(objs[0].toString());
			if(id != Integer.parseInt(message.get("ID").toString())) {
				return new UniqueValidationResult("CODE,LANGUAGE");
			}
			message.put("PRIORITY", objs[1]);
		}
		return null;
	}
}
