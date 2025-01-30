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
@Table(name="relatedcolumns")
public class RelatedColumns implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
		strategy=GenerationType.SEQUENCE,
		generator="relatedcolumnid_seq-gen"
	)
	@SequenceGenerator(
		name="relatedcolumnid_seq-gen",
		sequenceName="relatedcolumn_seq",
		allocationSize=1,
		initialValue=1
	)
	@Column(name="relcolumnid")
	private Long relcolumnid;

	@Column(name="columnname1")
	private String columnname1;

	@Column(name="columnname2")
	private String columnname2;

	@Column(name="messagejs")
	private String messagejs;

	@Column(name="messagejsp")
	private String messagejsp;

	@Column(name="relfromtable")
	private String relfromtable;

	@Column(name="relfromcolumn")
	private String relfromcolumn;

	@Column(name="relfromdescription")
	private String relfromdescription;

	@Column(name="relsubtable")
	private String relsubtable;

	@Column(name="relsubcolumn")
	private String relsubcolumn;

	@Column(name="relsubdescription")
	private String relsubdescription;

	public Long getRelcolumnid(){
		return relcolumnid;
	}

	public void setRelcolumnid(Long relcolumnid){
		this.relcolumnid=relcolumnid;
	}

	public String getColumnname1(){
		return columnname1;
	}

	public void setColumnname1(String columnname1){
		this.columnname1=columnname1;
	}

	public String getColumnname2(){
		return columnname2;
	}

	public void setColumnname2(String columnname2){
		this.columnname2=columnname2;
	}

	public String getMessagejs(){
		return messagejs;
	}

	public void setMessagejs(String messagejs){
		this.messagejs=messagejs;
	}

	public String getMessagejsp(){
		return messagejsp;
	}

	public void setMessagejsp(String messagejsp){
		this.messagejsp=messagejsp;
	}

	public String getRelfromtable(){
		return relfromtable;
	}

	public void setRelfromtable(String relfromtable){
		this.relfromtable=relfromtable;
	}

	public String getRelfromcolumn(){
		return relfromcolumn;
	}

	public void setRelfromcolumn(String relfromcolumn){
		this.relfromcolumn=relfromcolumn;
	}

	public String getRelfromdescription(){
		return relfromdescription;
	}

	public void setRelfromdescription(String relfromdescription){
		this.relfromdescription=relfromdescription;
	}

	public String getRelsubtable(){
		return relsubtable;
	}

	public void setRelsubtable(String relsubtable){
		this.relsubtable=relsubtable;
	}

	public String getRelsubcolumn(){
		return relsubcolumn;
	}

	public void setRelsubcolumn(String relsubcolumn){
		this.relsubcolumn=relsubcolumn;
	}

	public String getRelsubdescription(){
		return relsubdescription;
	}

	public void setRelsubdescription(String relsubdescription){
		this.relsubdescription=relsubdescription;
	}
}