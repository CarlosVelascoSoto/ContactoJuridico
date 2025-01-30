package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.CommunicationTypesDAO;
import com.aj.model.CommunicationTypes;

@Service("commtypeService")
@Transactional
public class CommunicationTypesServiceImpl implements CommunicationTypesService{
	@Autowired
	private CommunicationTypesDAO communicationtypesDAO;
	
	@Override
	public Integer addNewCommType(CommunicationTypes commtypeobj){
		return communicationtypesDAO.addNewCommType(commtypeobj);
	}
	
	@Override
	public List<CommunicationTypes> getAll(String query){
		return communicationtypesDAO.getAll(query);
	}
	
	@Override
	public CommunicationTypes getCommTypeById(Long getcommtypeid){
		return communicationtypesDAO.getCommTypeById(getcommtypeid);
	}

	@Override
	public void updateCommType(CommunicationTypes commtypeobj){
		communicationtypesDAO.updateCommType(commtypeobj);
	}
	
	@Override
	public void deleteCommType(Long commtypeid){
		communicationtypesDAO.deleteCommType(commtypeid);
	}
}