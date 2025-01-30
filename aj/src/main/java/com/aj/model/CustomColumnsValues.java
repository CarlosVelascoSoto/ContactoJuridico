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
@Table(name="CustomColumnsValues")
public class CustomColumnsValues implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="customcolumnsvalues_seq-gen"
    )
    @SequenceGenerator(
        name="customcolumnsvalues_seq-gen",
        sequenceName="customcolumnsvalues_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="customcolumnvalueid")
	private Long customcolumnvalueid;

	@Column(name="customcolumnid")
	private int customcolumnid;

	@Column(name="assignedvalue")
	private String assignedvalue;

	@Column(name="savedon")
	private String savedon;

	@Column(name="idreferenced")
	private Integer idreferenced;

	public Long getCustomcolumnvalueid(){
		return customcolumnvalueid;
	}

	public void setCustomcolumnvalueid(Long customcolumnvalueid){
		this.customcolumnvalueid=customcolumnvalueid;
	}

	public int getCustomcolumnid(){
		return customcolumnid;
	}

	public void setCustomcolumnid(int customcolumnid){
		this.customcolumnid=customcolumnid;
	}

	public String getAssignedvalue(){
		return assignedvalue;
	}

	public void setAssignedvalue(String assignedvalue){
		this.assignedvalue=assignedvalue;
	}

	public String getSavedon(){
		return savedon;
	}

	public void setSavedon(String savedon){
		this.savedon=savedon;
	}

	public Integer getIdreferenced(){
		return idreferenced;
	}

	public void setIdreferenced(Integer idreferenced){
		this.idreferenced=idreferenced;
	}
}