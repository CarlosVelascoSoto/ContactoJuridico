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
@Table(name="calendar")
public class ECalendar implements Serializable{
	private static final long serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="calendar-seq-gen"
    )
    @SequenceGenerator(
        name="calendar-seq-gen",
        sequenceName="calendar_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="calendarid")
	private Integer calendarid;

	@Column(name="appointment")
	private String appointment;

	@Column(name="dateini")
	private Date dateini;

	@Column(name="dateend")
	private Date dateend;

	@Column(name="comments")
	private String comments;

	@Column(name="followupid",nullable=true)
	private Integer followupid;

	@Column(name="action")
	private int action;

	@Column(name="status")
	private int status;

	@Column(name="userid")
	private int userid;

	@Column(name="movimientoid",nullable=true)
	private Integer movimientoid;

	public Integer getCalendarid(){
		return calendarid;
	}

	public void setCalendarid(Integer calendarid){
		this.calendarid=calendarid;
	}

	public String getAppointment(){
		return appointment;
	}

	public void setAppointment(String appointment){
		this.appointment=appointment;
	}

	public Date getDateini(){
		return dateini;
	}

	public void setDateini(Date dateini){
		this.dateini=dateini;
	}

	public Date getDateend(){
		return dateend;
	}

	public void setDateend(Date dateend){
		this.dateend=dateend;
	}

	public String getComments(){
		return comments;
	}

	public void setComments(String comments){
		this.comments=comments;
	}

	public Integer getFollowupid(){
		return followupid;
	}

	public void setFollowupid(Integer followupid){
		this.followupid=followupid;
	}

	public int getAction(){
		return action;
	}

	public void setAction(int action){
		this.action=action;
	}

	public int getStatus(){
		return status;
	}

	public void setStatus(int status){
		this.status=status;
	}

	public int getUserid(){
		return userid;
	}

	public void setUserid(int userid){
		this.userid=userid;
	}

	public Integer getMovimientoid(){
		return movimientoid;
	}

	public void setMovimientoid(Integer movimientoid){
		this.movimientoid=movimientoid;
	}	
}