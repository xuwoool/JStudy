package com.jstudy.sys.vo;

public class CityProjectForm {
	private Long taskFlag;
	private String projectCd;
	private String approvalName;
	private String createUser;
	private String cityCode;
	private Double projectInvAt;
	
	public Long getTaskFlag() {
		return taskFlag;
	}
	public void setTaskFlag(Long taskFlag) {
		this.taskFlag = taskFlag;
	}
	public String getProjectCd() {
		return projectCd;
	}
	public void setProjectCd(String projectCd) {
		this.projectCd = projectCd;
	}
	public String getApprovalName() {
		return approvalName;
	}
	public void setApprovalName(String approvalName) {
		this.approvalName = approvalName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public Double getProjectInvAt() {
		return projectInvAt;
	}
	public void setProjectInvAt(Double projectInvAt) {
		this.projectInvAt = projectInvAt;
	}
}