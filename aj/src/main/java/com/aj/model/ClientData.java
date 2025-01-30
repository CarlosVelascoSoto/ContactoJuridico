package com.aj.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
/*import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;*/
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "clientdata")
public class ClientData implements Serializable{

//	private static final long serialVersionUID = 1L;
	
	@Id
/*	@GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "client-seq-gen"
    )
    @SequenceGenerator(
        name = "client-seq-gen",
        sequenceName = "client_seq",
        allocationSize = 1,
        initialValue = 1
    )*/
	@Column(name = "clientid", updatable=false)
	private Integer clientid;
	
	@Column(name = "address", updatable=false)
	private String address;
	
	@Column(name = "city", updatable=false)
	private String city;
	
	@Column(name = "contact", updatable=false)
	private String contact;
	
	@Column(name = "country", updatable=false)
	private String country;
	
	@Column(name = "email", updatable=false)
	private String email;
	
	@Column(name = "fax", updatable=false)
	private String fax;
	
	@Column(name = "phone", updatable=false)
	private String phone;
	
	@Column(name = "state", updatable=false)
	private String state;
	
	@Column(name = "zipcode", updatable=false)
	private String zipcode;
	
	@Column(name = "shipmod", updatable=false)
	private String shipmod;
	
	@Column(name = "termpay", updatable=false)
	private String termpay;
	
	@Column(name = "incoterms", updatable=false)
	private String incoterms;

	@Column(name = "address2", updatable=false)
	private String address2;

	@Column(name = "address3", updatable=false)
	private String address3;
	
	@Column(name = "taxid", updatable=false)
	private String taxid;

	@Column(name = "pitex", updatable=false)
	private String pitex;

	@Column(name = "confirmreception", updatable=false)
	private int confirmreception;

	@Column(name = "paytermsid", updatable=false)
	private int paytermsid;

	@Column(name = "comments", updatable=false)
	private String comments;
	
	@Column(name = "applycomm", updatable=false)
	private int applycomm;
	
	@Column(name = "commission", updatable=false)
	private Double commission; //numeric(5,2)
	
	@Column(name = "vendorid", updatable=false)
	private String vendorid;
	
	@Column(name = "carrierid", updatable=false)
	private int carrierid;
	
	@Column(name = "taxbreakdown", updatable=false)
	private int taxbreakdown;
	
	@Column(name = "installment", updatable=false)
	private int installment;
	
	@Column(name = "interestrate", updatable=false)
	private Double interestrate; //numeric(5,2)
	
	@Column(name = "extnumber", updatable=false)
	private String extnumber;
	
	@Column(name = "intnumber", updatable=false)
	private String intnumber;
	
	@Column(name = "retentionvat", updatable=false)
	private Double retentionvat;

	@Column(name = "bankreference1", updatable=false)
	private String bankreference1;

	@Column(name = "bankreference2", updatable=false)
	private String bankreference2;

	@Column(name = "foreigntaxid", updatable=false)
	private String foreigntaxid;

	@Column(name = "alteremail", updatable=false)
	private String alteremail;
	
	@Column(name = "locationid", updatable=false)
	private int locationid;
	
	@Column(name = "mngvatret", updatable=false)
	private int mngvatret;
	
	@Column(name = "mngtaxret", updatable=false)
	private int mngtaxret;
	
	@Column(name = "mngdstret", updatable=false)
	private int mngdstret;
	
	@Column(name = "mngothret", updatable=false)
	private int mngothret;
	
	@Column(name = "mngtplabels", updatable=false)
	private int mngtplabels;
	
	@Column(name = "iepsbreakdown", updatable=false)
	private int iepsbreakdown;
	
	@Column(name = "curp", updatable=false)
	private String curp;
	
	@Column(name = "countryid", updatable=false)
	private int countryid;
	
	@Column(name = "openfield1", updatable=false)
	private String openfield1;
	
	@Column(name = "openfield2", updatable=false)
	private String openfield2;
	
	@Column(name = "stateid", updatable=false)
	private int stateid;
	
	@Column(name = "municipalityid", updatable=false)
	private int municipalityid;
	
	@Column(name = "addrlocationid", updatable=false)
	private int addrlocationid;

	@Column(name = "zipcodeid", updatable=false)
	private int zipcodeid;

	@Column(name = "suburbid", updatable=false)
	private int suburbid;

	public Integer getClientid() {
		return clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getShipmod() {
		return shipmod;
	}

	public void setShipmod(String shipmod) {
		this.shipmod = shipmod;
	}

	public String getTermpay() {
		return termpay;
	}

	public void setTermpay(String termpay) {
		this.termpay = termpay;
	}

	public String getIncoterms() {
		return incoterms;
	}

	public void setIncoterms(String incoterms) {
		this.incoterms = incoterms;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getTaxid() {
		return taxid;
	}

	public void setTaxid(String taxid) {
		this.taxid = taxid;
	}

	public String getPitex() {
		return pitex;
	}

	public void setPitex(String pitex) {
		this.pitex = pitex;
	}

	public int getConfirmreception() {
		return confirmreception;
	}

	public void setConfirmreception(int confirmreception) {
		this.confirmreception = confirmreception;
	}

	public int getPaytermsid() {
		return paytermsid;
	}

	public void setPaytermsid(int paytermsid) {
		this.paytermsid = paytermsid;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getApplycomm() {
		return applycomm;
	}

	public void setApplycomm(int applycomm) {
		this.applycomm = applycomm;
	}

	public Double getCommission() {
		return commission;
	}

	public void setCommission(Double commission) {
		this.commission = commission;
	}

	public String getVendorid() {
		return vendorid;
	}

	public void setVendorid(String vendorid) {
		this.vendorid = vendorid;
	}

	public int getCarrierid() {
		return carrierid;
	}

	public void setCarrierid(int carrierid) {
		this.carrierid = carrierid;
	}

	public int getTaxbreakdown() {
		return taxbreakdown;
	}

	public void setTaxbreakdown(int taxbreakdown) {
		this.taxbreakdown = taxbreakdown;
	}

	public int getInstallment() {
		return installment;
	}

	public void setInstallment(int installment) {
		this.installment = installment;
	}

	public Double getInterestrate() {
		return interestrate;
	}

	public void setInterestrate(Double interestrate) {
		this.interestrate = interestrate;
	}

	public String getExtnumber() {
		return extnumber;
	}

	public void setExtnumber(String extnumber) {
		this.extnumber = extnumber;
	}

	public String getIntnumber() {
		return intnumber;
	}

	public void setIntnumber(String intnumber) {
		this.intnumber = intnumber;
	}

	public Double getRetentionvat() {
		return retentionvat;
	}

	public void setRetentionvat(Double retentionvat) {
		this.retentionvat = retentionvat;
	}

	public String getBankreference1() {
		return bankreference1;
	}

	public void setBankreference1(String bankreference1) {
		this.bankreference1 = bankreference1;
	}

	public String getBankreference2() {
		return bankreference2;
	}

	public void setBankreference2(String bankreference2) {
		this.bankreference2 = bankreference2;
	}

	public String getForeigntaxid() {
		return foreigntaxid;
	}

	public void setForeigntaxid(String foreigntaxid) {
		this.foreigntaxid = foreigntaxid;
	}

	public String getAlteremail() {
		return alteremail;
	}

	public void setAlteremail(String alteremail) {
		this.alteremail = alteremail;
	}

	public int getLocationid() {
		return locationid;
	}

	public void setLocationid(int locationid) {
		this.locationid = locationid;
	}

	public int getMngvatret() {
		return mngvatret;
	}

	public void setMngvatret(int mngvatret) {
		this.mngvatret = mngvatret;
	}

	public int getMngtaxret() {
		return mngtaxret;
	}

	public void setMngtaxret(int mngtaxret) {
		this.mngtaxret = mngtaxret;
	}

	public int getMngdstret() {
		return mngdstret;
	}

	public void setMngdstret(int mngdstret) {
		this.mngdstret = mngdstret;
	}

	public int getMngothret() {
		return mngothret;
	}

	public void setMngothret(int mngothret) {
		this.mngothret = mngothret;
	}

	public int getMngtplabels() {
		return mngtplabels;
	}

	public void setMngtplabels(int mngtplabels) {
		this.mngtplabels = mngtplabels;
	}

	public int getIepsbreakdown() {
		return iepsbreakdown;
	}

	public void setIepsbreakdown(int iepsbreakdown) {
		this.iepsbreakdown = iepsbreakdown;
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp;
	}

	public int getCountryid() {
		return countryid;
	}

	public void setCountryid(int countryid) {
		this.countryid = countryid;
	}

	public String getOpenfield1() {
		return openfield1;
	}

	public void setOpenfield1(String openfield1) {
		this.openfield1 = openfield1;
	}

	public String getOpenfield2() {
		return openfield2;
	}

	public void setOpenfield2(String openfield2) {
		this.openfield2 = openfield2;
	}

	public int getStateid() {
		return stateid;
	}

	public void setStateid(int stateid) {
		this.stateid = stateid;
	}

	public int getMunicipalityid() {
		return municipalityid;
	}

	public void setMunicipalityid(int municipalityid) {
		this.municipalityid = municipalityid;
	}

	public int getAddrlocationid() {
		return addrlocationid;
	}

	public void setAddrlocationid(int addrlocationid) {
		this.addrlocationid = addrlocationid;
	}

	public int getZipcodeid() {
		return zipcodeid;
	}

	public void setZipcodeid(int zipcodeid) {
		this.zipcodeid = zipcodeid;
	}

	public int getSuburbid() {
		return suburbid;
	}

	public void setSuburbid(int suburbid) {
		this.suburbid = suburbid;
	}	
}