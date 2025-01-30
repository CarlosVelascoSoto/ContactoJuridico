package com.aj.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "uploadfiles")
public class Uploadfiles implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uploadfileid-seq-gen")
	@SequenceGenerator(name = "uploadfileid-seq-gen", sequenceName = "uploadfiles_seq", allocationSize = 1, initialValue = 1)
	@Column(name = "uploadfileid")
	private Integer uploadfileid;
	@Column
	private String path;
	@Column
	private Integer catalogtype;
	@Column
	private String filename;
	@Column
	private Integer idregister;
	@Transient
	private String img;

	public Integer getUploadfileid() {
		return uploadfileid;
	}

	public void setUploadfileid(Integer uploadfileid) {
		this.uploadfileid = uploadfileid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Integer getCatalogtype() {
		return catalogtype;
	}

	public void setCatalogtype(Integer catalogtype) {
		this.catalogtype = catalogtype;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public Integer getIdregister() {
		return idregister;
	}

	public void setIdregister(Integer idregister) {
		this.idregister = idregister;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
