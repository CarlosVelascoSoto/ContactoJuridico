package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.SocialnetworkDAO;
import com.aj.model.Socialnetworks;

@Service("socialnetworkService")
@Transactional
public class SocialnetworkServiceImpl implements SocialnetworkService{
	@Autowired
	private SocialnetworkDAO socialnetworkDAO;

	@Override
	public Integer addNewSocNetWork(Socialnetworks SocNetWorkobj){
		return socialnetworkDAO.addNewSocNetWork(SocNetWorkobj);
	}

	@Override
	public Socialnetworks getSocNetWorkById(int SocNetWorkid){
		return socialnetworkDAO.getSocNetWorkById(SocNetWorkid);
	}

	@Override
	public void updateSocNetWork(Socialnetworks SocNetWorkobj){
		socialnetworkDAO.updateSocNetWork(SocNetWorkobj);
	}

	@Override
	public void deleteSocNetWork(int SocNetWorkid){
		socialnetworkDAO.deleteSocNetWork(SocNetWorkid);
	}

	@Override
	public List<Socialnetworks> getAll(String query){
		return socialnetworkDAO.getAll(query);
	}
}