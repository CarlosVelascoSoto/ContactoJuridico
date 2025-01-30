package com.aj.model;

import java.io.Serializable;
import java.util.Date;

public class LeadAux implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String lead;
	private String lead_info;
	private String first_name;
	private String last_name;
	private Date date;
	private Integer salesperson_id;
	private String companyname;
	private String salesfm;
	private String salesln;
	private String quantification;
	private Integer status;

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

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getLead_info() {
		return lead_info;
	}

	public void setLead_info(String lead_info) {
		this.lead_info = lead_info;
	}

	public Integer getSalesperson_id() {
		return salesperson_id;
	}

	public void setSalesperson_id(Integer salesperson_id) {
		this.salesperson_id = salesperson_id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
	}

	public String getSalesfm() {
		return salesfm;
	}

	public void setSalesfm(String salesfm) {
		this.salesfm = salesfm;
	}

	public String getSalesln() {
		return salesln;
	}

	public void setSalesln(String salesln) {
		this.salesln = salesln;
	}

	public String getQuantification() {
		return quantification;
	}

	public void setQuantification(String quantification) {
		this.quantification = quantification;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	
}
