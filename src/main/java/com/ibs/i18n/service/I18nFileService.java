package com.ibs.i18n.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.ibs.IbsI18nConfigurationProperties;
import com.ibs.parent.code.service.file.DownloadFileException;
import com.ibs.parent.code.service.file.FileService;
import com.ibs.token.TokenContext;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nFileService extends FileService{
	
	@Autowired
	private I18nUtilService util;
	
	@Autowired
	private IbsI18nConfigurationProperties i18nConfig;
	
	/**
	 * 
	 * @param language
	 * @param addResponseData 是否添加响应数据
	 */
	public void updateI18nFile(String language, boolean addResponseData) {
		String[] languages = null;
		if(language.equalsIgnoreCase("all")) {
			List<Object[]> tmpLanguages = SessionContext.getSqlSession().query_("select distinct language from " + util.i18nMessageTableName());
			languages = new String[tmpLanguages.size()];
			for (int i = 0; i < languages.length; i++) {
				languages[i] = tmpLanguages.get(i)[0].toString();
			}
			tmpLanguages.clear();
		}else {
			languages = new String[] {language};
		}
		
		
		
		
		
		
		if(addResponseData) addSingleResponseData("language", Arrays.toString(languages));
	}
	
	/**
	 * 
	 * @param language
	 * @param response 
	 * @throws DownloadFileException 
	 */
	public void downloadByLanguage(String language, HttpServletResponse response) throws DownloadFileException {
		File file = i18nConfig.getDownloadFile(TokenContext.getToken().getProjectId(), language);
		if(!file.exists()) {
			updateI18nFile(language, false);
		}
		download(response, file);
	}
}
