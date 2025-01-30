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
@Table(name="amparos")
public class Amparos implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="amparoid_seq-gen"
    )
    @SequenceGenerator(
        name="amparoid_seq-gen",
        sequenceName="amparoid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="amparoid")
	private Integer amparoid;

	@Column(name="amparo")
	private String amparo;

	@Column(name="quejoso",nullable=true)
	private String quejoso;

	@Column(name="apelacionid",nullable=true)
	private Integer apelacionid;

	@Column(name="autoridadresponsable")
	private String autoridadresponsable;

	@Column(name="actoreclamado")
	private String actoreclamado;

	@Column(name="fechanotificacionactoreclamado")
	private Date fechanotificacionactoreclamado;

	@Column(name="tercero",nullable=true)
	private String tercero;

	@Column(name="fechapresentaciondemanda")
	private Date fechapresentaciondemanda;

	@Column(name="demandaamparoturnadaa")
	private String demandaamparoturnadaa;

	@Column(name="fechadmision")
	private Date fechadmision;

	@Column(name="fechanotificaciondmision")
	private Date fechanotificaciondmision;

	@Column(name="suspension")
	private String suspension;

	@Column(name="fechaamparodirectoadhesivo")
	private Date fechaamparodirectoadhesivo;

	@Column(name="fechaturnoaponencia")
	private Date fechaturnoaponencia;

	@Column(name="fechasesionproyectosentencia")
	private Date fechasesionproyectosentencia;

	@Column(name="fechasentencia")
	private Date fechasentencia;

	@Column(name="fechanotificacionsentencia")
	private Date fechanotificacionsentencia;

	@Column(name="fecharecursorevision")
	private Date fecharecursorevision;

	@Column(name="amparotipo")
	private Integer amparotipo;

	@Column(name="fechaaudicienciaconstitucional")
	private Date fechaaudicienciaconstitucional;

	@Column(name="recursorevisioncontrasentencia")
	private String recursorevisioncontrasentencia;

	@Column(name="sentenciadefinitiva")
	private String sentenciadefinitiva;

	@Column(name="suspensionprovisional")
	private String suspensionprovisional;

	@Column(name="cuaderno")
	private String cuaderno;

	@Column(name="fechaaudienciaincidental")
	private Date fechaaudienciaincidental;

	@Column(name="sentencia")
	private String sentencia;

	@Column(name="juicioid",nullable=true)
	private Integer juicioid;
	
	@Column(name="fechaactoreclamado")
	private Date fechaactoreclamado;

	@Column(name="companyclientid")
	private Integer companyclientid;

	@Column(name="tipodemandaturnadaa")
	private String tipodemandaturnadaa;

	public Integer getAmparoid(){
		return amparoid;
	}

	public void setAmparoid(Integer amparoid){
		this.amparoid=amparoid;
	}

	public String getAmparo(){
		return amparo;
	}

	public void setAmparo(String amparo){
		this.amparo=amparo;
	}

	public String getQuejoso(){
		return quejoso;
	}

	public void setQuejoso(String quejoso){
		this.quejoso=quejoso;
	}

	public Integer getApelacionid(){
		return apelacionid;
	}

	public void setApelacionid(Integer apelacionid){
		this.apelacionid=apelacionid;
	}

	public String getAutoridadresponsable(){
		return autoridadresponsable;
	}

	public void setAutoridadresponsable(String autoridadresponsable){
		this.autoridadresponsable=autoridadresponsable;
	}

	public String getActoreclamado(){
		return actoreclamado;
	}

	public void setActoreclamado(String actoreclamado){
		this.actoreclamado=actoreclamado;
	}

	public Date getFechanotificacionactoreclamado(){
		return fechanotificacionactoreclamado;
	}

	public void setFechanotificacionactoreclamado(Date fechanotificacionactoreclamado){
		this.fechanotificacionactoreclamado=fechanotificacionactoreclamado;
	}

	public String getTercero(){
		return tercero;
	}

	public void setTercero(String tercero){
		this.tercero=tercero;
	}

	public Date getFechapresentaciondemanda(){
		return fechapresentaciondemanda;
	}

	public void setFechapresentaciondemanda(Date fechapresentaciondemanda){
		this.fechapresentaciondemanda=fechapresentaciondemanda;
	}

	public String getDemandaamparoturnadaa(){
		return demandaamparoturnadaa;
	}

	public void setDemandaamparoturnadaa(String demandaamparoturnadaa){
		this.demandaamparoturnadaa=demandaamparoturnadaa;
	}

	public Date getFechadmision(){
		return fechadmision;
	}

	public void setFechadmision(Date fechadmision){
		this.fechadmision=fechadmision;
	}

	public Date getFechanotificaciondmision(){
		return fechanotificaciondmision;
	}

	public void setFechanotificaciondmision(Date fechanotificaciondmision){
		this.fechanotificaciondmision=fechanotificaciondmision;
	}

	public String getSuspension(){
		return suspension;
	}

	public void setSuspension(String suspension){
		this.suspension=suspension;
	}

	public Date getFechaamparodirectoadhesivo(){
		return fechaamparodirectoadhesivo;
	}

	public void setFechaamparodirectoadhesivo(Date fechaamparodirectoadhesivo){
		this.fechaamparodirectoadhesivo=fechaamparodirectoadhesivo;
	}

	public Date getFechaturnoaponencia(){
		return fechaturnoaponencia;
	}

	public void setFechaturnoaponencia(Date fechaturnoaponencia){
		this.fechaturnoaponencia=fechaturnoaponencia;
	}

	public Date getFechasesionproyectosentencia(){
		return fechasesionproyectosentencia;
	}

	public void setFechasesionproyectosentencia(Date fechasesionproyectosentencia){
		this.fechasesionproyectosentencia=fechasesionproyectosentencia;
	}

	public Date getFechasentencia(){
		return fechasentencia;
	}

	public void setFechasentencia(Date fechasentencia){
		this.fechasentencia=fechasentencia;
	}

	public Date getFechanotificacionsentencia(){
		return fechanotificacionsentencia;
	}

	public void setFechanotificacionsentencia(Date fechanotificacionsentencia){
		this.fechanotificacionsentencia=fechanotificacionsentencia;
	}

	public Date getFecharecursorevision(){
		return fecharecursorevision;
	}

	public void setFecharecursorevision(Date fecharecursorevision){
		this.fecharecursorevision=fecharecursorevision;
	}

	public Integer getAmparotipo(){
		return amparotipo;
	}

	public void setAmparotipo(Integer amparotipo){
		this.amparotipo=amparotipo;
	}

	public Date getFechaaudicienciaconstitucional(){
		return fechaaudicienciaconstitucional;
	}

	public void setFechaaudicienciaconstitucional(Date fechaaudicienciaconstitucional){
		this.fechaaudicienciaconstitucional=fechaaudicienciaconstitucional;
	}

	public String getRecursorevisioncontrasentencia(){
		return recursorevisioncontrasentencia;
	}

	public void setRecursorevisioncontrasentencia(String recursorevisioncontrasentencia){
		this.recursorevisioncontrasentencia=recursorevisioncontrasentencia;
	}

	public String getSentenciadefinitiva(){
		return sentenciadefinitiva;
	}

	public void setSentenciadefinitiva(String sentenciadefinitiva){
		this.sentenciadefinitiva=sentenciadefinitiva;
	}

	public String getSuspensionprovisional(){
		return suspensionprovisional;
	}

	public void setSuspensionprovisional(String suspensionprovisional){
		this.suspensionprovisional=suspensionprovisional;
	}

	public String getCuaderno(){
		return cuaderno;
	}

	public void setCuaderno(String cuaderno){
		this.cuaderno=cuaderno;
	}

	public Date getFechaaudienciaincidental(){
		return fechaaudienciaincidental;
	}

	public void setFechaaudienciaincidental(Date fechaaudienciaincidental){
		this.fechaaudienciaincidental=fechaaudienciaincidental;
	}

	public String getSentencia(){
		return sentencia;
	}

	public void setSentencia(String sentencia){
		this.sentencia=sentencia;
	}

	public Integer getJuicioid(){
		return juicioid;
	}

	public void setJuicioid(Integer juicioid){
		this.juicioid=juicioid;
	}

	public Date getFechaactoreclamado(){
		return fechaactoreclamado;
	}

	public void setFechaactoreclamado(Date fechaactoreclamado){
		this.fechaactoreclamado=fechaactoreclamado;
	}

	public Integer getCompanyclientid(){
		return companyclientid;
	}

	public void setCompanyclientid(Integer companyclientid){
		this.companyclientid=companyclientid;
	}

	public String getTipodemandaturnadaa(){
		return tipodemandaturnadaa;
	}

	public void setTipodemandaturnadaa(String tipodemandaturnadaa){
		this.tipodemandaturnadaa=tipodemandaturnadaa;
	}
}