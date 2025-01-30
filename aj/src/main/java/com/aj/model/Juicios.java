package com.aj.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import com.aj.model.Juicios;

@Entity
@Table(name="juicios")
public class Juicios implements Serializable{

	private static final Integer serialVersionUID=(int) 1L;
	//private static final Long serialVersionUID=1L;

	@Id
	@GeneratedValue(
        strategy=GenerationType.SEQUENCE,
        generator="juicios_seq-gen"
    )
    @SequenceGenerator(
        name="juicios_seq-gen",
        sequenceName="juicios_seq",
        allocationSize=1,
        initialValue=1
    )
	@Column(name="juicioid")
	private Integer juicioid;
	
	@Column(name="juicio")
	private String juicio;
	
	@Column(name="companyclientid")
	private Integer companyclientid;
	
	@Column(name="juzgadoid")
	private Integer juzgadoid;
	
	@Column(name="materiaid")
	private Integer materiaid;
	
	@Column(name="juiciotipoid")
	private Integer juiciotipoid;
	
	@Column(name="ciudadid")
	private Integer ciudadid;
	
	@Column(name="paisid")
	private Integer paisid;
	
	@Column(name="actor")
	private String actor;
	
	@Column(name="demandado")
	private String demandado;
	
	@Column(name="tercero")
	private String tercero;
	
	@Column(name="status")
	private Integer status;

	@Column(name="abogado")
	private String abogado;
	
	@Column(name="abogadocontraparte")
	private String abogadocontraparte;

	@Column(name="clientecaracter")
	private String clientecaracter;

	@Column(name="abogadoasignado")
	private String abogadoasignado;

	@Column(name="userid", nullable=true)
	private Integer userid;

	@Column(name="juezid", nullable=true)
	private Integer juezid;

	public Integer getJuicioid(){
		return juicioid;
	}

	public void setJuicioid(Integer juicioid){
		this.juicioid=juicioid;
	}

	public String getJuicio(){
		return juicio;
	}

	public void setJuicio(String juicio){
		this.juicio=juicio;
	}

	public Integer getCompanyclientid() {
		return companyclientid;
	}

	public void setCompanyclientid(Integer companyclientid) {
		this.companyclientid=companyclientid;
	}

	public Integer getJuzgadoid(){
		return juzgadoid;
	}

	public void setJuzgadoid(Integer juzgadoid){
		this.juzgadoid=juzgadoid;
	}

	public Integer getMateriaid(){
		return materiaid;
	}

	public void setMateriaid(Integer materiaid){
		this.materiaid=materiaid;
	}

	public Integer getJuiciotipoid(){
		return juiciotipoid;
	}

	public void setJuiciotipoid(Integer juiciotipoid){
		this.juiciotipoid=juiciotipoid;
	}

	public Integer getCiudadid(){
		return ciudadid;
	}

	public void setCiudadid(Integer ciudadid){
		this.ciudadid=ciudadid;
	}

	public Integer getPaisid(){
		return paisid;
	}

	public void setPaisid(Integer paisid){
		this.paisid=paisid;
	}

	public String getActor(){
		return actor;
	}

	public void setActor(String actor){
		this.actor=actor;
	}

	public String getDemandado(){
		return demandado;
	}

	public void setDemandado(String demandado){
		this.demandado=demandado;
	}

	public String getTercero(){
		return tercero;
	}

	public void setTercero(String tercero){
		this.tercero=tercero;
	}

	public Integer getStatus(){
		return status;
	}

	public void setStatus(Integer status){
		this.status=status;
	}

	public String getAbogado(){
		return abogado;
	}

	public void setAbogado(String abogado){
		this.abogado=abogado;
	}

	public String getAbogadocontraparte(){
		return abogadocontraparte;
	}

	public void setAbogadocontraparte(String abogadocontraparte){
		this.abogadocontraparte=abogadocontraparte;
	}

	public String getClientecaracter(){
		return clientecaracter;
	}

	public void setClientecaracter(String clientecaracter){
		this.clientecaracter=clientecaracter;
	}

	public String getAbogadoasignado(){
		return abogadoasignado;
	}

	public void setAbogadoasignado(String abogadoasignado){
		this.abogadoasignado=abogadoasignado;
	}

	public Integer getUserid(){
		return userid;
	}

	public void setUserid(Integer userid){
		this.userid=userid;
	}

	public Integer getJuezid(){
		return juezid;
	}

	public void setJuezid(Integer juezid){
		this.juezid=juezid;
	}
}