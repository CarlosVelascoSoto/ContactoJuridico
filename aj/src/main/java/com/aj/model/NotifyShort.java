package com.aj.model;

import java.util.Date;

// Datos sólo para el DashBoard
public class NotifyShort{
	private String notificationid;
	private String area;
	private String docnumber;
	private String clientname;
	private Integer activity;
	private String username;
	private Date date;

	public String getNotificationid(){
		return notificationid;
	}
	public void setNotificationid(String notificationid){
		this.notificationid=notificationid;
	}
	public String getArea(){
		return area;
	}
	public void setArea(String area){
		this.area=area;
	}
	public String getDocnumber(){
		return docnumber;
	}
	public void setDocnumber(String docnumber){
		this.docnumber=docnumber;
	}
	public String getClientname(){
		return clientname;
	}
	public void setClientname(String clientname){
		this.clientname=clientname;
	}
	public Integer getActivity(){
		return activity;
	}
	public void setActivity(Integer activity){
		this.activity=activity;
	}
	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username=username;
	}
	public Date getDate(){
		return date;
	}
	public void setDate(Date date){
		this.date=date;
	}
}