package com.aj.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class BeanFlot implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigInteger oportunidades;
	private String mon;
	private BigDecimal valor;
	private BigInteger numero_eventos;
	private Integer status;
	private Integer stageid;
	private String name;

	public BigInteger getOportunidades() {
		return oportunidades;
	}

	public void setOportunidades(BigInteger oportunidades) {
		this.oportunidades = oportunidades;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public BigInteger getNumero_eventos() {
		return numero_eventos;
	}

	public void setNumero_eventos(BigInteger numero_eventos) {
		this.numero_eventos = numero_eventos;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStageid() {
		return stageid;
	}

	public void setStageid(Integer stageid) {
		this.stageid = stageid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
