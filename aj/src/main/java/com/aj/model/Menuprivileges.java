package com.aj.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "menuprivileges")
public class Menuprivileges implements Serializable{
	private static final Integer serialVersionUID = (int) 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menuprivileges-seq-gen")
	@SequenceGenerator(name = "menuprivileges-seq-gen", sequenceName = "menuprivileges_seq", allocationSize = 1, initialValue = 1)
	private Integer menuprivilegesid;
	private Integer privilegesid;
	private Integer menuid;
	private Integer roleid;
	private Integer shareddocketid;

	public void setMenuprivilegesid(Integer menuprivilegesid) {
		this.menuprivilegesid = menuprivilegesid;
	}

	public void setPrivilegesid(Integer privilegesid) {
		this.privilegesid = privilegesid;
	}

	public void setMenuid(Integer menuid) {
		this.menuid = menuid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Integer getMenuprivilegesid() {
		return menuprivilegesid;
	}

	public Integer getPrivilegesid() {
		return privilegesid;
	}

	public Integer getMenuid() {
		return menuid;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public Integer getShareddocketid(){
		return shareddocketid;
	}

	public void setShareddocketid(Integer shareddocketid){
		this.shareddocketid=shareddocketid;
	}
}
