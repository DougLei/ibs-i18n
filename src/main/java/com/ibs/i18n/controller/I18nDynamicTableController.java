package com.ibs.i18n.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.components.response.Response;
import com.ibs.components.response.ResponseContext;
import com.ibs.i18n.service.I18nUtilService;
import com.ibs.parent.code.controller.BasicController;
import com.ibs.parent.code.service.dynamic.table.DynamicTableService;

/**
 * 
 * @author DougLei
 */
@RestController
@RequestMapping("/i18n/dynamic/table")
public class I18nDynamicTableController extends BasicController{
	
	@Autowired
	private DynamicTableService service;
	
	@Autowired
	private I18nUtilService util;
	
	/**
	 * 初始化指定项目的I18nMessage表
	 * @return
	 */
	@RequestMapping(value="/initial", method=RequestMethod.GET)
	public Response initial() {
		service.initial(util.getMappingCodes());
		return ResponseContext.getFinalResponse();
	}
	
	/**
	 * 销毁指定项目的I18nMessage表
	 * @return
	 */
	@RequestMapping(value="/destroy", method=RequestMethod.GET)
	public Response destroy() {
		service.destroy(util.getMappingCodes());
		return ResponseContext.getFinalResponse();
	}
}


