package com.ibs.i18n.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.douglei.api.doc.annotation.Api;
import com.douglei.api.doc.annotation.ApiCatalog;
import com.douglei.api.doc.annotation.ApiParam;
import com.douglei.api.doc.annotation.ApiParam_;
import com.ibs.code.DataValidationResult;
import com.ibs.code.controller.BasicController;
import com.ibs.code.controller.validators.ParameterNotBlankValidator;
import com.ibs.code.service.file.CreateFileException;
import com.ibs.code.service.file.DownloadFileException;
import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;
import com.ibs.i18n.service.I18nFileService;

/**
 * 
 * @author DougLei
 */
@ApiCatalog(name="国际化文件api")
@RestController
@RequestMapping("/i18n/file")
public class I18nFileController extends BasicController{
	private ParameterNotBlankValidator languageNotBlankValidator = new ParameterNotBlankValidator("language");
	
	@Autowired
	private I18nFileService service;
	
	/**
	 * 创建国际化内容文件
	 * @param language 创建指定language的文件, 或传入all, 创建所有language的文件
	 * @return
	 * @throws CreateFileException 
	 */
	@Api(name="创建国际化内容文件",
			 url=@ApiParam(params ={
				 @ApiParam_(name="language", required=true, description="创建指定language的文件, 或传入all, 创建所有language的文件", egValue="zh_CN"),
			 }))
	@RequestMapping(value="create/{language}", method=RequestMethod.GET)
	public Response createI18nFile(@PathVariable(name="language") String language) throws CreateFileException {
		if(validateByValidator(language, languageNotBlankValidator) == DataValidationResult.SUCCESS) {
			service.createI18nFile(language);
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
	@RequestMapping(value="download/{language}", method=RequestMethod.GET)
	public void downloadByLanguage(@PathVariable(name="language") String language, HttpServletResponse response) throws DownloadFileException {
		if(validateByValidator(language, languageNotBlankValidator) == DataValidationResult.SUCCESS) {
			service.downloadByLanguage(language, response);
		}
	}
}
