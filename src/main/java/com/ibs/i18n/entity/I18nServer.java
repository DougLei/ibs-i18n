package com.ibs.i18n.entity;

import java.util.Date;

import com.ibs.parent.code.entity.Entity;

/**
 * 国际化服务
 * @author DougLei
 */
public class I18nServer implements Entity{
	
	private int id;// 主键
	
	private String projectId;// 项目id
	private String customerId;// 租户id
	private String uniqueCode;// 唯一编码
	private short tableIndex;// 国际化消息表索引
	
	private String createUserId;// 创建人id
	private String createUserName;// 创建人name
	private Date createDate;// 创建时间
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getUniqueCode() {
		return uniqueCode;
	}
	public void setUniqueCode(String uniqueCode) {
		this.uniqueCode = uniqueCode;
	}
	public short getTableIndex() {
		return tableIndex;
	}
	public void setTableIndex(short tableIndex) {
		this.tableIndex = tableIndex;
	}
	public String getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
