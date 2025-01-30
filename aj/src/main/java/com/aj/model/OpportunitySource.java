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
@Table(name="opportunitysource")
public class OpportunitySource implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="opportunitysource_seq-gen"
    )
    @SequenceGenerator(
        name="opportunitysource_seq-gen",
        sequenceName="opportunitysource_seq",
        allocationSize=1,
        initialValue=1
    )
	
	@Column
	private int opportunitysourceid;

	@Column
	private String source;

	public int getOpportunitysourceid() {
		return opportunitysourceid;
	}

	public void setOpportunitysourceid(int opportunitysourceid) {
		this.opportunitysourceid = opportunitysourceid;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	

	
}