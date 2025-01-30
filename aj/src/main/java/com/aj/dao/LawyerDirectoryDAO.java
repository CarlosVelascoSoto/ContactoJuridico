package com.aj.dao;

import java.util.List;
import com.aj.model.LawyerDirectory;

public interface LawyerDirectoryDAO{
	public Integer addNewLawyerDir(LawyerDirectory lawyerdirobj);
	public LawyerDirectory getLawyerDirById(Long lawyerdirid);
	public void updateLawyerDir(LawyerDirectory lawyerdirobj);
	public void deleteLawyerDir(Long lawyerdirid);
	public List<LawyerDirectory> getAll(String query);
}