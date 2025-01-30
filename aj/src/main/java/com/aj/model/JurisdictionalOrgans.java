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
@Table(name="organos")
public class JurisdictionalOrgans implements Serializable{
	private static final Integer seriaVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
		strategy=GenerationType.SEQUENCE,
		generator="organos_seq-gen"
	)
	@SequenceGenerator(
		name="organos_seq-gen",
		sequenceName="organos_seq",
		allocationSize=1,
		initialValue=1
	)
	@Column(name="organoid")
	private Long organoid;

	@Column(name="organo")
	private String organo;

	@Column(name="ciudadid")
	private Long ciudadid;

	public Long getOrganoid(){
		return organoid;
	}

	public void setOrganoid(Long organoid){
		this.organoid=organoid;
	}

	public String getOrgano(){
		return organo;
	}

	public void setOrgano(String organo){
		this.organo=organo;
	}

	public Long getCiudadid(){
		return ciudadid;
	}

	public void setCiudadid(Long ciudadid){
		this.ciudadid=ciudadid;
	}
}