package com.ibs;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 
 * @author DougLei
 */
@Component
@ConfigurationProperties(prefix="ibs.i18n")
public class IbsI18nConfigurationProperties {
	
	// 要下载文件的路径, 使用绝对路径
	private String downloadFilePath = System.getProperty("user.home") + File.separatorChar + "i18n-download" + File.separatorChar;
	
	/**
	 * 获取要下载的文件
	 * @param projectId
	 * @param language
	 * @return
	 */
	public File getDownloadFile(String projectId, String language) {
		return new File(downloadFilePath + File.separatorChar + projectId + File.separatorChar + language + ".json");
	}
	public String getDownloadFilePath() {
		return downloadFilePath;
	}
	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath + File.separatorChar + "i18n-download" + File.separatorChar;
	}
}
