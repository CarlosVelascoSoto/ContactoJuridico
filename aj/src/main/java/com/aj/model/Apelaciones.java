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
@Table(name="apelaciones")
public class Apelaciones implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="apelaciones_seq-gen"
    )
    @SequenceGenerator(
        name="apelaciones_seq-gen",
        sequenceName="apelaciones_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="apelacionid")
	private Integer apelacionid;

	@Column(name="toca")
	private String toca;

	@Column(name="companyid")
	private Integer companyid;

	@Column(name="juicioid")
	private Integer juicioid;

	@Column(name="materiaid")
	private Integer materiaid;

	@Column(name="salaid")
	private Integer salaid;

	@Column(name="ciudadid")
	private Integer ciudadid;

	@Column(name="paisid")
	private Integer paisid;

	@Column(name="ponente")
	private String ponente;

	@Column(name="resolucion")
	private String resolucion;

	@Column(name="apelante")
	private String apelante;

	@Column(name="apelado")
	private String apelado;

	@Column(name="apelacionadhesiva")
	private Integer apelacionadhesiva;

	public Integer getApelacionid(){
		return apelacionid;
	}

	public void setApelacionid(Integer apelacionid){
		this.apelacionid=apelacionid;
	}

	public String getToca(){
		return toca;
	}

	public void setToca(String toca){
		this.toca=toca;
	}

	public Integer getCompanyid(){
		return companyid;
	}

	public void setCompanyid(Integer companyid){
		this.companyid=companyid;
	}

	public Integer getJuicioid(){
		return juicioid;
	}

	public void setJuicioid(Integer juicioid){
		this.juicioid=juicioid;
	}

	public Integer getMateriaid(){
		return materiaid;
	}

	public void setMateriaid(Integer materiaid){
		this.materiaid=materiaid;
	}

	public Integer getSalaid(){
		return salaid;
	}

	public void setSalaid(Integer salaid){
		this.salaid=salaid;
	}

	public Integer getCiudadid(){
		return ciudadid;
	}

	public void setCiudadid(Integer ciudadid){
		this.ciudadid=ciudadid;
	}

	public Integer getPaisid(){
		return paisid;
	}

	public void setPaisid(Integer paisid){
		this.paisid=paisid;
	}

	public String getPonente(){
		return ponente;
	}

	public void setPonente(String ponente){
		this.ponente=ponente;
	}

	public String getResolucion(){
		return resolucion;
	}

	public void setResolucion(String resolucion){
		this.resolucion=resolucion;
	}

	public String getApelante(){
		return apelante;
	}

	public void setApelante(String apelante){
		this.apelante=apelante;
	}

	public String getApelado(){
		return apelado;
	}

	public void setApelado(String apelado){
		this.apelado=apelado;
	}

	public Integer getApelacionadhesiva(){
		return apelacionadhesiva;
	}

	public void setApelacionadhesiva(Integer apelacionadhesiva){
		this.apelacionadhesiva=apelacionadhesiva;
	}
}