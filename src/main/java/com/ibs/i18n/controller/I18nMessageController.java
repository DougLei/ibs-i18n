package com.ibs.i18n.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@Controller
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
	@ResponseBody
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
	@ResponseBody
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
	@ResponseBody
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
	@ResponseBody
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
	@ResponseBody
	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
	public Response deleteByIds(HttpServletRequest request) {
		String ids = getDeleteIds(request);
		if(ids != null) {
			messageUpdateService.deleteByIds(ids);
		}
		return ResponseContext.getFinalBatchResponse();
	}
	
	
	/**
	 * 查询指定code, 所有语言的的国际化消息
	 * @param code
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="query/{code}", method=RequestMethod.GET)
	public Response query(String code) {
		// TODO 查询指定code, 所有语言的的国际化消息
		return ResponseContext.getFinalBatchResponse();
	}
	
	/**
	 * 查询指定language, 指定code的的国际化消息
	 * code多个用, 分割
	 * @param code
	 * @param language
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="query/{language}/{code}", method=RequestMethod.GET)
	public Response query(@PathVariable(value="language") String language, @PathVariable(value="code") String code) {
		// TODO 查询指定language, 指定code的的国际化消息, code多个用, 分割
		return ResponseContext.getFinalResponse();
	}
	
	/**
	 * 下载指定language的国际化消息配置文件
	 * @param language 可以只是language名, 或language_时间戳(即之前下载的文件名), 这种情况会先去判断是否有文件更新, 如果有更新, 再重新下载, 否则不进行无用的下载
	 * @return 返回的文件名为 language_时间戳.properties
	 */
	@RequestMapping(value="download/{language}", method=RequestMethod.GET)
	public Object download(String language) {
		// TODO 下载指定language的国际化消息配置文件
		// 在服务器指定目录下也有下载文件的缓存--- .../i18n-download-cache/{projectId}/lanuage_时间戳.properties
		return ResponseContext.getFinalResponse();
	}
}