package com.ibs.i18n.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.tools.instances.file.writer.FileBufferedWriter;
import com.douglei.tools.utils.Collections;
import com.ibs.IbsI18nConfigurationProperties;
import com.ibs.parent.code.service.file.CreateSystemFileException;
import com.ibs.parent.code.service.file.DownloadFileException;
import com.ibs.parent.code.service.file.FileService;
import com.ibs.response.ResponseContext;
import com.ibs.token.TokenContext;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nFileService extends FileService{
	private static final String fileContentQuerySql = "select code, message from %s where language=?";
	private static final char file_start = '{';
	private static final String file_end = "\"\":\"\"}";
	
	@Autowired
	private I18nUtilService util;
	
	@Autowired
	private IbsI18nConfigurationProperties i18nConfig;
	
	/**
	 * 
	 * @param language
	 * @throws CreateSystemFileException 
	 */
	@Transaction(beginTransaction=false)
	public void createI18nFile(String language) throws CreateSystemFileException {
		String[] languages = null;
		if(language.equalsIgnoreCase("all")) {
			List<Object[]> tmpLanguages = SessionContext.getSqlSession().query_("select distinct language from " + util.i18nMessageTableName());
			if(tmpLanguages.size() > 0) {
				languages = new String[tmpLanguages.size()];
				for (int i = 0; i < languages.length; i++) {
					languages[i] = tmpLanguages.get(i)[0].toString();
				}
			}
		}else {
			if(SessionContext.getSqlSession().uniqueQuery_("select distinct language from " + util.i18nMessageTableName() + " where language=?", Collections.toList(language)).length == 1) {
				languages = new String[] {language};
			}
		}
		if(languages == null) {
			ResponseContext.addValidation(null, null, "在项目["+TokenContext.getToken().getProjectId()+"]中, 不存在language为["+language+"]的国际化message数据", "ibs.i18n.non-existent.language", TokenContext.getToken().getProjectId(), language);
			return;
		}
		
		String querySql = String.format(fileContentQuerySql, util.i18nMessageTableName());
		List<Object> parameters = new ArrayList<Object>(1);
		StringBuilder content = new StringBuilder(1024);
		FileBufferedWriter writer = new FileBufferedWriter();
		for (String language_ : languages) {
			if(parameters.size() > 0) parameters.clear(); parameters.add(language_);
			createI18nFile(querySql, parameters, content, writer, language_);
		}
		addSingleResponseData("language", Arrays.toString(languages));
	}
	
	// 创建i18n文件
	private void createI18nFile(String querySql, List<Object> parameters, StringBuilder content, FileBufferedWriter writer, String language) throws CreateSystemFileException {
		PageResult<Map<String, Object>> result = SessionContext.getSqlSession().pageQuery(1, i18nConfig.getDownloadQueryCount(), querySql, parameters);
		if(result.getCount() > 0) {
			writer.setFile(i18nConfig.getFile(TokenContext.getToken().getProjectId(), language));
			try {
				writer.write(file_start);
				while(true) {
					result.getResultDatas().forEach(data -> content.append("\"").append(data.get("CODE")).append("\":\"").append(data.get("MESSAGE")).append("\","));
					writer.write(content.toString());
					content.setLength(0);
					if(result.isLastPage()) {
						break;
					}
					result = SessionContext.getSqlSession().pageQuery(result.getPageNum()+1, i18nConfig.getDownloadQueryCount(), querySql, parameters);
				}
				writer.write(file_end);
			} catch (IOException e) {
				throw new CreateSystemFileException(writer.getTargetFile(), e);
			} finally {
				writer.close();
			}
		}
	}
	
	/**
	 * 
	 * @param language
	 * @param response 
	 * @throws DownloadFileException 
	 */
	public void downloadByLanguage(String language, HttpServletResponse response) throws DownloadFileException {
		download(response, i18nConfig.getDownloadFile(TokenContext.getToken().getProjectId(), language));
	}
}
