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
@Table(name="estados")
public class Estados implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
		strategy=GenerationType.SEQUENCE,
		generator="estadoid_seq-gen"
	)
	@SequenceGenerator(
		name="estadoid_seq-gen",
		sequenceName="estados_seq",
		allocationSize=1,
		initialValue=1
	)
	@Column(name="estadoid")
	private Long estadoid;

	@Column(name="estado")
	private String estado;

	@Column(name="paisid")
	private Long paisid;

	@Column(name="companyid")
	private Long companyid;

	public Long getEstadoid(){
		return estadoid;
	}

	public void setEstadoid(Long estadoid){
		this.estadoid=estadoid;
	}

	public String getEstado(){
		return estado;
	}

	public void setEstado(String estado){
		this.estado=estado;
	}

	public Long getPaisid(){
		return paisid;
	}

	public void setPaisid(Long paisid){
		this.paisid=paisid;
	}

	public Long getCompanyid(){
		return companyid;
	}

	public void setCompanyid(Long companyid){
		this.companyid=companyid;
	}
}