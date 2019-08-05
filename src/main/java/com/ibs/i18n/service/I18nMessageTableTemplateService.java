package com.ibs.i18n.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.douglei.tools.utils.ExceptionUtil;

/**
 * 
 * @author DougLei
 */
@Service
public class I18nMessageTableTemplateService {
	private static final Logger logger = LoggerFactory.getLogger(I18nMessageTableTemplateService.class);
	
	private static final String tableNamePrefix = "I18N_MESSAGE_";// 表名前缀
	private static final String templatePath = "mappings/I18nMessage.tmp.xml.template";// 模板路径
	private static final String tableMappingTemplate;// 表映射模板
	static {
		StringBuilder tmt = new StringBuilder(1200);
		try(BufferedReader br = new BufferedReader(new InputStreamReader(I18nMessageTableTemplateService.class.getClassLoader().getResourceAsStream(templatePath)))){
			while(br.ready()) {
				tmt.append(br.readLine().trim());
			}
		} catch (IOException e) {
			logger.error("读取国际化消息表模板文件["+templatePath+"]时出现异常: {}", ExceptionUtil.getExceptionDetailMessage(e));
		}
		tableMappingTemplate = tmt.toString();
	}
	
	/**
	 * 获得国际化消息表的表名
	 * @param tableIndex
	 * @return
	 */
	public String getTableName(short tableIndex) {
		return tableNamePrefix + tableIndex;
	}
	
	/**
	 * 获得国际化消息表的表映射
	 * @param tableIndex
	 * @return
	 */
	public String getTableMapping(short tableIndex) {
		return String.format(tableMappingTemplate, tableIndex);
	}
}