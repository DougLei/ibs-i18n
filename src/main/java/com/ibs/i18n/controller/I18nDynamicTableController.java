package com.ibs.i18n.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.dynamic.table.ValidateProjectIdNotNull;
import com.ibs.i18n.service.I18nDynamicTableService;
import com.ibs.parent.code.controller.BasicController;
import com.ibs.parent.code.validator.DataValidationResult;
import com.ibs.response.Response;
import com.ibs.response.ResponseContext;

/**
 * 
 * @author DougLei
 */
@RestController
@RequestMapping("/i18n/dynamic/table")
public class I18nDynamicTableController extends BasicController{
	private static final ValidateProjectIdNotNull validateProjectIdNotNullWhenInitial = new ValidateProjectIdNotNull("i18n", (byte)1);
	private static final ValidateProjectIdNotNull validateProjectIdNotNullWhenDestroy = new ValidateProjectIdNotNull("i18n", (byte)0);
	
	@Autowired
	private I18nDynamicTableService dynamicTableService;
	
	/**
	 * 初始化指定项目的I18nMessage表
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value="/initial/{projectId}", method=RequestMethod.GET)
	public Response initial(String projectId) {
		if(validateByValidator(projectId, validateProjectIdNotNullWhenInitial) == DataValidationResult.SUCCESS) {
			dynamicTableService.initial(projectId);
		}
		return ResponseContext.getFinalResponse();
	}
	
	/**
	 * 销毁指定项目的I18nMessage表
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value="/destroy/{projectId}", method=RequestMethod.GET)
	public Response destroy(String projectId) {
		if(validateByValidator(projectId, validateProjectIdNotNullWhenDestroy) == DataValidationResult.SUCCESS) {
			dynamicTableService.destroy(projectId);
		}
		return ResponseContext.getFinalResponse();
	}
}


