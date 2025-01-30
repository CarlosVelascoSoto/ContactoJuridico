package com.aj.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "workflowprocess")
public class Wfprocessstage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer wfprocessstageid;
	@Column
	private Integer workflowid;
	@Column
	private Integer stageid;
	@Column
	private Integer processid;
	
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wfprocessstageorden-gen")
	@SequenceGenerator(name = "wfprocessstageorden-gen", sequenceName = "wfprocessstageorden_seq", allocationSize = 1, initialValue = 1)
	@Column
	private Integer orden;

	public Integer getWfprocessstageid() {
		return wfprocessstageid;
	}

	public void setWfprocessstageid(Integer wfprocessstageid) {
		this.wfprocessstageid = wfprocessstageid;
	}

	public Integer getWorkflowid() {
		return workflowid;
	}

	public void setWorkflowid(Integer workflowid) {
		this.workflowid = workflowid;
	}

	public Integer getStageid() {
		return stageid;
	}

	public void setStageid(Integer stageid) {
		this.stageid = stageid;
	}

	public Integer getProcessid() {
		return processid;
	}

	public void setProcessid(Integer processid) {
		this.processid = processid;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	
	
}
