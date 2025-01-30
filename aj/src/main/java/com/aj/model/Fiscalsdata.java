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
@Table(name="fiscalsdata")
public class Fiscalsdata implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="fiscalsdata_seq-gen"
    )
    @SequenceGenerator(
        name="fiscalsdata_seq-gen",
        sequenceName="fiscalsdata_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="fiscaldataid")
	private Long fiscaldataid;

	@Column(name="origintype")
	private Integer origintype;

	@Column(name="originid")
	private Long originid;

	@Column(name="personafiscalid")
	private Integer personafiscalid;

	@Column(name="businessname")
	private String businessname;

	@Column(name="commercialname")
	private String commercialname;

	@Column(name="rfc")
	private String rfc;

	@Column(name="curp")
	private String curp;

	@Column(name="address1")
	private String address1;

	@Column(name="address2")
	private String address2;

	@Column(name="address3")
	private String address3;

	@Column(name="zipcode")
	private String zipcode;

	@Column(name="ciudadid")
	private Integer ciudadid;

	@Column(name="startdate")
	private Date startdate;

	@Column(name="enddate")
	private Date enddate;

	@Column(name="samedata")
	private Integer samedata;

	public Long getFiscaldataid(){
		return fiscaldataid;
	}

	public void setFiscaldataid(Long fiscaldataid){
		this.fiscaldataid=fiscaldataid;
	}

	public Integer getOrigintype(){
		return origintype;
	}

	public void setOrigintype(Integer origintype){
		this.origintype=origintype;
	}

	public Long getOriginid(){
		return originid;
	}

	public void setOriginid(Long originid){
		this.originid=originid;
	}

	public Integer getPersonafiscalid(){
		return personafiscalid;
	}

	public void setPersonafiscalid(Integer personafiscalid){
		this.personafiscalid=personafiscalid;
	}

	public String getBusinessname(){
		return businessname;
	}

	public void setBusinessname(String businessname){
		this.businessname=businessname;
	}

	public String getCommercialname(){
		return commercialname;
	}

	public void setCommercialname(String commercialname){
		this.commercialname=commercialname;
	}

	public String getRfc(){
		return rfc;
	}

	public void setRfc(String rfc){
		this.rfc=rfc;
	}

	public String getCurp(){
		return curp;
	}

	public void setCurp(String curp){
		this.curp=curp;
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

	public String getZipcode(){
		return zipcode;
	}

	public void setZipcode(String zipcode){
		this.zipcode=zipcode;
	}

	public Integer getCiudadid(){
		return ciudadid;
	}

	public void setCiudadid(Integer ciudadid){
		this.ciudadid=ciudadid;
	}

	public Date getStartdate(){
		return startdate;
	}

	public void setStartdate(Date startdate){
		this.startdate=startdate;
	}

	public Date getEnddate(){
		return enddate;
	}

	public void setEnddate(Date enddate){
		this.enddate=enddate;
	}

	public Integer getSamedata() {
		return samedata;
	}

	public void setSamedata(Integer samedata){
		this.samedata=samedata;
	}
}