package com.ibs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import com.ibs.i18n.I18nUtil;
import com.ibs.parent.code.service.dynamic.table.DynamicTableService;

/**
 * 
 * @author DougLei
 */
@EnableEurekaClient
@SpringBootApplication
public class IbsI18nApplication {

	@Autowired
	private DynamicTableService service;
	
	@Autowired
	private I18nUtil util;
	
	@Bean
	public void start() {
		service.start("I18N", util.getMappingTemplates());
	}
	
	public static void main(String[] args) {
		SpringApplication.run(IbsI18nApplication.class, args);
	}
}
