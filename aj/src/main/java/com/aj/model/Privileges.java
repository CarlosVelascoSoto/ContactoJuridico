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
@Table(name = "privileges")
public class Privileges implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privilegesid-seq-gen")
	@SequenceGenerator(name = "privilegesid-seq-gen", sequenceName = "privileges_seq", allocationSize = 1, initialValue = 1)
	@Column(name = "privilegesid")
	private Integer privilegesid;
	@Column(name = "privilege")
	private String privilege;

	public Integer getPrivilegesid() {
		return privilegesid;
	}

	public void setPrivilegesid(Integer privilegesid) {
		this.privilegesid = privilegesid;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

}
