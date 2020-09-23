package com.ibs;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.ibs.code.service.file.DownloadFile;

/**
 * 
 * @author DougLei
 */
@Component
@ConfigurationProperties(prefix="ibs.i18n")
public class IbsI18nConfigurationProperties {
	
	// 要下载文件的路径, 使用绝对路径
	private String downloadFilePath = System.getProperty("user.home") + File.separatorChar + ".i18n" + File.separatorChar;
	
	// 下载时查询数据的数量
	private short downloadQueryCount = 200;
	
	// 获取文件
	public File getFile(String projectId, String language) {
		return new File(downloadFilePath + File.separatorChar + projectId + File.separatorChar + language + ".json");
	}
	
	// 获取要下载的文件对象
	public DownloadFile getDownloadFile(String projectId, String language) {
		return new DownloadFile(downloadFilePath + File.separatorChar + projectId + File.separatorChar + language + ".json");
	}
	
	public String getDownloadFilePath() {
		return downloadFilePath;
	}
	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath + File.separatorChar + ".i18n" + File.separatorChar;
	}
	public short getDownloadQueryCount() {
		return downloadQueryCount;
	}
	public void setDownloadQueryCount(short downloadQueryCount) {
		this.downloadQueryCount = downloadQueryCount;
	}
}
