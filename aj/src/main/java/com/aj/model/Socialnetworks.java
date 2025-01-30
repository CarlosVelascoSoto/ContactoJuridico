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
@Table(name="socialnetworks")
public class Socialnetworks implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="socialnetworkid_seq-gen"
    )
    @SequenceGenerator(
        name="socialnetworkid_seq-gen",
        sequenceName="socialnetworkid_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="socialnetworkid")
	private int socialnetworkid;

	@Column(name="socialnetwork")
	private String socialnetwork;

	@Column(name="mainurl")
	private String mainurl;

	@Column(name="imageurl")
	private String imageurl;

	public int getSocialnetworkid(){
		return socialnetworkid;
	}

	public void setSocialnetworkid(int socialnetworkid){
		this.socialnetworkid=socialnetworkid;
	}

	public String getSocialnetwork(){
		return socialnetwork;
	}

	public void setSocialnetwork(String socialnetwork){
		this.socialnetwork=socialnetwork;
	}

	public String getMainurl(){
		return mainurl;
	}

	public void setMainurl(String mainurl){
		this.mainurl=mainurl;
	}

	public String getImageurl(){
		return imageurl;
	}

	public void setImageurl(String imageurl){
		this.imageurl=imageurl;
	}
}