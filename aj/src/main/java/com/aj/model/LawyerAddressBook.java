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
@Table(name="lawyeraddressbook")
public class LawyerAddressBook implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="lawyeraddressbook_seq-gen"
    )
    @SequenceGenerator(
        name="lawyeraddressbook_seq-gen",
        sequenceName="lawyeraddressbook_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="id")
	private Long id;

	@Column(name="companyid")
	private Long companyid;

	@Column(name="abogadocontraparte", nullable=true)
	private Integer abogadocontraparte;

	@Column(name="commtypeid")
	private Long commtypeid;

	@Column(name="commlabelid")
	private Long commlabelid;

	@Column(name="contactinfo")
	private String contactinfo;

	@Column(name="additionalinfo")
	private String additionalinfo;

	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id=id;
	}

	public Long getCompanyid(){
		return companyid;
	}

	public void setCompanyid(Long companyid){
		this.companyid=companyid;
	}

	public Integer getAbogadocontraparte(){
		return abogadocontraparte;
	}

	public void setAbogadocontraparte(Integer abogadocontraparte){
		this.abogadocontraparte=abogadocontraparte;
	}

	public Long getCommtypeid(){
		return commtypeid;
	}

	public void setCommtypeid(Long commtypeid){
		this.commtypeid=commtypeid;
	}

	public Long getCommlabelid(){
		return commlabelid;
	}

	public void setCommlabelid(Long commlabelid){
		this.commlabelid=commlabelid;
	}

	public String getContactinfo(){
		return contactinfo;
	}

	public void setContactinfo(String contactinfo){
		this.contactinfo=contactinfo;
	}

	public String getAdditionalinfo(){
		return additionalinfo;
	}

	public void setAdditionalinfo(String additionalinfo){
		this.additionalinfo=additionalinfo;
	}
}