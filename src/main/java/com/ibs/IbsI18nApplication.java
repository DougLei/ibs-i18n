package com.ibs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ibs.i18n.service.I18nDynamicTableService;

/**
 * 
 * @author DougLei
 */
@SpringBootApplication
public class IbsI18nApplication {

	@Autowired
	private I18nDynamicTableService service;
	
	@Bean
	public void start() {
		service.start();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(IbsI18nApplication.class, args);
	}
}
