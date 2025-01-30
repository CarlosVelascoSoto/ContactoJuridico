package com.aj.service;

import java.util.List;
import com.aj.model.LawyerDirectory;

public interface LawyerDirectoryService{
	public Integer addNewLawyerDir(LawyerDirectory lawyerdirobj);
	public LawyerDirectory getLawyerDirById(Long lawyerdirid);
	public void updateLawyerDir(LawyerDirectory lawyerdirobj);
	public void deleteLawyerDir(Long lawyerdirid);
	public List<LawyerDirectory> getAll(String query);
}