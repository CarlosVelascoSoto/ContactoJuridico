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
@Table(name="paises")
public class Paises implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="paisid_seq-gen"
    )
    @SequenceGenerator(
        name="paisid_seq-gen",
        sequenceName="paisid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="paisid")
	private Long paisid;

	@Column(name="pais")
	private String pais;

	public Long getPaisid(){
		return paisid;
	}

	public void setPaisid(Long paisid){
		this.paisid=paisid;
	}

	public String getpais(){
		return pais;
	}

	public void setpais(String pais){
		this.pais=pais;
	}
}