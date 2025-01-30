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
@Table(name="companyclients")
public class Companyclients implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="companyclientid_seq-gen"
    )
    @SequenceGenerator(
        name="companyclientid_seq-gen",
        sequenceName="companyclientid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="companyclientid")
	private int companyclientid;

	@Column(name="companyid",nullable=true)
	private Integer companyid;

	@Column(name="clientid")
	private int clientid;

	public int getCompanyclientid(){
		return companyclientid;
	}

	public void setCompanyclientid(int companyclientid){
		this.companyclientid=companyclientid;
	}

	public int getCompanyid(){
		return companyid;
	}

	public void setCompanyid(int companyid){
		this.companyid=companyid;
	}

	public int getClientid(){
		return clientid;
	}

	public void setClientid(int clientid){
		this.clientid=clientid;
	}
}