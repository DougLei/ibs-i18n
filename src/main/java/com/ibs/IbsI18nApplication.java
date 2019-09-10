package com.ibs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.ibs.dynamic.table.DynamicTableService;
import com.ibs.dynamic.table.DynamicTableSystemAlreadyStartException;

/**
 * 
 * @author DougLei
 */
@SpringBootApplication
public class IbsI18nApplication {

	@Autowired
	private DynamicTableService service;
	
	@Bean
	public void start() throws DynamicTableSystemAlreadyStartException {
		service.start();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(IbsI18nApplication.class, args);
	}
}
