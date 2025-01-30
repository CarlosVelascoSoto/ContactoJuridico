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
@Table(name="role")
public class Roles implements Serializable{
	
	private static final Integer serialVersionUID = (int) 1L;

	@Id
	@GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "roleid-seq-gen"
    )
    @SequenceGenerator(
        name = "roleid-seq-gen",
        sequenceName = "role_seq",
        allocationSize = 1,
        initialValue = 1
    )
	@Column(name = "roleid")
	private Long roleid;
	
	@Column(name = "name")
	private String rolename;

	@Column(name = "companyid",nullable=true)
	private Integer companyid;

	public Long getRoleid() {
		return roleid;
	}

	public void setRoleid(Long roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public Integer getCompanyid(){
		return companyid;
	}

	public void setCompanyid(Integer companyid){
		this.companyid=companyid;
	}
}