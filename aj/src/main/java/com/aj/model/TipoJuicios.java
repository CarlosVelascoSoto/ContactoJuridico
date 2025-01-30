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
@Table(name="tipojuicios")
public class TipoJuicios implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="tipojuicios_seq-gen"
    )
    @SequenceGenerator(
        name="tipojuicios_seq-gen",
        sequenceName="tipojuicios_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="tipojuicioid")
	private Long tipojuicioid;

	@Column(name="tipojuicio")
	private String tipojuicio;

	@Column(name="accionid")
	private Integer accionid;

	@Column(name="requiereactor")
	private Integer requiereactor;

	@Column(name="requieredemandado")
	private Integer requieredemandado;

	@Column(name="requieretercero")
	private Integer requieretercero;

	@Column(name="tipojuicioen")
	private String tipojuicioen;

	public Long getTipojuicioid(){
		return tipojuicioid;
	}

	public void setTipojuicioid(Long tipojuicioid){
		this.tipojuicioid=tipojuicioid;
	}

	public String getTipojuicio(){
		return tipojuicio;
	}

	public void setTipojuicio(String tipojuicio){
		this.tipojuicio=tipojuicio;
	}

	public Integer getAccionid(){
		return accionid;
	}

	public void setAccionid(Integer accionid){
		this.accionid=accionid;
	}

	public Integer getRequiereactor(){
		return requiereactor;
	}

	public void setRequiereactor(Integer requiereactor){
		this.requiereactor=requiereactor;
	}

	public Integer getRequieredemandado(){
		return requieredemandado;
	}

	public void setRequieredemandado(Integer requieredemandado){
		this.requieredemandado=requieredemandado;
	}

	public Integer getRequieretercero(){
		return requieretercero;
	}

	public void setRequieretercero(Integer requieretercero){
		this.requieretercero=requieretercero;
	}

	public String getTipojuicioen(){
		return tipojuicioen;
	}

	public void setTipojuicioen(String tipojuicioen){
		this.tipojuicioen=tipojuicioen;
	}
}