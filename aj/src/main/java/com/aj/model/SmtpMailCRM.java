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
@Table(name="smtpmail")
public class SmtpMailCRM implements Serializable{
	private static final Integer serialVersionUID=(int) 1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="smtpmail-seq-gen"
    )
    @SequenceGenerator(
        name="smtpmail-seq-gen",
        sequenceName="smtpmail_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="smtpid")
	private Long smtpid;

	@Column(name="host")
	private String host;

	@Column(name="accountmail")
	private String accountmail;

	@Column(name="passwordmail")
	private String passwordmail;

	@Column(name="port")
	private Integer port;

	@Column(name="aliasmail")
	private String aliasmail;

	public Long getSmtpid(){
		return smtpid;
	}
	public void setSmtpid(Long smtpid){
		this.smtpid=smtpid;
	}

	public String getHost(){
		return host;
	}
	public void setHost(String host){
		this.host=host;
	}

	public String getAccountmail(){
		return accountmail;
	}
	public void setAccountmail(String accountmail){
		this.accountmail=accountmail;
	}

	public String getPasswordmail(){
		return passwordmail;
	}
	public void setPasswordmail(String passwordmail){
		this.passwordmail=passwordmail;
	}

	public Integer getPort(){
		return port;
	}
	public void setPort(Integer port){
		this.port=port;
	}

	public String getAliasmail(){
		return aliasmail;
	}
	public void setAliasmail(String aliasmail){
		this.aliasmail=aliasmail;
	}
}