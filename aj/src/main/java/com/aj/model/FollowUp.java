package com.aj.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.aj.model.LeadProspect;;

@Entity
@Table(name = "followup")
public class FollowUp implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "followup-seq-gen"
    )
    @SequenceGenerator(
        name = "followup-seq-gen",
        sequenceName = "followup_seq",
        allocationSize = 1,
        initialValue = 1
    )
	@Column(name = "followupid")
	private Long followupid;
	

	
/*	@ManyToOne
	@JoinColumn(name="opportunityid", insertable=false, updatable=false)
	private LeadProspect lead;
	
	public LeadProspect getLead() {
		return lead;
	}

	public void setLead(LeadProspect lead) {
		this.lead = lead;
	}*/
	
	

	@Column(name = "opportunityid")
	private int opportunityid;
	
	@Column(name = "description")
	private String description;

	@Column(name = "date")
	private Date date;
	
	/*@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date", insertable = true)
	private Date date;*/

	@Column(name = "userid")
	private int userid;
	
	@Column(name = "dateend")
	private Date dateend;
	
	@Column(name = "action")
	private int action;
	
	public int getOpportunityid() {
		return opportunityid;
	}

	public void setOpportunityid(int opportunityid) {
		this.opportunityid = opportunityid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getFollowupid() {
		return followupid;
	}

	public void setFollowupid(Long followupid) {
		this.followupid = followupid;
	}

	public Date getDateend() {
		return dateend;
	}

	public void setDateend(Date dateend) {
		this.dateend = dateend;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}
	
}