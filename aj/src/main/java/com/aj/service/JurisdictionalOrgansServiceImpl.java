package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.JurisdictionalOrgansDAO;
import com.aj.model.JurisdictionalOrgans;

@Service("jrdorganService")
@Transactional
public class JurisdictionalOrgansServiceImpl implements JurisdictionalOrgansService{
	@Autowired
	private JurisdictionalOrgansDAO jrdorganesDAO;
	
	@Override
	public Integer addNewJrdOrgan(JurisdictionalOrgans jrdorganobj){
		return jrdorganesDAO.addNewJrdOrgan(jrdorganobj);
	}
	
	@Override
	public List<JurisdictionalOrgans> getAll(String query){
		return jrdorganesDAO.getAll(query);
	}
	
	@Override
	public JurisdictionalOrgans getJrdOrganById(Long getjrdorganid){
		return jrdorganesDAO.getJrdOrganById(getjrdorganid);
	}

	@Override
	public void updateJrdOrgan(JurisdictionalOrgans jrdorganobj){
		jrdorganesDAO.updateJrdOrgan(jrdorganobj);
	}
	
	@Override
	public void deleteJrdOrgan(Long jrdorganid){
		jrdorganesDAO.deleteJrdOrgan(jrdorganid);
	}
}