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
@Table(name="jueces")
public class Jueces implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="jueces_seq-gen"
    )
    @SequenceGenerator(
        name="jueces_seq-gen",
        sequenceName="jueces_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="juezid")
	private Long juezid;

	@Column(name="nombre")
	private String nombre;

	@Column(name="juzgadoid")
	private Long juzgadoid;

	public Long getJuezid(){
		return juezid;
	}

	public void setJuezid(Long juezid){
		this.juezid=juezid;
	}

	public String getNombre(){
		return nombre;
	}

	public void setNombre(String nombre){
		this.nombre=nombre;
	}

	public Long getJuzgadoid(){
		return juzgadoid;
	}

	public void setJuzgadoid(Long juzgadoid){
		this.juzgadoid=juzgadoid;
	}
}