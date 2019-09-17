package com.ibs.i18n.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.components.filters.validator.DataValidationResult;
import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;
import com.ibs.i18n.service.I18nFileService;
import com.ibs.parent.code.controller.BasicController;
import com.ibs.parent.code.controller.validators.ParameterNotBlankValidator;
import com.ibs.parent.code.service.file.CreateSystemFileException;
import com.ibs.parent.code.service.file.DownloadFileException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author DougLei
 */
@Api(value="国际化文件操作接口控制器")
@RestController
@RequestMapping("/i18n/file")
public class I18nFileController extends BasicController{
	private static final ParameterNotBlankValidator languageNotBlankValidator = new ParameterNotBlankValidator("language");
	
	@Autowired
	private I18nFileService service;
	
	/**
	 * 创建国际化内容文件
	 * @param language 创建指定language的文件, 或传入all, 创建所有language的文件
	 * @return
	 * @throws CreateSystemFileException 
	 */
	@ApiOperation(value="创建国际化文件", notes="前端需要下载创建的文件, 渲染国际化页面")
	@ApiImplicitParam(paramType="path", name="language", required=true, dataType="String")
	@RequestMapping(value="create/{language}", method=RequestMethod.GET)
	public Response createI18nFile(@PathVariable(name="language") String language) throws CreateSystemFileException {
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
