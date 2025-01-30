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
@Table(name="companies")
public class Company implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="companies_seq-gen"
    )
    @SequenceGenerator(
        name="companies_seq-gen",
        sequenceName="companies_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="id")
	private int id;

	@Column(name="name")
	private String name;
	@Column(name = "rfc", updatable = false)
	private String rfc;

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id=id;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name=name;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	
}