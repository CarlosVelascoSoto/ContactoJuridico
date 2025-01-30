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
@Table(name="salas")
public class Salas implements Serializable{
	
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="salaid_seq-gen"
    )
    @SequenceGenerator(
        name="salaid_seq-gen",
        sequenceName="salaid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="salaid")
	private Long salaid;
	
	@Column(name="sala")
	private String sala;

	@Column(name="companyid", nullable=true)
	private Integer companyid;

	public Long getSalaid(){
		return salaid;
	}

	public void setSalaid(Long salaid){
		this.salaid=salaid;
	}

	public String getSala(){
		return sala;
	}

	public void setSala(String sala){
		this.sala=sala;
	}

	public Integer getCompanyid(){
		return companyid;
	}

	public void setCompanyid(Integer companyid){
		this.companyid=companyid;
	}
}