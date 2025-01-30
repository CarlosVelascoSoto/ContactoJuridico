package com.aj.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="users")
public class Users implements Serializable{
	private static final long serialVersionUID=1L;
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="userid-seq-gen")
/*	@GenericGenerator(name="userid-seq-gen", strategy="com.aj.hibernate.cfg.CustomUsersSequenceGenerator", 
		parameters ={@org.hibernate.annotations.Parameter(name="sequence", value="users_id_seq") })*/
	@SequenceGenerator(
		name="userid-seq-gen",
		sequenceName="users_id_seq",
		allocationSize=1,
		initialValue=1
	)
	@Column(name="id")
	private long id;
	@Column(name="role")
	private int role;
	@Column(name="username")
	private String username;
	@Column(name="password")
	private String password;
	@Column(name="first_name")
	private String first_name;
	@Column(name="last_name")
	private String last_name;
	@Column(name="company_name")
	private String company_name;
	@Column(name="photo_name")
	private String photo_name;
	@Column(name="email")
	private String email;
	@Column(name="phone")
	private String phone;
	@Column(name="address")
	private String address;
	@Column(name="city")
	private String city;
	@Column(name="state")
	private String state;
	@Column(name="country")
	private String country;
	@Column(name="status")
	private int status;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created", insertable=false)
	private Date created;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated", insertable=true)
	private Date updated;
	@Column(name="language")
	private String language;
	@Column(name="currencyid")
	private int currency;
	@Column(name="companyid")
	private int companyid;
	@Column(name="zipcode", nullable=true)
	private Integer zipcode;
	@Column(name="cellphone")
	private String cellphone;
	@Column(name="usertype", nullable=true)
	private Integer usertype;
	@Column(name="linkedclientid", nullable=true)
	private Integer linkedclientid;

	public long getId(){
		return this.id;
	}
	public void setId(long id){
		this.id=id;
	}

	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username=username;
	}

	public String getPassword(){
		return this.password;
	}
	public void setPassword(String password){
		this.password=password;
	}

	public String getEmail(){
		return this.email;
	}
	public void setEmail(String email){
		this.email=email;
	}

	public int getRole(){
		return this.role;
	}
	public void setRole(int role){
		this.role=role;
	}

	public String getFirst_name(){
		return this.first_name;
	}
	public void setFirst_name(String first_name){
		this.first_name=first_name;
	}

	public String getLast_name(){
		return this.last_name;
	}
	public void setLast_name(String last_name){
		this.last_name=last_name;
	}

	public String getCompany_name(){
		return this.company_name;
	}
	public void setCompany_name(String company_name){
		this.company_name=company_name;
	}

	public String getPhoto_name(){
		return this.photo_name;
	}
	public void setPhoto_name(String photo_name){
		this.photo_name=photo_name;
	}

	public String getPhone(){
		return this.phone;
	}
	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getAddress(){
		return this.address;
	}
	public void setAddress(String address){
		this.address=address;
	}

	public String getCity(){
		return this.city;
	}
	public void setCity(String city){
		this.city=city;
	}

	public String getState(){
		return this.state;
	}
	public void setState(String state){
		this.state=state;
	}

	public String getCountry(){
		return this.country;
	}
	public void setCountry(String country){
		this.country=country;
	}

	public int getStatus(){
		return this.status;
	}
	public void setStatus(int status){
		this.status=status;
	}

	public Date getCreated(){
		return this.created;
	}
	public void setCreated(Date created){
		this.created=created;
	}

	public Date getUpdated(){
		return this.updated;
	}
	public void setUpdated(Date updated){
		this.updated=updated;
	}

	public String getLanguage(){
		return language;
	}
	public void setLanguage(String language){
		this.language=language;
	}
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	public int getCompanyid() {
		return companyid;
	}
	public void setCompanyid(int companyid){
		this.companyid=companyid;
	}
	public Integer getZipcode(){
		return zipcode;
	}
	public void setZipcode(Integer zipcode){
		this.zipcode=zipcode;
	}
	public String getCellphone(){
		return cellphone;
	}
	public void setCellphone(String cellphone){
		this.cellphone=cellphone;
	}
	public Integer getUsertype(){
		return usertype;
	}
	public void setUsertype(Integer usertype){
		this.usertype=usertype;
	}
	public Integer getLinkedclientid(){
		return linkedclientid;
	}
	public void setLinkedclientid(Integer linkedclientid){
		this.linkedclientid=linkedclientid;
	}
}