package com.ibs.i18n.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.orm.context.SessionFactoryRegister;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.douglei.tools.instances.reader.ResourcesReader;
import com.ibs.parent.code.service.BasicService;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nDynamicTableService extends BasicService{
	private static final String i18nMessageTable_Template = new ResourcesReader("mappings/I18nMessage.tmp.xml.template").readAll(1500).toString();// 表映射模板

	@Autowired
	private SessionFactoryRegister sessionFactoryRegister;
	
	@Transaction
	public void initial(String projectId) {
		// TODO Auto-generated method stub
		
	}

	@Transaction
	public void destroy(String projectId) {
		// TODO Auto-generated method stub
		
	}

}
