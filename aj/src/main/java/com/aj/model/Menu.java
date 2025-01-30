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
@Table(name = "menu")
public class Menu implements Serializable {
	private static final Integer serialVersionUID = (int) 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_seq-gen")
	@SequenceGenerator(name = "menu_seq-gen", sequenceName = "menuid_seq", allocationSize = 1, initialValue = 1)
	@Column(name = "menuid")
	private int menuid;

	@Column(name = "menu")
	private String menu;

	@Column(name = "menuparentid")
	private Integer menuparentid;

	@Column(name = "icon")
	private String icon;

	@Column(name = "link")
	private String link;

	@Column(name = "tipomenu")
	private Integer tipomenu;

	@Column(name = "orden")
	private Integer orden;

	public int getMenuid() {
		return menuid;
	}

	public void setMenuid(int menuid) {
		this.menuid = menuid;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public Integer getMenuparentid() {
		return menuparentid;
	}

	public void setMenuparentid(Integer menuparentid) {
		this.menuparentid = menuparentid;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Integer getTipomenu() {
		return tipomenu;
	}

	public void setTipomenu(Integer tipomenu) {
		this.tipomenu = tipomenu;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

}