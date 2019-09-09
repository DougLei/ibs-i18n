package com.ibs.i18n.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.orm.core.sql.pagequery.PageResult;
import com.douglei.tools.instances.file.writer.FileBufferedWriter;
import com.ibs.IbsI18nConfigurationProperties;
import com.ibs.parent.code.service.file.CreateSystemFileException;
import com.ibs.parent.code.service.file.DownloadFileException;
import com.ibs.parent.code.service.file.SystemFileService;
import com.ibs.token.TokenContext;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nFileService extends SystemFileService{
	private static final String fileContentQuerySql = "select code, message from %s where language=?";
	
	@Autowired
	private I18nUtilService util;
	
	@Autowired
	private IbsI18nConfigurationProperties i18nConfig;
	
	/**
	 * 
	 * @param language
	 * @param addResponseData 是否添加响应数据
	 * @throws CreateSystemFileException 
	 */
	public void createI18nFile(String language, boolean addResponseData) throws CreateSystemFileException {
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
			languages = new String[] {language};
		}
		if(languages == null) {
			// TODO 没有任何language
			return;
		}
		
		FileBufferedWriter writer = new FileBufferedWriter();
		String querySql = String.format(fileContentQuerySql, util.i18nMessageTableName());
		List<Object> parameters = new ArrayList<Object>(1);
		StringBuilder content = new StringBuilder(1024);
		for (String language_ : languages) {
			if(parameters.size() > 0) parameters.clear(); parameters.add(language_);
			if(content.length() > 0) content.setLength(0);
			createI18nFile(querySql, parameters, content, language_, writer);
		}
		if(addResponseData) addSingleResponseData("language", Arrays.toString(languages));
	}
	
	private void createI18nFile(String querySql, List<Object> parameters, StringBuilder content, String language, FileBufferedWriter writer) throws CreateSystemFileException {
		PageResult<Object[]> result = SessionContext.getSqlSession().pageQuery_(1, i18nConfig.getDownloadQueryCount(), querySql, parameters);
		if(result.getCount() > 0) {
			writer.setFile(createFile(i18nConfig.getDownloadFile(TokenContext.getToken().getProjectId(), language)));
			try {
				writer.write('{');
				while(true) {
					result.getResultDatas().forEach(data -> content.append("\"").append(data[0]).append("\":\"").append(data[1]).append("\","));
					writer.write(content.toString());
					content.setLength(0);
					if(result.isLastPage()) {
						break;
					}
					result = SessionContext.getSqlSession().pageQuery_(result.getPageNum()+1, i18nConfig.getDownloadQueryCount(), querySql, parameters);
				}
				writer.write("\"_end\":\"_end\"");
			} catch (IOException e) {
				throw new CreateSystemFileException(writer.getTargetFile(), e);
			}
		}
	}

	/**
	 * 
	 * @param language
	 * @param response 
	 * @throws DownloadFileException 
	 * @throws CreateSystemFileException 
	 */
	public void downloadByLanguage(String language, HttpServletResponse response) throws DownloadFileException, CreateSystemFileException {
		File file = i18nConfig.getDownloadFile(TokenContext.getToken().getProjectId(), language);
		if(!file.exists()) {
			createI18nFile(language, false);
		}
		download(response, file);
	}
}
