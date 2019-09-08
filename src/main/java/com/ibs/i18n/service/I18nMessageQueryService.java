package com.ibs.i18n.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tools.ant.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.douglei.i18n.I18nMessage;
import com.douglei.orm.context.SessionContext;
import com.douglei.orm.context.transaction.component.Transaction;
import com.douglei.orm.context.transaction.component.TransactionComponent;
import com.ibs.IbsI18nConfigurationProperties;
import com.ibs.dynamic.table.DynamicTableConfigurationProperties;
import com.ibs.dynamic.table.DynamicTableIndexContext;
import com.ibs.parent.code.service.DownloadService;
import com.ibs.response.ResponseContext;
import com.ibs.token.TokenContext;

/**
 * 
 * @author DougLei
 */
@TransactionComponent
public class I18nMessageQueryService extends DownloadService{
	
	@Autowired
	private DynamicTableConfigurationProperties config;
	
	@Autowired
	private IbsI18nConfigurationProperties i18nConfig;
	
	/**
	 * I18nMessage表名
	 * @return
	 */
	private String i18nMessageTableName() {
		return config.getMappingCodes()[0] + DynamicTableIndexContext.getIndex();
	}
	
	/**
	 * 
	 * @param codes
	 */
	@Transaction
	public void queryByCodes(String[] codes) {
		List<Object> parameters = new ArrayList<Object>(codes.length);
		StringBuilder querySql = new StringBuilder(100);
		querySql.append("select id, code, language, message, priority from ").append(i18nMessageTableName()).append(" where ");
		if(codes.length == 1) {
			parameters.add(codes[0]);
			querySql.append("code=?");
		}else {
			querySql.append("code in(");
			for (byte i = 0; i < codes.length; i++) {
				parameters.add(codes[i]);
				querySql.append("?");
				if(i < codes.length-1) {
					querySql.append(",");
				}
			}
			querySql.append(")");
		}
		ResponseContext.addData(SessionContext.getSqlSession().query(I18nMessage.class, querySql.toString(), parameters));
	}

	/**
	 * 
	 * @param language
	 * @param response 
	 */
	public void downloadByLanguage(String language, HttpServletResponse response) {
		File file = i18nConfig.getDownloadFile(TokenContext.getToken().getProjectId(), language);
		if(file.exists()) {
			
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
				PrintWriter writer = response.getWriter();
				while(reader.ready()) {
					writer.write(reader.readLine());
				}
				
				
				
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		// TODO 下载指定language的国际化消息配置文件
		// 在服务器指定目录下也有下载文件的缓存--- .../i18n-download-cache/{projectId}/lanuage_时间戳.json
		
		
		// 去查询指定language的所有数据, 建议分页查询, 根据总数量觉得分几次页
		// select code, message from i18nMessageTableName() where language=? order by priority asc
	}
}
