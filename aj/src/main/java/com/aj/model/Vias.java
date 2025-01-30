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
@Table(name="vias")
public class Vias implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="vias_seq-gen"
    )
    @SequenceGenerator(
        name="vias_seq-gen",
        sequenceName="vias_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="viaid")
	private Long viaid;

	@Column(name="via")
	private String via;

	@Column(name="viaen")
	private String viaen;

	public Long getViaid(){
		return viaid;
	}

	public void setViaid(Long viaid){
		this.viaid=viaid;
	}

	public String getVia(){
		return via;
	}

	public void setVia(String via){
		this.via=via;
	}

	public String getViaen(){
		return viaen;
	}

	public void setViaen(String viaen){
		this.viaen=viaen;
	}
}