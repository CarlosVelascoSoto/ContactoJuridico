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
@Table(name="circuitos")
public class Circuitos implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
		strategy=GenerationType.SEQUENCE,
		generator="circuitoid_seq-gen"
	)
	@SequenceGenerator(
		name="circuitoid_seq-gen",
		sequenceName="circuitos_seq",
		allocationSize=1,
		initialValue=1
	)
	@Column(name="circuitoid")
	private Long circuitoid;

	@Column(name="circuito")
	private String circuito;

	@Column(name="estadoid")
	private Long estadoid;

	public Long getCircuitoid(){
		return circuitoid;
	}

	public void setCircuitoid(Long circuitoid){
		this.circuitoid=circuitoid;
	}

	public String getCircuito(){
		return circuito;
	}

	public void setCircuito(String circuito){
		this.circuito=circuito;
	}

	public Long getEstadoid(){
		return estadoid;
	}

	public void setEstadoid(Long estadoid){
		this.estadoid=estadoid;
	}
}