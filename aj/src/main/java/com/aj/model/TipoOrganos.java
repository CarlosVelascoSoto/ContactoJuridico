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
@Table(name="tipoorganos")
public class TipoOrganos implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="tipoorganos_seq-gen"
    )
    @SequenceGenerator(
        name="tipoorganos_seq-gen",
        sequenceName="tipoorganos_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="tipoorganoid")
	private Long tipoorganoid;

	@Column(name="tipoorgano")
	private String tipoorgano;

	public Long getTipoorganoid(){
		return tipoorganoid;
	}

	public void setTipoorganoid(Long tipoorganoid){
		this.tipoorganoid=tipoorganoid;
	}

	public String getTipoorgano(){
		return tipoorgano;
	}

	public void setTipoorgano(String tipoorgano){
		this.tipoorgano=tipoorgano;
	}
}