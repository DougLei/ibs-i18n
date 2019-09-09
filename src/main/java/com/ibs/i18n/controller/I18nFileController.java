package com.ibs.i18n.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.i18n.service.I18nFileService;
import com.ibs.parent.code.controller.BasicController;
import com.ibs.parent.code.controller.validators.ParameterNotBlankValidator;
import com.ibs.parent.code.service.file.DownloadFileException;
import com.ibs.parent.code.validator.DataValidationResult;
import com.ibs.response.Response;
import com.ibs.response.ResponseContext;

/**
 * 
 * @author DougLei
 */
@RestController
@RequestMapping("/i18n/file")
public class I18nFileController extends BasicController{
	private static final ParameterNotBlankValidator languageNotBlankValidator = new ParameterNotBlankValidator("language");
	
	@Autowired
	private I18nFileService service;
	
	/**
	 * 更新国际化文件
	 * @param language 更新指定language的文件, 或传入all, 更新所有language的文件
	 * @return
	 */
	@RequestMapping(value="{language}/file/update", method=RequestMethod.GET)
	public Response updateI18nFile(String language) {
		if(validateByValidator(language, languageNotBlankValidator) == DataValidationResult.SUCCESS) {
			service.updateI18nFile(language, true);
		}
		return ResponseContext.getFinalResponse();
	}
	
	
	/**
	 * 下载指定language的国际化消息配置文件
	 * @param language 
	 * @param response
	 * @return 
	 * @throws DownloadFileException 
	 */
	@RequestMapping(value="{language}/download", method=RequestMethod.GET)
	public void downloadByLanguage(@PathVariable(name="language") String language, HttpServletResponse response) throws DownloadFileException {
		if(validateByValidator(language, languageNotBlankValidator) == DataValidationResult.SUCCESS) {
			service.downloadByLanguage(language, response);
		}
	}
}
