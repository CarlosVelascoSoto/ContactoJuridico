package com.aj.model;

public class TemplateEmail {
	private Integer templateemailid;
	private String contenidohtml;
	private String description;
	private Integer status;
	private Integer smtpid;
	private String subject;
	private String smtpname;

	public void setTemplateemailid(Integer templateemailid) {
		this.templateemailid = templateemailid;
	}

	public void setContenidohtml(String contenidohtml) {
		this.contenidohtml = contenidohtml;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTemplateemailid() {
		return templateemailid;
	}

	public String getContenidohtml() {
		return contenidohtml;
	}

	public String getDescription() {
		return description;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getSmtpid() {
		return smtpid;
	}

	public void setSmtpid(Integer smtpid) {
		this.smtpid = smtpid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSmtpname() {
		return smtpname;
	}

	public void setSmtpname(String smtpname) {
		this.smtpname = smtpname;
	}

}
