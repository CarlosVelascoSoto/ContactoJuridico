package com.aj.utility;

import java.util.Date;

public class UserDTO{
	private long id;
	private int role;
	private String username;
	private String password;
	private String first_name;
	private String last_name;
	private String company_name;
	private String photo_name;
	private String email;
	private String phone;
	private String address;
	private String city;
	private String state;
	private String country;
	private int status;
	private Date created;
	private Date updated;
	private int countAdminUsers;
	private int countSalesPerson;
	private int countSalesManager;
	private int countUsers;
	private int countCompany;
	private String language;
	private int currency;
	private int companyid;
	private Integer zipcode;
	private String cellphone;
	private Integer usertype;
	private Integer linkedclientid;

	public int getCountCompany(){
		return countCompany;
	}
	public void setCountCompany(int countCompany){
		this.countCompany=countCompany;
	}

	public int getCountAdminUsers(){
		return countAdminUsers;
	}
	public void setCountAdminUsers(int countAdminUsers){
		this.countAdminUsers=countAdminUsers;
	}

	public int getCountSalesPerson(){
		return countSalesPerson;
	}
	public void setCountSalesPerson(int countSalesPerson){
		this.countSalesPerson=countSalesPerson;
	}

	public int getCountSalesManager(){
		return countSalesManager;
	}
	public void setCountSalesManager(int countSalesManager){
		this.countSalesManager=countSalesManager;
	}

	public int getCountUsers(){
		return countUsers;
	}
	public void setCountUsers(int countUsers){
		this.countUsers=countUsers;
	}

	public long getId(){
		return id;
	}
	public void setId(long id){
		this.id=id;
	}

	public String getUsername(){
		return username;
	}
	public void setUsername(String username){
		this.username=username;
	}

	public String getPassword(){
		return password;
	}
	public void setPassword(String password){
		this.password=password;
	}

	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email=email;
	}

	public int getRole(){
		return role;
	}
	public void setRole(int role){
		this.role=role;
	}

	public String getFirst_name(){
		return first_name;
	}
	public void setFirst_name(String first_name){
		this.first_name=first_name;
	}

	public String getLast_name(){
		return last_name;
	}
	public void setLast_name(String last_name){
		this.last_name=last_name;
	}

	public String getCompany_name(){
		return company_name;
	}
	public void setCompany_name(String company_name){
		this.company_name=company_name;
	}

	public String getPhoto_name(){
		return photo_name;
	}
	public void setPhoto_name(String photo_name){
		this.photo_name=photo_name;
	}

	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone=phone;
	}

	public String getAddress(){
		return address;
	}
	public void setAddress(String address){
		this.address=address;
	}

	public String getCity(){
		return city;
	}
	public void setCity(String city){
		this.city=city;
	}

	public String getState(){
		return state;
	}
	public void setState(String state){
		this.state=state;
	}

	public String getCountry(){
		return country;
	}
	public void setCountry(String country){
		this.country=country;
	}

	public int getStatus(){
		return status;
	}
	public void setStatus(int status){
		this.status=status;
	}

	public Date getCreated(){
		return created;
	}
	public void setCreated(Date created){
		this.created=created;
	}

	public Date getUpdated(){
		return updated;
	}
	public void setUpdated(Date updated){
		this.updated=updated;
	}

	public String getLanguage(){
		return language;
	}
	public void setLanguage(String language){
		this.language=language;
	}
	public int getCurrency() {
		return currency;
	}
	public void setCurrency(int currency) {
		this.currency = currency;
	}
	public int getCompanyid(){
		return companyid;
	}
	public void setCompanyid(int companyid){
		this.companyid=companyid;
	}
	public Integer getZipcode(){
		return zipcode;
	}
	public void setZipcode(Integer zipcode){
		this.zipcode=zipcode;
	}
	public String getCellphone(){
		return cellphone;
	}
	public void setCellphone(String cellphone){
		this.cellphone=cellphone;
	}
	public Integer getUsertype(){
		return usertype;
	}
	public void setUsertype(Integer usertype){
		this.usertype=usertype;
	}
	public Integer getLinkedclientid(){
		return linkedclientid;
	}
	public void setLinkedclientid(Integer linkedclientid){
		this.linkedclientid=linkedclientid;
	}
}