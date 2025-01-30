package com.aj.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.aj.dao.LawyerDirectoryDAO;
import com.aj.model.LawyerDirectory;

@Service("lawyerdirectoryService")
@Transactional
public class LawyerDirectoryServiceImpl implements LawyerDirectoryService{
	@Autowired
	private LawyerDirectoryDAO lawyerdirectoryDAO;
	
	@Override
	public Integer addNewLawyerDir(LawyerDirectory lawyerdirobj){
		return lawyerdirectoryDAO.addNewLawyerDir(lawyerdirobj);
	}
	
	@Override
	public List<LawyerDirectory> getAll(String query){
		return lawyerdirectoryDAO.getAll(query);
	}
	
	@Override
	public LawyerDirectory getLawyerDirById(Long getlawyerdirid){
		return lawyerdirectoryDAO.getLawyerDirById(getlawyerdirid);
	}

	@Override
	public void updateLawyerDir(LawyerDirectory lawyerdirobj){
		lawyerdirectoryDAO.updateLawyerDir(lawyerdirobj);
	}
	
	@Override
	public void deleteLawyerDir(Long lawyerdirid){
		lawyerdirectoryDAO.deleteLawyerDir(lawyerdirid);
	}
}