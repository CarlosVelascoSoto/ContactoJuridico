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
@Table(name="customcolumns")
public class CustomColumns implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="customcolumns_seq-gen"
    )
    @SequenceGenerator(
        name="customcolumns_seq-gen",
        sequenceName="customcolumns_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="customcolumnid")
	private Long customcolumnid;

	@Column(name="titulo")
	private String titulo;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="obligatorio")
	private Integer obligatorio;

	@Column(name="tituloen")
	private String tituloen;

	@Column(name="descripcionen")
	private String descripcionen;

	@Column(name="tipodecolumna")
	private String tipodecolumna;

	@Column(name="ligadoatabla")
	private String ligadoatabla;

	@Column(name="nombrepk")
	private String nombrepk;

	@Column(name="idpkreferenced")
	private Integer idpkreferenced;

	@Column(name="longitud")
	private Integer longitud;

	@Column(name="regex")
	private String regex;

	@Column(name="masdeuno")
	private Integer masdeuno;

	public Long getCustomcolumnid(){
		return customcolumnid;
	}

	public void setCustomcolumnid(Long customcolumnid){
		this.customcolumnid=customcolumnid;
	}

	public String getTitulo(){
		return titulo;
	}

	public void setTitulo(String titulo){
		this.titulo=titulo;
	}

	public String getDescripcion(){
		return descripcion;
	}

	public void setDescripcion(String descripcion){
		this.descripcion=descripcion;
	}

	public Integer getObligatorio(){
		return obligatorio;
	}

	public void setObligatorio(Integer obligatorio){
		this.obligatorio=obligatorio;
	}

	public String getTituloen(){
		return tituloen;
	}

	public void setTituloen(String tituloen){
		this.tituloen=tituloen;
	}

	public String getDescripcionen(){
		return descripcionen;
	}

	public void setDescripcionen(String descripcionen){
		this.descripcionen=descripcionen;
	}

	public String getTipodecolumna(){
		return tipodecolumna;
	}

	public void setTipodecolumna(String tipodecolumna){
		this.tipodecolumna=tipodecolumna;
	}

	public String getLigadoatabla(){
		return ligadoatabla;
	}

	public void setLigadoatabla(String ligadoatabla){
		this.ligadoatabla=ligadoatabla;
	}

	public String getNombrepk(){
		return nombrepk;
	}

	public void setNombrepk(String nombrepk){
		this.nombrepk=nombrepk;
	}

	public Integer getIdpkreferenced(){
		return idpkreferenced;
	}

	public void setIdpkreferenced(Integer idpkreferenced){
		this.idpkreferenced=idpkreferenced;
	}

	public Integer getLongitud(){
		return longitud;
	}

	public void setLongitud(Integer longitud){
		this.longitud=longitud;
	}

	public String getRegex(){
		return regex;
	}

	public void setRegex(String regex){
		this.regex=regex;
	}

	public Integer getMasdeuno(){
		return masdeuno;
	}

	public void setMasdeuno(Integer masdeuno){
		this.masdeuno=masdeuno;
	}
}