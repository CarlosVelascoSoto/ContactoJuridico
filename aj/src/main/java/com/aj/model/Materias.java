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
@Table(name="materias")
public class Materias implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="materiaid_seq-gen"
    )
    @SequenceGenerator(
        name="materiaid_seq-gen",
        sequenceName="materiaid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="materiaid")
	private Long materiaid;

	@Column(name="materia")
	private String materia;

	public Long getMateriaid(){
		return materiaid;
	}

	@Column(name="companyid", nullable=true)
	private Integer companyid;

	public void setMateriaid(Long materiaid){
		this.materiaid=materiaid;
	}

	public String getMateria(){
		return materia;
	}

	public void setMateria(String materia){
		this.materia=materia;
	}

	public Integer getCompanyid() {
		return companyid;
	}

	public void setCompanyid(Integer companyid){
		this.companyid=companyid;
	}
}