package com.aj.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="consultas")
public class Consultas implements Serializable{
        private static final Integer serialVersionUID=(int) 1L;

        @Id
        @GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="consultaid_seq-gen"
    )
    @SequenceGenerator(
        name="consultaid_seq-gen",
        sequenceName="consultaid_seq",
        allocationSize=1,
        initialValue=1
    )
    @Column(name="consultaid")
    private Integer consultaid;
    
    @Column(name="clienteid")
    private Integer clienteid;
    
    @Column(name="juicioid",nullable=true)
    private Integer juicioid;

    @Column(name="abogadoid")
    private Long abogadoid;
   
	@Column(name="materiaid")
    private Integer materiaid;

    @Column(name="consulta")
    private String consulta;

    @Column(name="fecha")
    private Date fecha;

    @Column(name="opinion")
    private String opinion;

    @Column(name="resumen")
    private String resumen;

    @Column(name="honorarios", precision=10, scale=2)
    private BigDecimal honorarios;

	public Integer getConsultaid(){
		return consultaid;
	}

	public void setConsultaid(Integer consultaid){
		this.consultaid=consultaid;
	}

	public Integer getClienteid(){
		return clienteid;
	}

	public void setClienteid(Integer clienteid){
		this.clienteid=clienteid;
	}

	public Integer getJuicioid(){
		return juicioid;
	}

	public void setJuicioid(Integer juicioid){
		this.juicioid=juicioid;
	}

	public Long getAbogadoid(){
		return abogadoid;
	}

	public void setAbogadoid(Long abogadoid){
		this.abogadoid=abogadoid;
	}

	public Integer getMateriaid(){
		return materiaid;
	}

	public void setMateriaid(Integer materiaid){
		this.materiaid=materiaid;
	}

	public String getConsulta(){
		return consulta;
	}

	public void setConsulta(String consulta){
		this.consulta=consulta;
	}

	public Date getFecha(){
		return fecha;
	}

	public void setFecha(Date fecha){
		this.fecha=fecha;
	}

	public String getOpinion(){
		return opinion;
	}

	public void setOpinion(String opinion){
		this.opinion=opinion;
	}

	public String getResumen(){
		return resumen;
	}

	public void setResumen(String resumen){
		this.resumen=resumen;
	}

	public BigDecimal getHonorarios(){
		return honorarios;
	}

	public void setHonorarios(BigDecimal honorarios){
		this.honorarios=honorarios;
	}
}