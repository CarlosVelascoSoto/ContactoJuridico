package com.aj.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "stage")
public class Stage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer stageid;
	@Column
	private String name;

	public Integer getStageid() {
		return stageid;
	}

	public void setStageid(Integer stageid) {
		this.stageid = stageid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
