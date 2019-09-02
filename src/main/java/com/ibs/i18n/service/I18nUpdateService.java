package com.ibs.i18n.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.metadata.validator.ValidationResult;
import com.douglei.orm.sessionfactory.data.validator.table.UniqueValidationResult;
import com.douglei.orm.sessionfactory.sessions.Session;
import com.douglei.tools.utils.Collections;
import com.ibs.dynamic.table.DynamicTable;
import com.ibs.dynamic.table.DynamicTableContext;
import com.ibs.i18n.entity.I18nMessage;
import com.ibs.parent.code.service.BasicService;
import com.ibs.parent.code.service.validator.DataValidator2DB;
import com.ibs.parent.code.validator.DataValidationResult;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nUpdateService extends BasicService{
	private static final CodeAndLanguageUniqueValidate codeAndLanguageUniqueValidate = new CodeAndLanguageUniqueValidate();

	@Autowired
	private I18nMessageTableService service;
	
	/**
	 * 添加国际化消息
	 * @param message
	 */
	@Transaction
	public void add(I18nMessage message) {
		if(validate(message, codeAndLanguageUniqueValidate) == DataValidationResult.SUCCESS) {
			service.dynamicTableExists(true);
			SessionContext.getTableSession().save(message);
		}
	}
	
	/**
	 * 批量添加国际化消息
	 * @param messages
	 */
	@Transaction
	public void adds(List<I18nMessage> messages) {
		if(validate(messages, codeAndLanguageUniqueValidate) == DataValidationResult.SUCCESS) {
			service.dynamicTableExists(true);
			SessionContext.getTableSession().save(messages);
		}
	}
}

// 验证code和language唯一
class CodeAndLanguageUniqueValidate implements DataValidator2DB<I18nMessage>{
	@Override
	public ValidationResult doValidate(I18nMessage i18nMessage, List<I18nMessage> originValidateDatas, Session session, String projectId, String customerId, String databaseId) {
		DynamicTable dt = DynamicTableContext.getDynamicTable();
		if(!dt.byNew()) {
			byte count = Byte.parseByte(session.getSqlSession().uniqueQuery_("select count(id) from I18N_MESSAGE_"+dt.getTableIndex()+" where code=? and language=?", Collections.toList(i18nMessage.getCode(), i18nMessage.getLanguage()))[0].toString());
			if(count > 0) {
				return new UniqueValidationResult("code,language", "["+i18nMessage.getCode()+", "+i18nMessage.getLanguage()+"]");
			}
		}
		return null;
	}
}

