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
@Table(name="tipoactuacion")
public class TipoActuacion implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="tipoactuacionid_seq-gen"
    )
    @SequenceGenerator(
        name="tipoactuacionid_seq-gen",
        sequenceName="tipoactuacionid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="tipoactuacionid")
	private int tipoactuacionid;

	@Column(name="tipoactuacion")
	private String tipoactuacion;

	public int getTipoactuacionid(){
		return tipoactuacionid;
	}

	public void setTipoactuacionid(int tipoactuacionid){
		this.tipoactuacionid=tipoactuacionid;
	}

	public String getTipoactuacion(){
		return tipoactuacion;
	}

	public void setTipoactuacion(String tipoactuacion){
		this.tipoactuacion=tipoactuacion;
	}
}