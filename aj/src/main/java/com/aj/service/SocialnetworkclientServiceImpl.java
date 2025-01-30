package com.aj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.dao.SocialnetworkclientDAO;
import com.aj.model.Socialnetworkclient;

@Service("socialnetworkclientService")
@Transactional
public class SocialnetworkclientServiceImpl implements SocialnetworkclientService{
	@Autowired
	private SocialnetworkclientDAO socialnetworkclientDAO;
	
	@Override
	public Integer addNewSNCWork(Socialnetworkclient SNCobj) {
		return socialnetworkclientDAO.addNewSNCWork(SNCobj);
	}
	
	@Override
	public List<Socialnetworkclient> getAll(String query){
		return socialnetworkclientDAO.getAll(query);
	}
	
	@Override
	public Socialnetworkclient getSNCById(int sncid){
		return socialnetworkclientDAO.getSNCById(sncid);
	}

	@Override
	public void updateSNC(Socialnetworkclient sncid){
		socialnetworkclientDAO.updateSNC(sncid);
	}
	
	@Override
	public void deleteSNC(int sncid) {
		socialnetworkclientDAO.deleteSNC(sncid);
	}

	@Override
	public void deleteNotIn(String ccid, String query) {
		socialnetworkclientDAO.deleteNotIn(ccid,query);
	}
}