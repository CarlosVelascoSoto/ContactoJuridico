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
@Table(name="tribunalColegiado")
public class TribunalColegiado implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="tribunalcolegiadoid_seq-gen"
    )
    @SequenceGenerator(
        name="tribunalcolegiadoid_seq-gen",
        sequenceName="tribunalcolegiadoid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="tribunalcolegiadoid")
	private Long tribunalcolegiadoid;

	@Column(name="tribunalcolegiado")
	private String tribunalcolegiado;

	@Column(name="companyid", nullable=true)
	private Integer companyid;

	@Column(name="ciudadid", nullable=true)
	private Integer ciudadid;

	public Long getTribunalcolegiadoid(){
		return tribunalcolegiadoid;
	}

	public void setTribunalcolegiadoid(Long tribunalcolegiadoid){
		this.tribunalcolegiadoid=tribunalcolegiadoid;
	}

	public String getTribunalcolegiado(){
		return tribunalcolegiado;
	}

	public void setTribunalcolegiado(String tribunalcolegiado){
		this.tribunalcolegiado=tribunalcolegiado;
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