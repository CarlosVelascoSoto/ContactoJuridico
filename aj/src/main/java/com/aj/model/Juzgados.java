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
@Table(name="juzgados")
public class Juzgados implements Serializable{
	
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="juzgadoid_seq-gen"
    )
    @SequenceGenerator(
        name="juzgadoid_seq-gen",
        sequenceName="juzgadoid_seq",
        allocationSize=1,
        initialValue=1
    )
	
	@Column(name="juzgadoid")
	private Long juzgadoid;
	
	@Column(name="juzgado")
	private String juzgado;

	@Column(name="companyid", nullable=true)
	private Integer companyid;

	@Column(name="ciudadid", nullable=true)
	private Integer ciudadid;

	@Column(name="tipojuzgado", nullable=true)
	private Integer tipojuzgado;

	@Column(name="materiaid", nullable=true)
	private Integer materiaid;

	@Column(name="distrito")
	private String distrito;

	@Column(name="partido", nullable=true)
	private Integer partido;

	public Long getJuzgadoid(){
		return juzgadoid;
	}

	public void setJuzgadoid(Long juzgadoid){
		this.juzgadoid=juzgadoid;
	}

	public String getJuzgado(){
		return juzgado;
	}

	public void setJuzgado(String juzgado){
		this.juzgado=juzgado;
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

	public Integer getTipojuzgado(){
		return tipojuzgado;
	}

	public void setTipojuzgado(Integer tipojuzgado){
		this.tipojuzgado=tipojuzgado;
	}

	public Integer getMateriaid(){
		return materiaid;
	}

	public void setMateriaid(Integer materiaid){
		this.materiaid=materiaid;
	}

	public String getDistrito(){
		return distrito;
	}

	public void setDistrito(String distrito){
		this.distrito=distrito;
	}

	public Integer getPartido(){
		return partido;
	}

	public void setPartido(Integer partido){
		this.partido=partido;
	}
}