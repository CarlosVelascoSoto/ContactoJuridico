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
@Table(name="shareddockets")
public class SharedDockets implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="shareddockets_seq-gen"
    )
    @SequenceGenerator(
        name="shareddockets_seq-gen",
        sequenceName="shareddockets_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="shareddocketid")
	private int shareddocketid;
	
	@Column(name="juicioid", nullable=true)
	private int juicioid;

	@Column(name="usuarioidautoriza")
	private int usuarioidautoriza;

	@Column(name="emailexternaluser")
	private String emailexternaluser;

	@Column(name="userid", nullable=true)
	private int userid;

	@Column(name="shareddate")
	private Date shareddate;

	@Column(name="confirmationdate")
	private Date confirmationdate;

	@Column(name="notificationdate")
	private Date notificationdate;

	public int getShareddocketid(){
		return shareddocketid;
	}

	public void setShareddocketid(int shareddocketid){
		this.shareddocketid=shareddocketid;
	}

	public int getJuicioid(){
		return juicioid;
	}

	public void setJuicioid(int juicioid){
		this.juicioid=juicioid;
	}

	public int getUsuarioidautoriza(){
		return usuarioidautoriza;
	}

	public void setUsuarioidautoriza(int usuarioidautoriza){
		this.usuarioidautoriza=usuarioidautoriza;
	}

	public String getEmailexternaluser(){
		return emailexternaluser;
	}

	public void setEmailexternaluser(String emailexternaluser){
		this.emailexternaluser=emailexternaluser;
	}

	public int getUserid(){
		return userid;
	}

	public void setUserid(int userid){
		this.userid=userid;
	}

	public Date getShareddate(){
		return shareddate;
	}

	public void setShareddate(Date shareddate){
		this.shareddate=shareddate;
	}

	public Date getConfirmationdate(){
		return confirmationdate;
	}

	public void setConfirmationdate(Date confirmationdate){
		this.confirmationdate=confirmationdate;
	}

	public Date getNotificationdate(){
		return notificationdate;
	}

	public void setNotificationdate(Date notificationdate){
		this.notificationdate=notificationdate;
	}
}