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
@Table(name="movimientos")
public class Movimientos implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="movimientoid_seq-gen"
    )
    @SequenceGenerator(
        name="movimientoid_seq-gen",
        sequenceName="movimientoid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="movimientoid")
	private Long movimientoid;

	@Column(name="movimiento")
	private String movimiento;

	@Column(name="fechapresentacion")
	private Date fechapresentacion;

	@Column(name="fechaacuerdo")
	private Date fechaacuerdo;

	@Column(name="fechanotificacion")
	private Date fechanotificacion;

	@Column(name="observaciones")
	private String observaciones;

	@Column(name="juicioid",nullable=true)
	private Integer juicioid;

	@Column(name="tipoactuacionid")
	private Integer tipoactuacionid;

	@Column(name="amparoid",nullable=true)
	private Integer amparoid;

	@Column(name="apelacionid",nullable=true)
	private Integer apelacionid;

	@Column(name="recursoid",nullable=true)
	private Integer recursoid;

	@Column(name="cuaderno")
	private String cuaderno;
	
	@Column(name="consultaid",nullable=true)
	private Integer consultaid;

	public Long getMovimientoid(){
		return movimientoid;
	}

	public void setMovimientoid(Long movimientoid){
		this.movimientoid=movimientoid;
	}

	public String getMovimiento(){
		return movimiento;
	}

	public void setMovimiento(String movimiento){
		this.movimiento=movimiento;
	}

	public Date getFechapresentacion(){
		return fechapresentacion;
	}

	public void setFechapresentacion(Date fechapresentacion){
		this.fechapresentacion=fechapresentacion;
	}

	public Date getFechaacuerdo(){
		return fechaacuerdo;
	}

	public void setFechaacuerdo(Date fechaacuerdo){
		this.fechaacuerdo=fechaacuerdo;
	}

	public Date getFechanotificacion(){
		return fechanotificacion;
	}

	public void setFechanotificacion(Date fechanotificacion){
		this.fechanotificacion=fechanotificacion;
	}

	public String getObservaciones(){
		return observaciones;
	}

	public void setObservaciones(String observaciones){
		this.observaciones=observaciones;
	}

	public Integer getJuicioid(){
		return juicioid;
	}

	public void setJuicioid(Integer juicioid){
		this.juicioid=juicioid;
	}

	public Integer getTipoactuacionid(){
		return tipoactuacionid;
	}

	public void setTipoactuacionid(Integer tipoactuacionid){
		this.tipoactuacionid=tipoactuacionid;
	}

	public Integer getAmparoid(){
		return amparoid;
	}

	public void setAmparoid(Integer amparoid){
		this.amparoid=amparoid;
	}

	public Integer getApelacionid(){
		return apelacionid;
	}

	public void setApelacionid(Integer apelacionid){
		this.apelacionid=apelacionid;
	}

	public Integer getRecursoid(){
		return recursoid;
	}

	public void setRecursoid(Integer recursoid){
		this.recursoid=recursoid;
	}

	public String getCuaderno(){
		return cuaderno;
	}

	public void setCuaderno(String cuaderno){
		this.cuaderno=cuaderno;
	}

	public Integer getConsultaid(){
		return consultaid;
	}

	public void setConsultaid(Integer consultaid){
		this.consultaid = consultaid;
	}
}