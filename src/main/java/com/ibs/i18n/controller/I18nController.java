package com.ibs.i18n.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ibs.i18n.entity.I18nMessage;
import com.ibs.i18n.service.I18nQueryService;
import com.ibs.i18n.service.I18nUpdateService;
import com.ibs.parent.code.controller.BasicController;
import com.ibs.parent.code.validator.DataValidationResult;
import com.ibs.response.Response;
import com.ibs.response.ResponseContext;

/**
 * 
 * @author DougLei
 */
@RequestMapping("/i18n")
public class I18nController extends BasicController{
	
	@Autowired
	private I18nQueryService queryService;
	
	@Autowired
	private I18nUpdateService updateService;
	
	/**
	 * 添加国际化消息
	 * @param message
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Response add(I18nMessage message) {
		if(validate(message) == DataValidationResult.SUCCESS) {
			updateService.insert(message);
		}
		return ResponseContext.getFinalResponse();
	}
	
	/**
	 * 批量添加国际化消息
	 * @param messages
	 * @return
	 */
	@RequestMapping(value="/adds", method=RequestMethod.POST)
	public Response adds(List<I18nMessage> messages) {
		if(validate(messages) == DataValidationResult.SUCCESS) {
			updateService.insert(messages);
		}
		return ResponseContext.getFinalBatchResponse();
	}
	
	/**
	 * 修改国际化消息
	 * @param message
	 * @return
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public Response update(I18nMessage message) {
		if(validate(message) == DataValidationResult.SUCCESS) {
			updateService.update(message);
		}
		return ResponseContext.getFinalResponse();
	}
	
	/**
	 * 批量修改国际化消息
	 * @param messages
	 * @return
	 */
	@RequestMapping(value="/updates", method=RequestMethod.POST)
	public Response update(List<I18nMessage> messages) {
		if(validate(messages) == DataValidationResult.SUCCESS) {
			updateService.update(messages);
		}
		return ResponseContext.getFinalBatchResponse();
	}
	

	/**
	 * 删除国际化消息
	 * @param message
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public Response delete(I18nMessage message) {
		if(validate(message) == DataValidationResult.SUCCESS) {
			updateService.delete(message);
		}
		return ResponseContext.getFinalResponse();
	}
	
	/**
	 * 删除修改国际化消息
	 * @param messages
	 * @return
	 */
	@RequestMapping(value="/deletes", method=RequestMethod.POST)
	public Response delete(List<I18nMessage> messages) {
		if(validate(messages) == DataValidationResult.SUCCESS) {
			updateService.delete(messages);
		}
		return ResponseContext.getFinalBatchResponse();
	}
}
