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
@Table(name="contacts")
public class Contact implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="contactsid-seq-gen"
    )
    @SequenceGenerator(
        name="contactsid-seq-gen",
        sequenceName="contacts_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="id")
	private int id;

	@Column(name="address")
	private String address;

	@Column(name="companies_id")
	private int companyId;

	@Column(name="user_id")
	private int userId;

	public int getId(){
		return id;
	}

	public void setId(int id){
		this.id=id;
	}

	public String getAddress(){
		return address;
	}

	public void setAddress(String address){
		this.address=address;
	}

	public int getCompanyId(){
		return companyId;
	}

	public void setCompanyId(int companyId){
		this.companyId=companyId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
}