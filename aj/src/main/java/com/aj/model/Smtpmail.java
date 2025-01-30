package com.aj.model;

public class Smtpmail {
	private Integer smtpid;
	private String host;
	private String accountmail;
	private String aliasmail;
	private String passwordmail;
	private Integer port;
	
	public Integer getSmtpid() {
		return smtpid;
	}
	public void setSmtpid(Integer smtpid) {
		this.smtpid = smtpid;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getAccountmail() {
		return accountmail;
	}
	public void setAccountmail(String accountmail) {
		this.accountmail = accountmail;
	}
	public String getAliasmail() {
		return aliasmail;
	}
	public void setAliasmail(String aliasmail) {
		this.aliasmail = aliasmail;
	}
	public String getPasswordmail() {
		return passwordmail;
	}
	public void setPasswordmail(String passwordmail) {
		this.passwordmail = passwordmail;
	}
	public Integer getPort() {
		return port;
	}
	public void setPort(Integer port) {
		this.port = port;
	}
}