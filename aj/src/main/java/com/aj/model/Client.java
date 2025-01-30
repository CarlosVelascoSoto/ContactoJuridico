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

//import com.aj.model.FollowUp;;

@Entity
@Table(name = "client")
public class Client implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "client-seq-gen"
    )
    @SequenceGenerator(
        name = "client-seq-gen",
        sequenceName = "client_seq",
        allocationSize = 1,
        initialValue = 1
    )
	@Column(name = "clientid", updatable=false)
	private Integer clientid;
	
	@Column(name = "client", updatable=false)
	private String client;
	
	@Column(name = "company", updatable=false)
	private String company;
	
	@Column(name = "status", updatable=false)
	private int status;
	
	@Column(name = "polizaid", updatable=false)
	private int polizaid;
	
	@Column(name = "salespersonid", updatable=false)
	private Integer salespersonid;
	
	@Column(name = "type", updatable=false)
	private int type;
	
	@Column(name = "currencyid", updatable=false)
	private int currencyid;
	
	@Column(name = "pricelistid", updatable=false)
	private Integer pricelistid;
	
	@Column(name = "mainaccountid", updatable=false)
	private Integer mainaccountid;
	
	@Column(name = "complementaccountid", updatable=false)
	private Integer complementaccountid;
	
	@Column(name = "saleschannelid", updatable=false)
	private Integer saleschannelid;
	
	@Column(name = "creditlimit", updatable=false)
	private Double creditlimit; //numeric(16,6)
	
	@Column(name = "sku", updatable=false)
	private int sku;
	
	@Column(name = "maxduebalance", updatable=false)
	private Double maxduebalance; //numeric(16,6)
	
	@Column(name = "clientgroupid", updatable=false)
	private int clientgroupid;
	
	@Column(name = "addenda", updatable=false)
	private int addenda;

	@Column(name = "payreference", updatable=false)
	private String payreference;
	
	@Column(name = "paymenttypeid", updatable=false)
	private Integer paymenttypeid;

	@Column(name = "cttypetaxid", updatable=false)
	private int cttypetaxid;
	
	@Column(name = "dsblautosendcfdi", updatable=false)
	private int dsblautosendcfdi;

	@Column(name = "addxmlexport", updatable=false)
	private int addxmlexport;

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Integer getClientid() {
		return clientid;
	}

	public void setClientid(Integer clientid) {
		this.clientid = clientid;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getPolizaid() {
		return polizaid;
	}

	public void setPolizaid(int polizaid) {
		this.polizaid = polizaid;
	}

	public Integer getSalespersonid() {
		return salespersonid;
	}

	public void setSalespersonid(Integer salespersonid) {
		this.salespersonid = salespersonid;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCurrencyid() {
		return currencyid;
	}

	public void setCurrencyid(int currencyid) {
		this.currencyid = currencyid;
	}

	public Integer getPricelistid() {
		return pricelistid;
	}

	public void setPricelistid(Integer pricelistid) {
		this.pricelistid = pricelistid;
	}

	public Integer getMainaccountid() {
		return mainaccountid;
	}

	public void setMainaccountid(Integer mainaccountid) {
		this.mainaccountid = mainaccountid;
	}

	public Integer getComplementaccountid() {
		return complementaccountid;
	}

	public void setComplementaccountid(Integer complementaccountid) {
		this.complementaccountid = complementaccountid;
	}

	public Integer getSaleschannelid() {
		return saleschannelid;
	}

	public void setSaleschannelid(Integer saleschannelid) {
		this.saleschannelid = saleschannelid;
	}

	public Double getCreditlimit() {
		return creditlimit;
	}

	public void setCreditlimit(Double creditlimit) {
		this.creditlimit = creditlimit;
	}

	public int getSku() {
		return sku;
	}

	public void setSku(int sku) {
		this.sku = sku;
	}

	public Double getMaxduebalance() {
		return maxduebalance;
	}

	public void setMaxduebalance(Double maxduebalance) {
		this.maxduebalance = maxduebalance;
	}

	public int getClientgroupid() {
		return clientgroupid;
	}

	public void setClientgroupid(int clientgroupid) {
		this.clientgroupid = clientgroupid;
	}

	public int getAddenda() {
		return addenda;
	}

	public void setAddenda(int addenda) {
		this.addenda = addenda;
	}

	public String getPayreference() {
		return payreference;
	}

	public void setPayreference(String payreference) {
		this.payreference = payreference;
	}

	public Integer getPaymenttypeid() {
		return paymenttypeid;
	}

	public void setPaymenttypeid(Integer paymenttypeid) {
		this.paymenttypeid = paymenttypeid;
	}

	public int getCttypetaxid() {
		return cttypetaxid;
	}

	public void setCttypetaxid(int cttypetaxid) {
		this.cttypetaxid = cttypetaxid;
	}

	public int getDsblautosendcfdi() {
		return dsblautosendcfdi;
	}

	public void setDsblautosendcfdi(int dsblautosendcfdi) {
		this.dsblautosendcfdi = dsblautosendcfdi;
	}

	public int getAddxmlexport() {
		return addxmlexport;
	}

	public void setAddxmlexport(int addxmlexport) {
		this.addxmlexport = addxmlexport;
	}
}