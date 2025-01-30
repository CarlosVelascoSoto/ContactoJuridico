package com.aj.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "statusfollow")
public class StatusFollow implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	private Short statusfollowid;
	@Column
	private String statuses;
	@Column
	private String statusen;
	@Column
	private String color;
	
	public Short getStatusfollowid() {
		return statusfollowid;
	}
	public void setStatusfollowid(Short statusfollowid) {
		this.statusfollowid = statusfollowid;
	}
	public String getStatuses() {
		return statuses;
	}
	public void setStatuses(String statuses) {
		this.statuses = statuses;
	}
	public String getStatusen() {
		return statusen;
	}
	public void setStatusen(String statusen) {
		this.statusen = statusen;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
		
}
