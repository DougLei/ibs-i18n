package com.ibs.i18n.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ibs.i18n.service.I18nService;
import com.ibs.response.IResponse;
import com.ibs.response.impl.dve.ResponseContext;

/**
 * 
 * @author DougLei
 */
@RestController
@RequestMapping("/i18n")
public class I18nController {
	
	@Autowired
	private I18nService service;
	
	@RequestMapping("/query/{code}")
	public IResponse query(@PathVariable String code) {
		
		return ResponseContext.getResponseInstance();
	}
}
