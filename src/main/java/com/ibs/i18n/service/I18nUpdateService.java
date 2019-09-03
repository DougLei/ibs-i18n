package com.ibs.i18n.service;

import java.util.List;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.data.validator.table.UniqueValidationResult;
import com.douglei.orm.sessionfactory.sessions.Session;
import com.douglei.tools.utils.Collections;
import com.ibs.dynamic.table.DynamicTableIndexContext;
import com.ibs.i18n.entity.I18nMessage;
import com.ibs.parent.code.service.BasicService;
import com.ibs.parent.code.service.validator.SValidator;
import com.ibs.parent.code.validator.DataValidationResult;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nUpdateService extends BasicService{
	private static final ValidateCodeAndLanguageUniqueWhenAdd validateCodeAndLanguageUniqueWhenAdd = new ValidateCodeAndLanguageUniqueWhenAdd();
	private static final ValidateCodeAndLanguageUniqueWhenUpdate validateCodeAndLanguageUniqueWhenUpdate = new ValidateCodeAndLanguageUniqueWhenUpdate();

	/**
	 * 添加国际化消息
	 * @param message
	 */
	@Transaction
	public void insert(I18nMessage message) {
		if(validateByValidator(message, validateCodeAndLanguageUniqueWhenAdd) == DataValidationResult.SUCCESS) {
			SessionContext.getTableSession().save(message);
		}
	}
	
	/**
	 * 批量添加国际化消息
	 * @param messages
	 */
	@Transaction
	public void insert(List<I18nMessage> messages) {
		if(validateByValidator(messages, validateCodeAndLanguageUniqueWhenAdd) == DataValidationResult.SUCCESS) {
			SessionContext.getTableSession().save(messages);
		}
	}
	
	/**
	 * 修改国际化消息
	 * @param message
	 */
	@Transaction
	public void update(I18nMessage message) {
		if(validateByValidator(message, validateCodeAndLanguageUniqueWhenUpdate) == DataValidationResult.SUCCESS) {
			SessionContext.getTableSession().update(message);
		}
	}
	
	/**
	 * 批量修改国际化消息
	 * @param messages
	 */
	@Transaction
	public void update(List<I18nMessage> messages) {
		if(validateByValidator(messages, validateCodeAndLanguageUniqueWhenUpdate) == DataValidationResult.SUCCESS) {
			SessionContext.getTableSession().update(messages);
		}
	}

	/**
	 * 删除国际化消息
	 * @param ids
	 */
	@Transaction
	public void deleteByIds(String ids) {
		deleteByIds("I18N_MESSAGE_"+DynamicTableIndexContext.getIndex(), "id", ids);
	}
}

// 在添加时验证code和language唯一
class ValidateCodeAndLanguageUniqueWhenAdd implements SValidator<I18nMessage> {
	protected byte getCompareRadix() {
		return 0;
	}
	
	@Override
	public ValidationResult doValidate(I18nMessage i18nMessage, List<I18nMessage> originValidateDatas, Session session, String projectId, String customerId, String databaseId) {
		byte originCount = Byte.parseByte(session.getSqlSession().uniqueQuery_("select count(id) from I18N_MESSAGE_"+DynamicTableIndexContext.getIndex()+" where code=? and language=?", Collections.toList(i18nMessage.getCode(), i18nMessage.getLanguage()))[0].toString());
		if(originCount  > getCompareRadix()) {
			return new UniqueValidationResult("code,language", "["+i18nMessage.getCode()+", "+i18nMessage.getLanguage()+"]");
		}
		return null;
	}
}

// 在修改时验证code和language唯一
class ValidateCodeAndLanguageUniqueWhenUpdate extends ValidateCodeAndLanguageUniqueWhenAdd {
	@Override
	protected byte getCompareRadix() {
		return 1;
	}
}

