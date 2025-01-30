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

@SuppressWarnings("serial")
@Entity
@Table(name="lawyerdirectory")
public class LawyerDirectory implements Serializable{
	@SuppressWarnings("unused")
	private static final Long serialVersionUID=1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="lawyerdirectory_seq-gen"
    )
    @SequenceGenerator(
        name="lawyerdirectory_seq-gen",
        sequenceName="lawyerdirectory_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="lawyerid")
	private Long lawyerid;

	@Column(name="companyid")
	private int companyid;

	@Column(name="company_name")
	private String company_name;

	@Column(name="first_name")
	private String first_name;

	@Column(name="last_name")
	private String last_name;

	@Column(name="jobposition")
	private String jobposition;

	@Column(name="speciality")
	private String speciality;

	@Column(name="address1")
	private String address1;

	@Column(name="address2")
	private String address2;

	@Column(name="address3")
	private String address3;

	@Column(name="zipcode")
	private String zipcode;

	@Column(name="paisid", nullable=true)
	private Integer paisid;

	@Column(name="estadoid", nullable=true)
	private Integer estadoid;

	@Column(name="ciudadid", nullable=true)
	private Integer ciudadid;

	@Column(name="status")
	private int status;

	@Column(name="notes")
	private String notes;

	@Column(name="created")
	private Date created;

	public Long getLawyerid(){
		return lawyerid;
	}

	public void setLawyerid(Long lawyerid){
		this.lawyerid=lawyerid;
	}

	public int getCompanyid(){
		return companyid;
	}

	public void setCompanyid(int companyid){
		this.companyid=companyid;
	}

	public String getCompany_name(){
		return company_name;
	}

	public void setCompany_name(String company_name){
		this.company_name=company_name;
	}

	public String getFirst_name(){
		return first_name;
	}

	public void setFirst_name(String first_name){
		this.first_name=first_name;
	}

	public String getLast_name(){
		return last_name;
	}

	public void setLast_name(String last_name){
		this.last_name=last_name;
	}

	public String getJobposition(){
		return jobposition;
	}

	public void setJobposition(String jobposition){
		this.jobposition=jobposition;
	}

	public String getSpeciality(){
		return speciality;
	}

	public void setSpeciality(String speciality){
		this.speciality=speciality;
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

	public Integer getPaisid(){
		return paisid;
	}

	public void setPaisid(Integer paisid){
		this.paisid=paisid;
	}

	public Integer getEstadoid(){
		return estadoid;
	}

	public void setEstadoid(Integer estadoid){
		this.estadoid=estadoid;
	}

	public Integer getCiudadid(){
		return ciudadid;
	}

	public void setCiudadid(Integer ciudadid){
		this.ciudadid=ciudadid;
	}

	public int getStatus(){
		return status;
	}

	public void setStatus(int status){
		this.status=status;
	}

	public String getNotes(){
		return notes;
	}

	public void setNotes(String notes){
		this.notes=notes;
	}

	public Date getCreated(){
		return created;
	}

	public void setCreated(Date created){
		this.created=created;
	}
}