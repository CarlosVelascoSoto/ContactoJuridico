package com.aj.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.aj.model.FollowUp;

@Entity
@Table(name = "opportunity")
public class LeadProspect implements Serializable {

	private static final long serialVersionUID = 1L;// = (int) 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleid-seq-gen")
	@SequenceGenerator(name = "roleid-seq-gen", sequenceName = "opportunity_id_seq", allocationSize = 1, initialValue = 1)
	@Column(name = "id")
	private int id;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "opportunityid")
	private List<FollowUp> relacionx = new ArrayList();

	@Column(name = "lead", updatable = false)
	private String lead;

	@Column(name = "lead_info", updatable = false)
	private String leadinfo;

	@Column(name = "company", updatable = false)
	private Integer company;

	@Column(name = "contact", updatable = false)
	private Integer contact;

	@Column(name = "source", updatable = false)
	private String source;

	@Column(name = "quantification", updatable = false)
	// private double quantification;
	private String quantification;

	/*
	 * @Temporal(TemporalType.TIMESTAMP)
	 * 
	 * @Column(name="created", insertable=false) private Date created;
	 * 
	 * @Temporal(TemporalType.TIMESTAMP)
	 * 
	 * @Column(name="updated", insertable=true) private Date updated;
	 */

	@Column(name = "qualification", updatable = false)
	private String qualification;

	@Column(name = "status", updatable = false)
	private Integer status;

	@Column(name = "qualifiedcheck", updatable = false)
	private Integer qualifiedcheck;

	@Column(name = "product", updatable = false)
	private String product;

	@Column(name = "selectgroup", updatable = false)
	private Integer selectgroup;

	@Column(name = "opportunity", updatable = false)
	private String opportunity;

	@Temporal(TemporalType.DATE)
	@Column(name = "date", insertable = false)
	private Date date;

	@Temporal(TemporalType.DATE)
	@Column(name = "update", insertable = true)
	private Date update;

	@Column(name = "comment", updatable = false)
	private String comment;

	@Column(name = "salesperson_id", updatable = false)
	private long salespersonid = 0;

	@Column(name = "userid", updatable = false)
	private Integer userid;

	@Column(name = "currencyid", updatable = false)
	private Integer currency;

	@JoinColumn(name = "status", insertable = false, updatable = false)
	@OneToOne
	private StatusOpportunity statusopportunity;

	@Column(name = "substatus", updatable = false)
	private Integer substatus;

	@Column(name = "wfprocessstageid", updatable = false)
	private Integer wfprocessstageid;
	
	@Column(name="clientidrb")
	private Integer clientidrb;
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public String getLeadinfo() {
		return leadinfo;
	}

	public void setLeadinfo(String leadinfo) {
		this.leadinfo = leadinfo;
	}

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}

	public Integer getContact() {
		return contact;
	}

	public void setContact(Integer contact) {
		this.contact = contact;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	// public double getQuantification(){
	public String getQuantification() {
		return quantification;
	}

	// public void setQuantification(double quantification){
	public void setQuantification(String quantification2) {
		this.quantification = quantification2;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getQualifiedcheck() {
		return qualifiedcheck;
	}

	public void setQualifiedcheck(Integer qualifiedcheck) {
		this.qualifiedcheck = qualifiedcheck;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Integer getSelectgroup() {
		return selectgroup;
	}

	public void setSelectgroup(Integer selectgroup) {
		this.selectgroup = selectgroup;
	}

	public String getOpportunity() {
		return opportunity;
	}

	public void setOpportunity(String opportunity) {
		this.opportunity = opportunity;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public List<FollowUp> getRelacionx() {
		return relacionx;
	}

	public void setRelacionx(List<FollowUp> relacionx) {
		this.relacionx = relacionx;
	}

	public long getSalespersonid() {
		return salespersonid;
	}

	public void setSalespersonid(long salespersonid) {
		this.salespersonid = salespersonid;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getCurrency() {
		return currency;
	}

	public void setCurrency(Integer currency) {
		this.currency = currency;
	}

	public StatusOpportunity getStatusopportunity() {
		return statusopportunity;
	}

	public void setStatusopportunity(StatusOpportunity statusopportunity) {
		this.statusopportunity = statusopportunity;
	}

	public Integer getSubstatus() {
		return substatus;
	}

	public void setSubstatus(Integer substatus) {
		this.substatus = substatus;
	}

	public Integer getWfprocessstageid() {
		return wfprocessstageid;
	}

	public void setWfprocessstageid(Integer wfprocessstageid) {
		this.wfprocessstageid = wfprocessstageid;
	}

	public Integer getClientidrb() {
		return clientidrb;
	}

	public void setClientidrb(Integer clientidrb) {
		this.clientidrb = clientidrb;
	}
	
	
}