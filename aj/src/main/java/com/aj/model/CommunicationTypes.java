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
@Table(name="communicationtypes")
public class CommunicationTypes implements Serializable{
	private static final int serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="communicationtype_seq-gen"
    )
    @SequenceGenerator(
        name="communicationtype_seq-gen",
        sequenceName="communicationtype_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="commtypeid")
	private int commtypeid;

	@Column(name="description")
	private String description;

	@Column(name="hrefaction")
	private String hrefaction;

	@Column(name="onclickaction")
	private String onclickaction;

	public int getCommtypeid(){
		return commtypeid;
	}

	public void setCommtypeid(int commtypeid){
		this.commtypeid=commtypeid;
	}

	public String getDescription(){
		return description;
	}

	public void setDescription(String description){
		this.description=description;
	}

	public String getHrefaction(){
		return hrefaction;
	}

	public void setHrefaction(String hrefaction){
		this.hrefaction=hrefaction;
	}

	public String getOnclickaction(){
		return onclickaction;
	}

	public void setOnclickaction(String onclickaction){
		this.onclickaction=onclickaction;
	}
}