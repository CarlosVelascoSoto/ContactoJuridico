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
@Table(name="tipojuiciosaccion")
public class TipoJuiciosAccion implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="tipojuiciosaccion_seq-gen"
    )
    @SequenceGenerator(
        name="tipojuiciosaccion_seq-gen",
        sequenceName="tipojuiciosaccion_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="accionid")
	private Long accionid;

	@Column(name="descripcion")
	private String descripcion;

	@Column(name="descripcionen")
	private String descripcionen;

	@Column(name="materiaid")
	private int materiaid;

	@Column(name="viaid")
	private int viaid;

	public Long getAccionid(){
		return accionid;
	}

	public void setAccionid(Long accionid){
		this.accionid=accionid;
	}

	public String getDescripcion(){
		return descripcion;
	}

	public void setDescripcion(String descripcion){
		this.descripcion=descripcion;
	}

	public String getDescripcionen(){
		return descripcionen;
	}

	public void setDescripcionen(String descripcionen){
		this.descripcionen=descripcionen;
	}

	public int getMateriaid(){
		return materiaid;
	}

	public void setMateriaid(int materiaid){
		this.materiaid=materiaid;
	}

	public int getViaid(){
		return viaid;
	}

	public void setViaid(int viaid){
		this.viaid=viaid;
	}
}