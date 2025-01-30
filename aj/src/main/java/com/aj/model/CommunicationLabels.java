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
@Table(name="communicationlabels")
public class CommunicationLabels implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="communicationlabels_seq-gen"
    )
    @SequenceGenerator(
        name="communicationlabels_seq-gen",
        sequenceName="communicationlabels_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="commlabelid")
	private Long commlabelid ;

	@Column(name="commlabelname")
	private String commlabelname;

	public Long getCommlabelid(){
		return commlabelid;
	}

	public void setCommlabelid(Long commlabelid){
		this.commlabelid=commlabelid;
	}

	public String getCommlabelname(){
		return commlabelname;
	}

	public void setCommlabelname(String commlabelname){
		this.commlabelname=commlabelname;
	}
}