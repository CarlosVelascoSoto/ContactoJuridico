package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aj.dao.CompanyclientsDAO;
import com.aj.model.Companyclients;

@Service("companyclientsService")
@Transactional
public class CompanyclientsServiceImpl implements CompanyclientsService{
	@Autowired
	private CompanyclientsDAO companyclientsDAO;

	@Override
	public Integer addNewCClient(Companyclients clientobj){
		return companyclientsDAO.addNewCClient(clientobj);
	}

	@Override
	public List<Companyclients> getAll(String query){
		return companyclientsDAO.getAll(query);
	}

	@Override
	public Companyclients getCClientById(int clientid){
		return companyclientsDAO.getCClientById(clientid);
	}

	@Override
	public void updateCClient(Companyclients clientobj){
		companyclientsDAO.updateCClient(clientobj);
	}

	@Override
	public void deleteCClient(int clientid){
		companyclientsDAO.deleteCClient(clientid);
	}
}