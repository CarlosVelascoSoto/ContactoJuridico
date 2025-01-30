package com.aj.dao;

import java.util.List;

import com.aj.model.Socialnetworkclient;

public interface SocialnetworkclientDAO{
	public Integer addNewSNCWork(Socialnetworkclient SNCobj); 
	public Socialnetworkclient getSNCById(int sncid);
	public void updateSNC(Socialnetworkclient SNCobj);
	public void deleteSNC(int sncid);
	public void deleteNotIn(String ccid,String validSN);
	public List<Socialnetworkclient> getAll(String query);
}