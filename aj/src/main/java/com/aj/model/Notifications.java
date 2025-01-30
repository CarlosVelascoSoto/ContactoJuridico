package com.aj.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="notifications")
public class Notifications implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
		strategy=GenerationType.SEQUENCE,
		generator="notificationid_seq-gen"
	)
	@SequenceGenerator(
		name="notificationid_seq-gen",
		sequenceName="notifications_seq",
		allocationSize=1,
		initialValue=1
	)
	@Column(name="notificationid")
	private Long notificationid;

	@Column(name="userid")
	private Long userid;

	@Column(name="actiontypeid")
	private int actiontypeid;

	@Column(name="capturedate")
	private Date capturedate;
	
	@Column(name="moduleref", nullable=true)
	private Integer moduleref;

	@Column(name="referenceid", nullable=true)
	private Integer referenceid;

	@Column(name="reference")
	private String reference;

	@Column(name="actionsdetails")
	private String actionsdetails;

	@Column(name="confirmations")
	private String confirmations;

	@Column(name="companyclientid", nullable=true)
	private Integer companyclientid;

	public Long getNotificationid(){
		return notificationid;
	}

	public void setNotificationid(Long notificationid){
		this.notificationid = notificationid;
	}

	public Long getUserid(){
		return userid;
	}

	public void setUserid(Long userid){
		this.userid = userid;
	}

	public int getActiontypeid(){
		return actiontypeid;
	}

	public void setActiontypeid(int actiontypeid){
		this.actiontypeid = actiontypeid;
	}

	public Date getCapturedate(){
		return capturedate;
	}

	public void setCapturedate(Date capturedate){
		this.capturedate = capturedate;
	}

	public Integer getModuleref(){
		return moduleref;
	}

	public void setModuleref(Integer moduleref){
		this.moduleref = moduleref;
	}

	public Integer getReferenceid() {
		return referenceid;
	}

	public void setReferenceid(Integer referenceid) {
		this.referenceid = referenceid;
	}

	public String getReference(){
		return reference;
	}

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getActionsdetails(){
		return actionsdetails;
	}

	public void setActionsdetails(String actionsdetails){
		this.actionsdetails = actionsdetails;
	}

	public String getConfirmations(){
		return confirmations;
	}

	public void setConfirmations(String confirmations){
		this.confirmations=confirmations;
	}

	public Integer getCompanyclientid(){
		return companyclientid;
	}

	public void setCompanyclientid(Integer companyclientid){
		this.companyclientid=companyclientid;
	}
}