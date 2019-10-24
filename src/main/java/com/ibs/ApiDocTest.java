package com.ibs;

import java.io.IOException;

import com.douglei.api.doc.ApiDocBuilder;
import com.douglei.api.doc.ApiFolderBuilder;
import com.douglei.api.doc.types.ParamStructType;
import com.ibs.components.request.header.RequestHeader;
import com.ibs.components.response.Response;

public class ApiDocTest {
	public static void main(String[] args) throws IOException {
		ApiDocBuilder builder = new ApiFolderBuilder();
		
		builder.setName("国际化消息API");
		builder.setVersion("1.0");
		builder.setPath("C:\\Users\\Administrator.USER-20190410XF\\Desktop");
	
		builder.setAuthors("DougLei");
	
		builder.setDevEnvironmentUrls("http://192.168.1.252:10002");
		builder.setTestEnvironmentUrls("http://192.168.1.111:10002");
	
		builder.setCommonHeader(ParamStructType.OBJECT, RequestHeader.class);
		builder.setCommonResponse(ParamStructType.OBJECT, Response.class);
	
		builder.setScanPackages("com.ibs.i18n.controller");
		
		builder.build();
	}
}
