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

@Entity
@Table(name="clients")
public class Clients implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="clients_seq-gen"
    )
    @SequenceGenerator(
        name="clients_seq-gen",
        sequenceName="clients_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="clientid")
	private int clientid;
	
	@Column(name="client")
	private String client;
	
	@Column(name="address1")
	private String address1;
	
	@Column(name="city")
	private String city;
	
	@Column(name="country")
	private String country;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="state")
	private String state;
	
	@Column(name="zipcode")
	private String zipcode;
	
	@Column(name="status",nullable=true)
	private Integer status;
	
	@Column(name="cellphone")
	private String cellphone;
	
	@Column(name="photo")
	private String photo;
	
	@Column(name="birthdate")
	private Date birthdate;
	
	@Column(name="comments")
	private String comments;

	@Column(name="personafiscalid")
	private Integer personafiscalid;

	@Column(name="webpage")
	private String webpage;

	@Column(name="contactperson")
	private String contactperson;
	
	@Column(name="rel_with")
	private String rel_with;
	
	@Column(name="ref_by")
	private String ref_by;

	public int getClientid(){
		return clientid;
	}

	public void setClientid(int clientid){
		this.clientid=clientid;
	}

	public String getClient(){
		return client;
	}

	public void setClient(String client){
		this.client=client;
	}

	public String getAddress1(){
		return address1;
	}

	public void setAddress1(String address1){
		this.address1=address1;
	}

	public String getCity(){
		return city;
	}

	public void setCity(String city){
		this.city=city;
	}

	public String getCountry(){
		return country;
	}

	public void setCountry(String country){
		this.country=country;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email=email;
	}

	public String getPhone(){
		return phone;
	}

	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getState(){
		return state;
	}

	public void setState(String state){
		this.state=state;
	}

	public String getZipcode(){
		return zipcode;
	}

	public void setZipcode(String zipcode){
		this.zipcode=zipcode;
	}

	public int getStatus(){
		return status;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public String getCellphone(){
		return cellphone;
	}

	public void setCellphone(String cellphone){
		this.cellphone=cellphone;
	}

	public String getPhoto(){
		return photo;
	}

	public void setPhoto(String photo){
		this.photo=photo;
	}

	public Date getBirthdate(){
		return birthdate;
	}

	public void setBirthdate(Date birthdate){
		this.birthdate=birthdate;
	}

	public String getComments(){
		return comments;
	}

	public void setComments(String comments){
		this.comments=comments;
	}

	public Integer getPersonafiscalid(){
		return personafiscalid;
	}

	public void setPersonafiscalid(Integer personafiscalid){
		this.personafiscalid=personafiscalid;
	}

	public String getWebpage(){
		return webpage;
	}

	public void setWebpage(String webpage){
		this.webpage=webpage;
	}

	public String getContactperson(){
		return contactperson;
	}

	public void setContactperson(String contactperson){
		this.contactperson=contactperson;
	}

	public String getRel_with(){
		return rel_with;
	}

	public void setRel_with(String rel_with){
		this.rel_with=rel_with;
	}

	public String getRef_by(){
		return ref_by;
	}

	public void setRef_by(String ref_by){
		this.ref_by=ref_by;
	}	
}