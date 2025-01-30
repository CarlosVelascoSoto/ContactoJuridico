package com.aj.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="recursos")
public class Recursos implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="recursoid_seq-gen"
    )
    @SequenceGenerator(
        name="recursoid_seq-gen",
        sequenceName="recursoid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="recursoid")
	private int recursoid;

	@Column(name="recurso")
	private String recurso;

	@Column(name="recursotipo")
	private String recursotipo;

	@Column(name="recurrente")
	private String recurrente;

	@Column(name="resolucionrecurrida")
	private String resolucionrecurrida;

	@Column(name="fechanotificacionresolucionrecurrida")
	private Date fechanotificacionresolucionrecurrida;

	@Column(name="fechainterposicionrecurso")
	private Date fechainterposicionrecurso;

	@Column(name="fechaadmisiontramiterecurso")
	private Date fechaadmisiontramiterecurso;

	@Column(name="fecharemisionaltribunalcolegiado")
	private Date fecharemisionaltribunalcolegiado;

	@Column(name="recursoturnadoa")
	private String recursoturnadoa;

	@Column(name="fechaadmisiontribunalcolegiado")
	private Date fechaadmisiontribunalcolegiado;

	@Column(name="fechanotificacionadmisiontribunalcolegiado")
	private Date fechanotificacionadmisiontribunalcolegiado;

	@Column(name="fecharecursorevisionadhesivo")
	private Date fecharecursorevisionadhesivo;

	@Column(name="fechaturnoaponencia")
	private Date fechaturnoaponencia;

	@Column(name="fechasesionproyectoresolucion")
	private Date fechasesionproyectoresolucion;

	@Column(name="fecharesolucion")
	private Date fecharesolucion;
	
	@Column(name="fechaturnoaponenciapararesolver")
	private Date fechaturnoaponenciapararesolver;

	@Column(name="fecharadicacionrecurso")
	private Date fecharadicacionrecurso;

	@Column(name="acuerdotribunalcolegiado")
	private String acuerdotribunalcolegiado;

	@Column(name="tipoorigen", nullable=true)
	private Integer tipoorigen;

	@Column(name="tipoorigenid", nullable=true)
	private Integer tipoorigenid;

	public int getRecursoid(){
		return recursoid;
	}

	public void setRecursoid(int recursoid){
		this.recursoid=recursoid;
	}

	public String getRecurso(){
		return recurso;
	}

	public void setRecurso(String recurso){
		this.recurso=recurso;
	}

	public String getRecursotipo(){
		return recursotipo;
	}

	public void setRecursotipo(String recursotipo){
		this.recursotipo=recursotipo;
	}

	public String getRecurrente(){
		return recurrente;
	}

	public void setRecurrente(String recurrente){
		this.recurrente=recurrente;
	}

	public String getResolucionrecurrida(){
		return resolucionrecurrida;
	}

	public void setResolucionrecurrida(String resolucionrecurrida){
		this.resolucionrecurrida=resolucionrecurrida;
	}

	public Date getFechanotificacionresolucionrecurrida(){
		return fechanotificacionresolucionrecurrida;
	}

	public void setFechanotificacionresolucionrecurrida(Date fechanotificacionresolucionrecurrida){
		this.fechanotificacionresolucionrecurrida=fechanotificacionresolucionrecurrida;
	}

	public Date getFechainterposicionrecurso(){
		return fechainterposicionrecurso;
	}

	public void setFechainterposicionrecurso(Date fechainterposicionrecurso){
		this.fechainterposicionrecurso=fechainterposicionrecurso;
	}

	public Date getFechaadmisiontramiterecurso(){
		return fechaadmisiontramiterecurso;
	}

	public void setFechaadmisiontramiterecurso(Date fechaadmisiontramiterecurso){
		this.fechaadmisiontramiterecurso=fechaadmisiontramiterecurso;
	}

	public Date getFecharemisionaltribunalcolegiado(){
		return fecharemisionaltribunalcolegiado;
	}

	public void setFecharemisionaltribunalcolegiado(Date fecharemisionaltribunalcolegiado){
		this.fecharemisionaltribunalcolegiado=fecharemisionaltribunalcolegiado;
	}

	public String getRecursoturnadoa(){
		return recursoturnadoa;
	}

	public void setRecursoturnadoa(String recursoturnadoa){
		this.recursoturnadoa=recursoturnadoa;
	}

	public Date getFechaadmisiontribunalcolegiado(){
		return fechaadmisiontribunalcolegiado;
	}

	public void setFechaadmisiontribunalcolegiado(Date fechaadmisiontribunalcolegiado){
		this.fechaadmisiontribunalcolegiado=fechaadmisiontribunalcolegiado;
	}

	public Date getFechanotificacionadmisiontribunalcolegiado(){
		return fechanotificacionadmisiontribunalcolegiado;
	}

	public void setFechanotificacionadmisiontribunalcolegiado(Date fechanotificacionadmisiontribunalcolegiado){
		this.fechanotificacionadmisiontribunalcolegiado=fechanotificacionadmisiontribunalcolegiado;
	}

	public Date getFecharecursorevisionadhesivo(){
		return fecharecursorevisionadhesivo;
	}

	public void setFecharecursorevisionadhesivo(Date fecharecursorevisionadhesivo){
		this.fecharecursorevisionadhesivo=fecharecursorevisionadhesivo;
	}

	public Date getFechaturnoaponencia(){
		return fechaturnoaponencia;
	}

	public void setFechaturnoaponencia(Date fechaturnoaponencia){
		this.fechaturnoaponencia=fechaturnoaponencia;
	}

	public Date getFechasesionproyectoresolucion(){
		return fechasesionproyectoresolucion;
	}

	public void setFechasesionproyectoresolucion(Date fechasesionproyectoresolucion){
		this.fechasesionproyectoresolucion=fechasesionproyectoresolucion;
	}

	public Date getFecharesolucion(){
		return fecharesolucion;
	}

	public void setFecharesolucion(Date fecharesolucion){
		this.fecharesolucion=fecharesolucion;
	}

	public Date getFechaturnoaponenciapararesolver(){
		return fechaturnoaponenciapararesolver;
	}

	public void setFechaturnoaponenciapararesolver(Date fechaturnoaponenciapararesolver){
		this.fechaturnoaponenciapararesolver=fechaturnoaponenciapararesolver;
	}

	public Date getFecharadicacionrecurso(){
		return fecharadicacionrecurso;
	}

	public void setFecharadicacionrecurso(Date fecharadicacionrecurso){
		this.fecharadicacionrecurso=fecharadicacionrecurso;
	}

	public String getAcuerdotribunalcolegiado(){
		return acuerdotribunalcolegiado;
	}

	public void setAcuerdotribunalcolegiado(String acuerdotribunalcolegiado){
		this.acuerdotribunalcolegiado=acuerdotribunalcolegiado;
	}

	public Integer getTipoorigen(){
		return tipoorigen;
	}

	public void setTipoorigen(Integer tipoorigen){
		this.tipoorigen=tipoorigen;
	}

	public Integer getTipoorigenid(){
		return tipoorigenid;
	}

	public void setTipoorigenid(Integer tipoorigenid){
		this.tipoorigenid=tipoorigenid;
	}
}