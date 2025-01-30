package com.aj.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "workflow")
public class Workflow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer workflowid;
	@Column
	private String name;
	@Column
	private Integer defaultvalue;
	

	public Integer getWorkflowid() {
		return workflowid;
	}

	public void setWorkflowid(Integer workflowid) {
		this.workflowid = workflowid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDefaultvalue() {
		return defaultvalue;
	}

	public void setDefaultvalue(Integer defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	
}
