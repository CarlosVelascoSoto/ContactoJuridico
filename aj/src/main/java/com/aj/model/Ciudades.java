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
@Table(name="ciudades")
public class Ciudades implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="ciudadid_seq-gen"
    )
    @SequenceGenerator(
        name="ciudadid_seq-gen",
        sequenceName="ciudadid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="ciudadid")
	private Long ciudadid;

	@Column(name="ciudad")
	private String ciudad;

	@Column(name="companyid", nullable=true)
	private Integer companyid;

	@Column(name="estadoid", nullable=true)
	private Integer estadoid;

	public Long getCiudadid(){
		return ciudadid;
	}

	public void setCiudadid(Long ciudadid){
		this.ciudadid=ciudadid;
	}

	public String getCiudad(){
		return ciudad;
	}

	public void setCiudad(String ciudad){
		this.ciudad=ciudad;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid){
		this.companyid=companyid;
	}

	public Integer getEstadoid(){
		return estadoid;
	}

	public void setEstadoid(Integer estadoid){
		this.estadoid=estadoid;
	}
}