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
@Table(name="socialnetworkclient")
public class Socialnetworkclient implements Serializable{
	private static final Integer serialVersionUID = (int) 1L;

	@Id
	@GeneratedValue(
		strategy = GenerationType.SEQUENCE,
		generator = "socialnetworkclient-seq-gen"
	)
	@SequenceGenerator(
		name="socialnetworkclient-seq-gen",
		sequenceName="socialnetworkclient_seq",
		allocationSize=1,
		initialValue=1
	)
	@Column(name = "snid")
	private Long snid;

	@Column(name="companyclientid")
	private int companyclientid;
	
	@Column(name="socialnetworkid")
	private int socialnetworkid;

	@Column(name="address")
	private String address;

	public Long getSnid(){
		return snid;
	}

	public void setSnid(Long snid){
		this.snid=snid;
	}

	public int getCompanyclientid(){
		return companyclientid;
	}

	public void setCompanyclientid(int companyclientid){
		this.companyclientid=companyclientid;
	}

	public int getSocialnetworkid(){
		return socialnetworkid;
	}

	public void setSocialnetworkid(int socialnetworkid){
		this.socialnetworkid=socialnetworkid;
	}

	public String getAddress(){
		return address;
	}

	public void setAddress(String address){
		this.address=address;
	}
}