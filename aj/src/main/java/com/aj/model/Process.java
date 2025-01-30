package com.aj.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "process")
public class Process implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer processid;
	@Column
	private String name;

	public Integer getProcessid() {
		return processid;
	}

	public void setProcessid(Integer processid) {
		this.processid = processid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
