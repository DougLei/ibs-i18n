package com.ibs.i18n.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.i18n.entity.I18nMessage;
import com.ibs.i18n.service.I18nMessageQueryService;
import com.ibs.i18n.service.I18nMessageUpdateService;
import com.ibs.parent.code.controller.BasicController;
import com.ibs.parent.code.validator.DataValidationResult;
import com.ibs.response.Response;
import com.ibs.response.ResponseContext;

/**
 * 
 * @author DougLei
 */
@RestController
@RequestMapping("/i18n/message")
public class I18nMessageController extends BasicController{
	
	@Autowired
	private I18nMessageQueryService messageQueryService;
	
	@Autowired
	private I18nMessageUpdateService messageUpdateService;
	
	/**
	 * 添加国际化消息
	 * @param message
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public Response add(I18nMessage message) {
		if(validate(message) == DataValidationResult.SUCCESS) {
			messageUpdateService.insert(message);
		}
		return ResponseContext.getFinalResponse();
	}
	
	/**
	 * 批量添加国际化消息
	 * @param messages
	 * @return
	 */
	@RequestMapping(value="/adds", method=RequestMethod.POST)
	public Response add(List<I18nMessage> messages) {
		if(validate(messages) == DataValidationResult.SUCCESS) {
			messageUpdateService.insert(messages);
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
			messageUpdateService.update(message);
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
			messageUpdateService.update(messages);
		}
		return ResponseContext.getFinalBatchResponse();
	}

	/**
	 * 删除国际化消息
	 * @return
	 */
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public Response deleteByIds(HttpServletRequest request) {
		String ids = getDeleteIds(request);
		if(ids != null) {
			messageUpdateService.deleteByIds(ids);
		}
		return ResponseContext.getFinalBatchResponse();
	}
}