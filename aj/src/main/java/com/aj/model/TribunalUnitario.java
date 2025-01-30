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
@Table(name="tribunalUnitario")
public class TribunalUnitario implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="tribunalUnitarioid_seq-gen"
    )
    @SequenceGenerator(
        name="tribunalUnitarioid_seq-gen",
        sequenceName="tribunalUnitarioid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="tribunalUnitarioid")
	private Long tribunalUnitarioid;

	@Column(name="tribunalUnitario")
	private String tribunalUnitario;

	@Column(name="companyid", nullable=true)
	private Integer companyid;

	@Column(name="ciudadid", nullable=true)
	private Integer ciudadid;

	public Long getTribunalUnitarioid(){
		return tribunalUnitarioid;
	}

	public void setTribunalUnitarioid(Long tribunalUnitarioid){
		this.tribunalUnitarioid=tribunalUnitarioid;
	}

	public String getTribunalUnitario(){
		return tribunalUnitario;
	}

	public void setTribunalUnitario(String tribunalUnitario){
		this.tribunalUnitario=tribunalUnitario;
	}

	public Integer getCompanyid(){
		return companyid;
	}

	public void setCompanyid(Integer companyid){
		this.companyid=companyid;
	}

	public Integer getCiudadid(){
		return ciudadid;
	}

	public void setCiudadid(Integer ciudadid){
		this.ciudadid=ciudadid;
	}
}