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
import com.ibs.IbsI18nConfigurationProperties;
import com.ibs.code.service.file.CreateFileException;
import com.ibs.code.service.file.DownloadFileException;
import com.ibs.code.service.file.FileService;
import com.ibs.components.filters.request.header.RequestHeaderContext;
import com.ibs.components.response.ResponseContext;
import com.ibs.i18n.I18nUtil;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nFileService extends FileService{
	private static final String fileContentQuerySql = "select code, message from %s where language=?";
	private static final char file_start = '{';
	private static final String file_end = "\"EOF\":\"-1\"}";
	
	@Autowired
	private I18nUtil util;
	
	@Autowired
	private IbsI18nConfigurationProperties i18nConfig;
	
	/**
	 * 
	 * @param language
	 * @throws CreateFileException 
	 */
	@Transaction(beginTransaction=false)
	public void createI18nFile(String language) throws CreateFileException {
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
			if(SessionContext.getSqlSession().uniqueQuery_("select distinct language from " + util.i18nMessageTableName() + " where language=?", Arrays.asList(language)).length == 1) {
				languages = new String[] {language};
			}
		}
		if(languages == null) {
			// TODO 这块的提示信息 projectId, 后续换成project name
			ResponseContext.addValidation("在项目[%s]中, 不存在language为[%s]的国际化数据", "ibs.i18n.unexists.language", RequestHeaderContext.getTokenEntity().getProjectId(), language);
			return;
		}
		
		String querySql = String.format(fileContentQuerySql, util.i18nMessageTableName());
		List<Object> parameters = new ArrayList<Object>(1);
		StringBuilder content = new StringBuilder(1024);
		FileBufferedWriter writer = new FileBufferedWriter();
		for (String language_ : languages) {
			if(!parameters.isEmpty()) 
				parameters.clear(); 
			parameters.add(language_);
			createI18nFile(querySql, parameters, content, writer);
		}
		addSingleResponseData("language", Arrays.toString(languages));
	}
	
	// 创建i18n文件
	private void createI18nFile(String querySql, List<Object> parameters, StringBuilder content, FileBufferedWriter writer) throws CreateFileException {
		PageResult<Map<String, Object>> result = SessionContext.getSqlSession().pageQuery(1, i18nConfig.getDownloadQueryCount(), querySql, parameters);
		if(result.getCount() > 0) {
			writer.setTargetFile(i18nConfig.getFile(RequestHeaderContext.getTokenEntity().getProjectId(), parameters.get(0).toString()));
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
				throw new CreateFileException(writer.getTargetFile(), e);
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
		download(response, i18nConfig.getDownloadFile(RequestHeaderContext.getTokenEntity().getProjectId(), language));
	}
}
