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
public class Companies implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

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
	@Column(name="companyid")
	private Long companyid;

	@Column(name="company")
	private String company;

	@Column(name="address1")
	private String address1;

	@Column(name="address2")
	private String address2;

	@Column(name="address3")
	private String address3;

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

	@Column(name="rfc")
	private String rfc;

	@Column(name="shortname")
	private String shortname;

	@Column(name="socialnetworkid1", nullable=true)
	private Integer socialnetworkid1;

	@Column(name="socialnetworkid2", nullable=true)
	private Integer socialnetworkid2;

	@Column(name="socialnetworkid3", nullable=true)
	private Integer socialnetworkid3;

	@Column(name="socialnetworkid4", nullable=true)
	private Integer socialnetworkid4;

	@Column(name="socialnetworkacct1", nullable=true)
	private String socialnetworkacct1;

	@Column(name="socialnetworkacct2", nullable=true)
	private String socialnetworkacct2;

	@Column(name="socialnetworkacct3", nullable=true)
	private String socialnetworkacct3;

	@Column(name="socialnetworkacct4", nullable=true)
	private String socialnetworkacct4;

	@Column(name="communicationlabel1", nullable=true)
	private Integer communicationlabel1;

	@Column(name="phone2")
	private String phone2;

	@Column(name="communicationlabel2", nullable=true)
	private Integer communicationlabel2;

	@Column(name="phone3", nullable=true)
	private String phone3;

	@Column(name="communicationlabel3")
	private Integer communicationlabel3;

	@Column(name="webpage")
	private String webpage;

	public Long getCompanyid(){
		return companyid;
	}

	public void setCompanyid(Long companyid){
		this.companyid=companyid;
	}

	public String getCompany(){
		return company;
	}

	public void setCompany(String company){
		this.company=company;
	}

	public String getAddress1(){
		return address1;
	}

	public void setAddress1(String address1){
		this.address1=address1;
	}

	public String getAddress2(){
		return address2;
	}

	public void setAddress2(String address2){
		this.address2=address2;
	}

	public String getAddress3(){
		return address3;
	}

	public void setAddress3(String address3){
		this.address3=address3;
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

	public String getRfc(){
		return rfc;
	}

	public void setRfc(String rfc){
		this.rfc=rfc;
	}

	public String getShortname(){
		return shortname;
	}

	public void setShortname(String shortname){
		this.shortname=shortname;
	}

	public Integer getSocialnetworkid1(){
		return socialnetworkid1;
	}

	public void setSocialnetworkid1(Integer socialnetworkid1){
		this.socialnetworkid1=socialnetworkid1;
	}

	public Integer getSocialnetworkid2(){
		return socialnetworkid2;
	}

	public void setSocialnetworkid2(Integer socialnetworkid2){
		this.socialnetworkid2=socialnetworkid2;
	}

	public Integer getSocialnetworkid3(){
		return socialnetworkid3;
	}

	public void setSocialnetworkid3(Integer socialnetworkid3){
		this.socialnetworkid3=socialnetworkid3;
	}

	public Integer getSocialnetworkid4(){
		return socialnetworkid4;
	}

	public void setSocialnetworkid4(Integer socialnetworkid4){
		this.socialnetworkid4=socialnetworkid4;
	}

	public String getSocialnetworkacct1(){
		return socialnetworkacct1;
	}

	public void setSocialnetworkacct1(String socialnetworkacct1){
		this.socialnetworkacct1=socialnetworkacct1;
	}

	public String getSocialnetworkacct2(){
		return socialnetworkacct2;
	}

	public void setSocialnetworkacct2(String socialnetworkacct2){
		this.socialnetworkacct2=socialnetworkacct2;
	}

	public String getSocialnetworkacct3(){
		return socialnetworkacct3;
	}

	public void setSocialnetworkacct3(String socialnetworkacct3){
		this.socialnetworkacct3=socialnetworkacct3;
	}

	public String getSocialnetworkacct4(){
		return socialnetworkacct4;
	}

	public void setSocialnetworkacct4(String socialnetworkacct4){
		this.socialnetworkacct4=socialnetworkacct4;
	}

	public Integer getCommunicationlabel1(){
		return communicationlabel1;
	}

	public void setCommunicationlabel1(Integer communicationlabel1){
		this.communicationlabel1=communicationlabel1;
	}

	public String getPhone2(){
		return phone2;
	}

	public void setPhone2(String phone2){
		this.phone2=phone2;
	}

	public Integer getCommunicationlabel2(){
		return communicationlabel2;
	}

	public void setCommunicationlabel2(Integer communicationlabel2){
		this.communicationlabel2=communicationlabel2;
	}

	public String getPhone3(){
		return phone3;
	}

	public void setPhone3(String phone3){
		this.phone3=phone3;
	}

	public Integer getCommunicationlabel3(){
		return communicationlabel3;
	}

	public void setCommunicationlabel3(Integer communicationlabel3){
		this.communicationlabel3=communicationlabel3;
	}

	public String getWebpage(){
		return webpage;
	}

	public void setWebpage(String webpage){
		this.webpage=webpage;
	}
}