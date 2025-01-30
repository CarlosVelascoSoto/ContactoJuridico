package com.aj.service;

import java.util.List;
import com.aj.model.Socialnetworks;

public interface SocialnetworkService{
	public Integer addNewSocNetWork(Socialnetworks SocNetWorkobj); 
	public Socialnetworks getSocNetWorkById(int SocNetWorkid);
	public void updateSocNetWork(Socialnetworks SocNetWorkobj);
	public void deleteSocNetWork(int SocNetWorkid);
	public List<Socialnetworks> getAll(String query);
}